package org.golde.scratchforge.installer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.golde.scratchforge.installer.helpers.JavaHelpers;

public class FrameMainWindow extends JPanel{

	private static final long serialVersionUID = -6456622050470062974L;
	private JTextField textFieldLocation;
	private File installerRunDirectory;
	private final String DATA_ZIP_NAME = "sfdata.zip";
	private JTextArea textArea;
	private JScrollPane scroll;

	public FrameMainWindow() throws URISyntaxException {

		installerRunDirectory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();

		setPreferredSize(new Dimension(404, 236));
		setLayout(null);

		JLabel lblScratchforgeVInstaller = new JLabel("ScratchForge v" + Main.SF_VERSION + " Installer");
		lblScratchforgeVInstaller.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblScratchforgeVInstaller.setBounds(49, 28, 332, 27);
		add(lblScratchforgeVInstaller);

		JLabel lblThisInstallerWill = new JLabel("<html><center>This installer will install ScratchForge v" + Main.SF_VERSION + " <br>to the selected directory</center></html>");
		lblThisInstallerWill.setBounds(74, 68, 241, 45);
		add(lblThisInstallerWill);

		JLabel lblFolder = new JLabel("Folder");
		lblFolder.setBounds(43, 126, 56, 16);
		add(lblFolder);

		textFieldLocation = new JTextField();
		textFieldLocation.setBounds(92, 123, 241, 24);
		add(textFieldLocation);
		textFieldLocation.setColumns(10);
		textFieldLocation.setText(installerRunDirectory.getPath());

		JButton btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(installerRunDirectory);
				fileChooser.setFileSelectionMode(1);
				fileChooser.setAcceptAllFileFilterUsed(false);
				if (fileChooser.showOpenDialog(FrameMainWindow.this) == 0) {
					installerRunDirectory = fileChooser.getSelectedFile();
					textFieldLocation.setText(installerRunDirectory.getPath());
				}
			}
		});
		btnNewButton.setBounds(334, 122, 47, 25);
		add(btnNewButton);

		JButton btnInstall = new JButton("Install");
		btnInstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scroll.setVisible(true);
				textArea.setVisible(true);
				btnInstall.setVisible(false);
				btnNewButton.setVisible(false);
				textFieldLocation.setVisible(false);

				//Install Program
				printText("Checking java 8...");
				String JAVA_HOME = checkJDK();
				if(JAVA_HOME != null) {
					JOptionPane.showMessageDialog(null, "No JDK found. Your JAVA_HOME variable points to: " + JAVA_HOME + "\nPlease install java JDK > 1.8", "JDK Checker", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
					return;
				}

				new Thread() {
					public void run() {
						install();
						System.exit(0);
					}
				}.start();
				
				
			}
		});
		btnInstall.setBounds(158, 198, 97, 25);
		add(btnInstall);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(49, 168, 332, 55);
		textArea.setVisible(false);
		
		scroll = new JScrollPane(textArea);
	    scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(21, 28, 360, 195);
	    scroll.setVisible(false);
		add(scroll);


	}

	private String checkJDK() {
		String JAVA_HOME = System.getenv("JAVA_HOME");
		if(JAVA_HOME.contains("jdk1.8")) {
			return null;
		}
		else {
			return JAVA_HOME;
		}
	}



	private void install() {
		
		installerRunDirectory = new File(installerRunDirectory, "ScratchForge");
		
		if(installerRunDirectory.exists()) {
			if(!installerRunDirectory.isDirectory()) {
				//User has file named ScratchForge
				installFailed("It looks like you have a file named ScratchForge. To prevent data lose, we have canceled the installation. Please install to another directory.");
				return;
			}
			printText("Removing old version...");
			JavaHelpers.deleteDirectory(installerRunDirectory);
			printText("Deleted old version!");
		}
		installerRunDirectory.mkdirs();

		printText("Downloading zip...");
		
		try {
			JavaHelpers.downloadZip("http://scratchforge.golde.org/downloads/assets/" + Main.SF_VERSION + ".zip", new File(installerRunDirectory, DATA_ZIP_NAME));
		}
		catch(Exception e) {
			installFailed("Failed to download zip: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		printText("Downloaded!");

		File dataZip = new File(installerRunDirectory, DATA_ZIP_NAME);

		//Unzip file
		printText("Extracting zip...");
		try {
			JavaHelpers.extractFolder(dataZip, installerRunDirectory);
		}
		catch(Exception e) {
			installFailed("Failed to extract zip: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		printText("Extracted!");
		
		printText("Deleting temp zip...");
		if(!dataZip.delete()) {
			installFailed("Failed to delete " + DATA_ZIP_NAME);
		}
		printText("Deleted temp zip.");
		
		printText("Starting MCP...");
		//Run cmd and see if fail -> output context to installer log?
		//	gradlew decompile
		//	gradlew eclipse
		try {
			String line;
			Process p = JavaHelpers.runCMD(new File(installerRunDirectory, "forge"), "echo Running gradlew setupDevWorkspace... & gradlew setupDevWorkspace & echo Running gradlew eclipse... & gradlew eclipse", false);
			BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = bri.readLine()) != null) {
				printText(line);
			}
			bri.close();
			p.waitFor();
		}
		catch(Exception e) {
			installFailed("Failed to run gradlew command line: " + e.getMessage());
			return;
		}

		//Finish
		printText("Finished.");
		JOptionPane.showMessageDialog(this, "Successfully installed ScratchForge v" + Main.SF_VERSION + "!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	private void installFailed(String message) {
		printText("Installed finished with errors.");
		JOptionPane.showMessageDialog(this, "Failed to install ScratchForge v" + Main.SF_VERSION + "! " + message, "Failed to install!", JOptionPane.ERROR_MESSAGE);
	}

	private void printText(String message) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				textArea.setText(textArea.getText() + "\n" + message);
			}
			
		});
	     
	}
}

package org.golde.scratchforge.installer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.golde.scratchforge.installer.helpers.JavaHelpers;
import org.golde.scratchforge.installer.helpers.PLog;

public class FrameMainWindow extends JPanel{

	private static final long serialVersionUID = -6456622050470062974L;
	private JTextField textFieldLocation;
	private File installerRunDirectory;
	private final String DATA_ZIP_NAME = "sfdata.zip";

	public FrameMainWindow() throws URISyntaxException {

		installerRunDirectory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

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
				//Install Program
				String JAVA_HOME = checkJDK();
				if(JAVA_HOME != null) {
					JOptionPane.showMessageDialog(null, "No JDK found. Your JAVA_HOME variable points to: " + JAVA_HOME + "\nPlease install java JDK > 1.8", "JDK Checker", JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
					return;
				}

				install();

			}
		});
		btnInstall.setBounds(158, 198, 97, 25);
		add(btnInstall);
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
		//Copy Zip
		
		//PLog.info("JAR: " + installerRunDirectory.getAbsolutePath());
		
		try {
			JavaHelpers.downloadZip("http://web.golde.org/temp/testzip/" + Main.SF_VERSION + ".zip", new File(installerRunDirectory, DATA_ZIP_NAME));
		}
		catch(Exception e) {
			PLog.error(e, "Failed to download ZIP!");
			return;
		}
		
		File dataZip = new File(installerRunDirectory, DATA_ZIP_NAME);
		
		//Unzip file
		try {
			JavaHelpers.extractFolder(dataZip, new File(installerRunDirectory, "ScratchForge"));
		}
		catch(Exception e) {
			PLog.error(e, "Failed to unzip ZIP file!");
			return;
		}
		
		if(!dataZip.delete()) {
			PLog.error("Failed to delete " + DATA_ZIP_NAME);
		}
		
		//Run cmd and see if fail -> output context to installer log?
		//	gradlew decompile
		//	gradlew eclipse
		try {
			Process p = JavaHelpers.runCMD(new File(new File(installerRunDirectory, "ScratchForge"), "forge"), "gradlew setupDevWorkspace & gradlew eclipse", false);
			while(p.isAlive()) {Thread.sleep(1);}
		}
		catch(Exception e) {
			PLog.error(e, "Failed to run gradlew command line!");
			return;
		}
		
		//Finish
		JOptionPane.showMessageDialog(this, "Successfully installed ScratchForge v" + Main.SF_VERSION + "!", "Success!", JOptionPane.INFORMATION_MESSAGE);
	}

}

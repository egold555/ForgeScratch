package org.golde.scratchforge.installer;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

public class FrameMainWindow extends JPanel{

	private static final long serialVersionUID = -6456622050470062974L;
	private JTextField textFieldLocation;
	private File jarFolder;
	
	public FrameMainWindow() throws URISyntaxException {
		
		jarFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		
		setPreferredSize(new Dimension(404, 236));
		setLayout(null);
		
		JLabel lblScratchforgeVInstaller = new JLabel("ScratchForge v" + Main.SF_VERSION + " Installer");
		lblScratchforgeVInstaller.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblScratchforgeVInstaller.setBounds(74, 28, 259, 27);
		add(lblScratchforgeVInstaller);
		
		JLabel lblThisInstallerWill = new JLabel("<html><center>This installer will install ScratchForge v" + Main.SF_VERSION + " <br>to the selected directory</center></html>");
		lblThisInstallerWill.setBounds(74, 68, 241, 27);
		add(lblThisInstallerWill);
		
		JLabel lblFolder = new JLabel("Folder");
		lblFolder.setBounds(43, 126, 56, 16);
		add(lblFolder);
		
		textFieldLocation = new JTextField();
		textFieldLocation.setBounds(92, 123, 241, 24);
		add(textFieldLocation);
		textFieldLocation.setColumns(10);
		textFieldLocation.setText(jarFolder.getPath());
		
		JButton btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(jarFolder);
				fileChooser.setFileSelectionMode(1);
				fileChooser.setAcceptAllFileFilterUsed(false);
		        if (fileChooser.showOpenDialog(FrameMainWindow.this) == 0) {
		        	jarFolder = fileChooser.getSelectedFile();
		        	textFieldLocation.setText(jarFolder.getPath());
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
		//Extract ZIP
		//Run cmd and see if fail -> output context to installer log?
		//	gradlew decompile
		//	gradlew eclipse
		//Finish
		
	}
	
}

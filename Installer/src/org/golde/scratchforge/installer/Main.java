package org.golde.scratchforge.installer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.golde.scratchforge.installer.helpers.OS;

public class Main {
	
	public static final String SF_VERSION = "1.0";
	
	public static void main(String[] args) throws Exception {
		
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		JFrame frame  = new JFrame();
		frame.setTitle("ScratchForge Installer");
		frame.add(new FrameMainWindow());
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		OS.getOS();
		
		if(!OS.isWindows()) {
			 JLabel label = new JLabel("<html>Currently, ScratchForge only supports the Windows OS! ScratchForge will not work correctly. <b>Use at your own risk!</b> <br> I am hoping to support Mac and Linux in the future. But for now only Windows is supported.</html>");
			JOptionPane.showMessageDialog(frame, label, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}



}

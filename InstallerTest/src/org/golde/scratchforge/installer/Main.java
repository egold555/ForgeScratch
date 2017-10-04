package org.golde.scratchforge.installer;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {
	
	public static final String SF_VERSION = "1.0_Beta";
	
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
		
		
	}



}

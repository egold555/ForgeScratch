package org.golde.java.jdkchecker;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		String JAVA_HOME = System.getenv("JAVA_HOME");
		if(JAVA_HOME.contains("jdk1.8")) {
			JOptionPane.showMessageDialog(null, "Java JDK > 1.8 found! Your good to go!", "JDK Checker", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "No JDK found. Your JAVA_HOME variable points to: " + JAVA_HOME + "\nPlease install java JDK > 1.8", "JDK Checker", JOptionPane.ERROR_MESSAGE);
		}
	}

}

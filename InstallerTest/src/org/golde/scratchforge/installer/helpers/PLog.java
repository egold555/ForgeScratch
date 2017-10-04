package org.golde.scratchforge.installer.helpers;

/**
 * A simple logging class that tells the class and line number things were logged at!
 * @author Eric
 *
 */
public class PLog {
	
	private static final boolean ENABLED = true;

	public static void info(String msg) {
		if(!ENABLED) {return;}
		System.out.println("[INFO]" + format(new Exception()) + msg);
	}
	
	public static void warning(String msg) {
		if(!ENABLED) {return;}
		System.out.println("[WARNING]" + format(new Exception()) + msg);
	}
	
	public static void error(String msg) {
		if(!ENABLED) {return;}
		System.err.println("[ERROR]" + format(new Exception()) + msg);
	}
	
	public static void error(Exception e, String msg) {
		if(!ENABLED) {return;}
		error("[ERROR]" + format(new Exception()) + msg);
		e.printStackTrace();
	}
	
	private static String format(Exception e) {
		StackTraceElement ste = e.getStackTrace()[1];
		String s = "[";
		
		s = s + ste.getFileName().replace(".java", "");
		s = s + ":" + ste.getLineNumber();
		
		return s + "] ";
	}
	
	/*public static void errorPopup(Exception e, String message) {
		error(e, message);
		final JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Sans-Serif", Font.PLAIN, 10));
		textArea.setEditable(false);
		StringWriter writer = new StringWriter();
		PrintWriter printer = new PrintWriter(writer);
		printer.println(message);
		printer.println("");
		e.printStackTrace(printer);
		
		textArea.setText(writer.toString());

		JScrollPane scrollPane = new JScrollPane(textArea);		
		scrollPane.setPreferredSize(new Dimension(350, 150));

		JOptionPane.showMessageDialog(null, scrollPane, "An Error Has Occurred", JOptionPane.ERROR_MESSAGE);
	}*/
	
}

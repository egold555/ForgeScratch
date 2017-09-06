package org.golde.java.scratchforge;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.mod.Mod;

import netscape.javascript.JSObject;

/**
 * The class that JSObject comunicates too and calls functions to
 * @author Eric
 *
 */
public class JSFunctions {

	private Main main;
	private File forgeDir;
	private File forgeScratch;
	private File forgeModsIn;
	//private File forgeModsOut;
	private JSObject javaApp;
	private String javaHome = System.getenv("JAVA_HOME");

	public JSFunctions(Main main) {
		this.main = main;
		this.javaApp = main.window;
		this.forgeDir = main.forge_folder;
		this.forgeScratch = new File(forgeDir, "forgescratch");
		this.forgeModsIn = new File(forgeDir, "src\\main\\java\\org\\golde\\forge\\scratchforge\\mods");
		//this.forgeModsOut = new File(forgeScratch, "unusedMods");
	}

	public String saveXML() {
		String XML = (String) javaApp.call("saveXML");
		XML = XML.substring(0, 42) + "<modName>" + main.MOD_NAME + "</modName>" + XML.substring(42);
		return XML;
	}

	public void load(String xml) {
		int startIndex = xml.indexOf("<modName>") + "<modName>".length();
		int endIndex = xml.indexOf("</modName>");
		main.MOD_NAME = xml.substring(startIndex, endIndex);
		javaApp.call("loadXML", xml);
	}

	public void run(String code) {
		String projectName = main.MOD_NAME.replace(" ", "_");
		try {
			// 1. Search code for classes to get list of all blocks into a string list.
			List<String> blockNames = findBlockNames(code);

			// 2. Read Mod_Template into a string.
			String modTemplate = JavaHelper.readFile(new File(forgeScratch,"Mod_Template.java"));

			// 3. Replace stuff in that string.
			modTemplate = modTemplate.replace("Mod_Template", "Mod_" + projectName);
			modTemplate = modTemplate.replace("Mod Template", main.MOD_NAME);

			modTemplate = modTemplate.replace("/*Variables*/", variables(blockNames));
			modTemplate = modTemplate.replace("/*Constructor calls*/", constructorCalls(blockNames));

			modTemplate = modTemplate.replace("/*Classes*/", fixCode(code));

			// 4. Write string to a new file.
			JavaHelper.writeFile(new File(forgeModsIn,"Mod_" + projectName + ".java"), modTemplate);
			
			main.modManager.scanDirectoriesForMods();

		}
		catch(Exception e) {
			PLog.error(e, "Failed to make mod!");
		}

		try {
			JavaHelper.runCMD(forgeDir, "\"" + javaHome + "/bin/java.exe\" -Xincgc -Xmx4G -Xms4G \"-Dorg.gradle.appname=gradlew\" -classpath \"gradle\\wrapper\\gradle-wrapper.jar\" org.gradle.wrapper.GradleWrapperMain runClient", false);
		}catch(Exception e) {
			PLog.error(e, "Failed to start forge!");
		}
	}

	private List<String> findBlockNames(String code) {
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("public class Mcblock_(.*?) extends BlockBase");

		Matcher matcher = pattern.matcher(code);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}

		return result;
	}

	private String variables(List<String> blockNames) {
		String result = "";

		for (String blockName: blockNames) {
			result += "static " + className(blockName) + " " + variableName(blockName) + ";" + "\n";
		}

		return result;
	}


	private String constructorCalls(List<String> blockNames) {
		String result = "";

		for (String blockName: blockNames) {
			result += variableName(blockName) + " = new " + className(blockName) + "();" + "\n";
		}

		return result;
	}


	private String fixCode(String code) {
		code = code.replace("package delete_me;", "");
		code = code.replace("public class MyApp {", "");
		code = code.substring(0, code.length() - 4);
		return code;
	}

	private String variableName(String blockName)
	{
		return "mcblock_" + blockName;
	}

	private String className(String blockName)
	{
		return "Mcblock_" + blockName;
	}

	
	public void showEnabledMods(JFrame frame) {
		//Makes checkbox list
		List<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
		JPanel listOfFiles = new JPanel();
		listOfFiles.setLayout(new BoxLayout(listOfFiles, BoxLayout.Y_AXIS));

		for(Mod mod: main.modManager.allMods()) {
			JCheckBox checkbox = new JCheckBox(mod.getModName());
			checkbox.setSelected(mod.isEnabled());
			checkboxes.add(checkbox);
			listOfFiles.add(checkbox);
		}

		JScrollPane scrollPane = new JScrollPane(listOfFiles);		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(350, 150));

		int result = JOptionPane.showConfirmDialog(frame, scrollPane, "Enabled Mods", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			for(JCheckBox checkbox: checkboxes) {
				Mod mod = main.modManager.getMod(checkbox.getText());
				if (mod != null) {
					mod.setEnabled(checkbox.isSelected());
				}
			}
		}
	}
	
	public void disguardElements() {
		javaApp.call("Code.discard");
	}

	public void log(String msg) {
		PLog.info("[JS] " + msg);
	}

}

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
			// 1. Search code for classes to get list of all blocks/items into a string list.
			List<String> blockNames = findNames(code, false);
			List<String> itemNames = findNames(code, true);
			
			//PLog.info("Block Amount: " + blockNames.size());
			//PLog.info("Item Amount: " + itemNames.size());

			// 2. Read Mod_Template into a string.
			String modTemplate = JavaHelper.readFile(new File(forgeScratch,"Mod_Template.java"));

			// 3. Replace stuff in that string.
			modTemplate = modTemplate.replace("Mod_Template", "Mod_" + projectName);
			modTemplate = modTemplate.replace("Mod Template", main.MOD_NAME);

			modTemplate = modTemplate.replace("/*Variables - Block*/", variables(blockNames, false));
			modTemplate = modTemplate.replace("/*Constructor calls - Block*/", constructorCalls(blockNames, false));
			
			modTemplate = modTemplate.replace("/*Variables - Item*/", variables(itemNames, true));
			modTemplate = modTemplate.replace("/*Constructor calls - Item*/", constructorCalls(itemNames, true));

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

	private List<String> findNames(String code, boolean isItem) {
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("public class Mc" + (isItem ? "item" : "block") + "_(.*?) extends " + (isItem ? "Item" : "Block") + "Base");

		Matcher matcher = pattern.matcher(code);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}

		return result;
	}

	private String variables(List<String> blockNames, boolean isItem) {
		String result = "";

		for (String blockName: blockNames) {
			result += "static " + className(blockName, isItem) + " " + variableName(blockName, isItem) + ";" + "\n";
		}

		return result;
	}


	private String constructorCalls(List<String> names, boolean isItem) {
		String result = "";

		for (String name: names) {
			result += variableName(name, isItem) + " = new " + className(name, isItem) + "();" + "\n";
		}

		return result;
	}


	private String fixCode(String code) {
		code = code.replace("package delete_me;", "");
		code = code.replace("public class MyApp {", "");
		code = code.substring(0, code.length() - 4);
		return code;
	}

	private String variableName(String name, boolean isItem)
	{
		return "mcblock_" + (isItem ? "item_" : "block_") + name;
	}

	private String className(String name, boolean isItem)
	{
		return "Mc" + (isItem ? "item_" : "block_") + name;
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
	
	public void displayFSError(String msg) {
		log("[FS-Error] " + msg);
	}

}

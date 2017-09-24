package org.golde.java.scratchforge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;

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
	
	enum EnumObjectType{
		Block("block", "BlockBase"),
		BlockFlower("blockFlower", "BlockBaseFlower"),
		BlockPlant("blockPlant", "BlockBasePlant"),
		Item("item", "ItemBase"),
		Entity("entity", "EntityCreature"),
		;

		public final String clazz;
		public final String extendz;
		EnumObjectType(String clazz, String extendz){
			this.clazz = clazz;
			this.extendz = extendz;
		}

	}

	public void run(String sfGenCode) {
		String projectName = main.MOD_NAME.replace(" ", "_");
		try {

			//Setup basic variables
			File projectFolder = new File(forgeModsIn, JavaHelper.makeJavaId(projectName));
			JavaHelper.copyFolder(new File(forgeScratch, "Template"), projectFolder);
			String fileToReplace = "";



			//================== [ Forge Mod.java Replacement] ==================
			List<String> blockNames = findNames(sfGenCode, EnumObjectType.Block);
			List<String> blockFlowerNames = findNames(sfGenCode, EnumObjectType.BlockFlower);
			List<String> blockPlantNames = findNames(sfGenCode, EnumObjectType.BlockPlant);

			List<String> itemNames = findNames(sfGenCode, EnumObjectType.Item);
			
			List<String> entityNames = findNames(sfGenCode, EnumObjectType.Entity);

			PLog.info("blockNames:" + blockNames.size());
			PLog.info("blockFlowerNames:" + blockFlowerNames.size());
			PLog.info("blockPlantNames:" + blockPlantNames.size());
			PLog.info("itemNames:" + itemNames.size());


			fileToReplace = JavaHelper.readFile(new File(projectFolder,"ForgeMod.java"));
			fileToReplace = fileToReplace.replace("/*Mod Package*/", JavaHelper.makeJavaId(main.MOD_NAME));


			fileToReplace = fileToReplace.replace("/*Mod Template*/", main.MOD_NAME);


			//write the file
			JavaHelper.writeFile(new File(projectFolder, "ForgeMod.java"), fileToReplace);
			//=============================== [ END ] ===============================



			//================== [ Forge CommonProxy.java Replacement] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"CommonProxy.java"));

			fileToReplace = fileToReplace.replace("/*Mod Package*/", JavaHelper.makeJavaId(main.MOD_NAME));

			fileToReplace = fileToReplace.replace("/*Variables - Block*/", variables(blockNames, EnumObjectType.Block));
			fileToReplace = fileToReplace.replace("/*Constructor calls - Block*/", constructorCalls(blockNames, EnumObjectType.Block));

			fileToReplace = fileToReplace.replace("/*Variables - BlockFlower*/", variables(blockFlowerNames, EnumObjectType.BlockFlower));
			fileToReplace = fileToReplace.replace("/*Constructor calls - BlockFlower*/", constructorCalls(blockFlowerNames, EnumObjectType.BlockFlower));
			fileToReplace = fileToReplace.replace("/*WorldGen - Overworld - Flowers*/", worldGenCalls(blockFlowerNames, EnumObjectType.BlockFlower));
			
			fileToReplace = fileToReplace.replace("/*Variables - BlockPlant*/", variables(blockPlantNames, EnumObjectType.BlockPlant));
			fileToReplace = fileToReplace.replace("/*Constructor calls - BlockPlant*/", constructorCalls(blockPlantNames, EnumObjectType.BlockPlant));
			fileToReplace = fileToReplace.replace("/*WorldGen - Overworld - Plant*/", worldGenCalls(blockPlantNames, EnumObjectType.BlockPlant));

			fileToReplace = fileToReplace.replace("/*Variables - Item*/", variables(itemNames, EnumObjectType.Item));
			fileToReplace = fileToReplace.replace("/*Constructor calls - Item*/", constructorCalls(itemNames, EnumObjectType.Item));

			
			fileToReplace = fileToReplace.replace("/*Classes*/", fixCode(sfGenCode));
			
			double sx = 1, sy = 1, sz = 1, tx = 0, ty = 0, tz = 0;
			ThreeDoubleClass scale = getThreeDoubles(sfGenCode, "Scale");
			if(scale != null) {
				sx = scale.x;
				sy = scale.y;
				sz = scale.z;
			}
			
			ThreeDoubleClass translate = getThreeDoubles(sfGenCode, "Translate");
			if(translate != null) {
				tx = translate.x;
				ty = translate.y;
				tz = translate.z;
			}
			
			List<String> models = getEntityModelString(fileToReplace);
			
			fileToReplace = fileToReplace.replace("/*Constructor calls - Entity*/", registerEntityCalls(entityNames, EnumObjectType.Entity, models));

			JavaHelper.writeFile(new File(projectFolder, "CommonProxy.java"), fileToReplace);
			//=============================== [ END ] ===============================



			//================== [ Forge ClientProxy.java Replacement] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"ClientProxy.java"));
			fileToReplace = fileToReplace.replace("/*Mod Package*/", JavaHelper.makeJavaId(main.MOD_NAME));
			fileToReplace = fileToReplace.replace("/*Entity Rendering*/", registerEntityRenderer(entityNames, EnumObjectType.Entity, models, sx, sy, sz, tx, ty, tz));
			JavaHelper.writeFile(new File(projectFolder, "ClientProxy.java"), fileToReplace);
			//=============================== [ END ] ===============================



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

	private List<String> findNames(String code, EnumObjectType type) {
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("class Mc" + type.clazz + "_(.*?) extends " + type.extendz);

		Matcher matcher = pattern.matcher(code);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}

		return result;
	}
	
	private List<String> getEntityModelString(String code) {
		Pattern pattern = Pattern.compile("public static final String MODEL = \"(.*?)\";");

		List<String> result = new ArrayList<String>();
		
		Matcher matcher = pattern.matcher(code);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		return result;
	}
	
	private ThreeDoubleClass getThreeDoubles(String code, String lookFor) {
		Pattern pattern = Pattern.compile("\\/\\/" + lookFor + " (.*?),(.*?),(.*?);");

		Matcher matcher = pattern.matcher(code);
		ThreeDoubleClass result = new ThreeDoubleClass();
		if(matcher.find()) {
			result.x = Double.parseDouble(matcher.group(1));
			result.y = Double.parseDouble(matcher.group(2));
			result.z = Double.parseDouble(matcher.group(3));
			return result;
		}
		
		return null;
		
	}
	
	 class ThreeDoubleClass{
		public double x;
		public double y;
		public double z;
	}

	private String variables(List<String> blockNames, EnumObjectType type) {
		String result = "";

		for (String blockName: blockNames) {
			result += "static " + className(blockName, type) + " " + variableName(blockName, type) + ";" + "\n";
		}

		return result;
	}


	private String constructorCalls(List<String> names, EnumObjectType type) {
		String result = "";

		for (String name: names) {
			result += variableName(name, type) + " = new " + className(name, type) + "();" + "\n";
		}

		return result;
	}
	
	private String registerEntityCalls(List<String> names, EnumObjectType type, List<String> model) {
		String result = "";

		for(int i = 0; i < names.size(); i++) {
			String className = className(names.get(i), type);
			result += "createEntity(" + className + ".class, " + className + ".RAW_NAME, " + className + ".NAME, " + className + ".EGG_P, " + className + ".EGG_S); //" + model.get(i) + "\n";
		}

		return result;
	}
	
	private String registerEntityRenderer(List<String> names, EnumObjectType type, List<String> model, double sx, double sy, double sz, double tx, double ty, double tz) {
		String result = "";
		
		for(int i = 0; i < names.size(); i++) {
			result += "RenderingRegistry.registerEntityRenderingHandler(" + className(names.get(i), type) + ".class, new CustomEntityRenderer(new Model" + model.get(i) + "(), \"" + variableName(names.get(i), type).toLowerCase() + "\", " + sx + ", " + sy + ", " + sz + ", " + tx + ", " + ty + ", " + tz + "));\n";
		}
		
		return result;
	}

	private String worldGenCalls(List<String> names, EnumObjectType type) {
		String result = "";
		//PLog.info("====WorldGen====");
		//PLog.info("  Type: " + type.name());
		//PLog.info("  Names: " + names.toString() );
		if(type == EnumObjectType.BlockFlower) {
			for (String name: names) {
				result += "(new WorldGenFlowers(" + variableName(name, type) + ")).generate(world, random, x, y, z); \n";
			}
		}
		else if(type == EnumObjectType.BlockPlant) {
			for (String name: names) {
				result += "(new WorldGenCustomPlant(" + variableName(name, type) + ")).generate(world, random, x, y, z); \n";
			}
		}
		//PLog.info("Result: " + result);
		//PLog.info(" ");

		return result;
	}


	private String fixCode(String code) {
		code = code.replace("package delete_me;", "");
		code = code.replace("public class MyApp {", "");
		code = code.substring(0, code.length() - 4);

		
		code = code.replace("BLOCK_ID", "ForgeMod.BLOCK_ID");
		code = code.replace("CREATIVE_TAB", "ForgeMod.CREATIVE_TAB");
		return code;
	}

	private String variableName(String name, EnumObjectType type)
	{
		return "mc" + type.clazz + "_" + name;
	}

	private String className(String name, EnumObjectType type)
	{
		return "Mc" + type.clazz + "_" + name;
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

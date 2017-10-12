package org.golde.java.scratchforge;

import java.io.File;
import java.util.List;

import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.helpers.codeparser.CodeComponent;
import org.golde.java.scratchforge.helpers.codeparser.CodeParser;

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
		Block("block"),
		BlockFlower("blockFlower"),
		BlockPlant("blockPlant"),
		Item("item"),
		Entity("entity"),
		Command("command"),
		Recipes("recipe")
		;

		public final String clazz;
		EnumObjectType(String clazz){
			this.clazz = clazz;
		}

	}

	public void run(String sfGenCode) {
		PLog.info("Fixing code....");
		String projectName = main.MOD_NAME.replace(" ", "_");
		try {
			CodeParser codeParser = new CodeParser();
			codeParser.parseCode(fixCode(sfGenCode));
			

			//Setup basic variables
			File projectFolder = new File(forgeModsIn, JavaHelper.makeJavaId(projectName));
			JavaHelper.copyEverythingInAFolder(new File(forgeScratch, "Template"), projectFolder);
			String fileToReplace = "";


			List<CodeComponent> allComponents = codeParser.getComponents();
			List<CodeComponent> blockComponents = findComponents(codeParser, EnumObjectType.Block);
			List<CodeComponent> blockFlowerComponents = findComponents(codeParser, EnumObjectType.BlockFlower);
			List<CodeComponent> blockPlantComponents = findComponents(codeParser, EnumObjectType.BlockPlant);
			List<CodeComponent> itemComponents = findComponents(codeParser, EnumObjectType.Item);
			List<CodeComponent> entityComponents = findComponents(codeParser, EnumObjectType.Entity);
			List<CodeComponent> commandComponents = findComponents(codeParser, EnumObjectType.Command);
			List<CodeComponent> recipeComponents = findComponents(codeParser, EnumObjectType.Recipes);

			//================== [ Forge Mod.java Replacement] ==================
			
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"ForgeMod.java"));
			fileToReplace = fileToReplace.replace("/*Mod Package*/", JavaHelper.makeJavaId(main.MOD_NAME));


			fileToReplace = fileToReplace.replace("/*Mod Template*/", main.MOD_NAME);


			//write the file
			JavaHelper.writeFile(new File(projectFolder, "ForgeMod.java"), fileToReplace);
			//=============================== [ END ] ===============================



			//================== [ Forge CommonProxy.java Replacement] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"CommonProxy.java"));

			fileToReplace = fileToReplace.replace("/*Mod Package*/", JavaHelper.makeJavaId(main.MOD_NAME));

			fileToReplace = fileToReplace.replace("/*Variables - Block*/", variables(blockComponents));
			fileToReplace = fileToReplace.replace("/*Constructor calls - Block*/", constructorCalls(blockComponents));

			fileToReplace = fileToReplace.replace("/*Variables - BlockFlower*/", variables(blockFlowerComponents));
			fileToReplace = fileToReplace.replace("/*Constructor calls - BlockFlower*/", constructorCalls(blockFlowerComponents));
			fileToReplace = fileToReplace.replace("/*WorldGen - Overworld - Flowers*/", worldGenCalls(blockFlowerComponents));
			
			fileToReplace = fileToReplace.replace("/*Variables - BlockPlant*/", variables(blockPlantComponents));
			fileToReplace = fileToReplace.replace("/*Constructor calls - BlockPlant*/", constructorCalls(blockPlantComponents));
			fileToReplace = fileToReplace.replace("/*WorldGen - Overworld - Plant*/", worldGenCalls(blockPlantComponents));

			fileToReplace = fileToReplace.replace("/*Variables - Item*/", variables(itemComponents));
			fileToReplace = fileToReplace.replace("/*Constructor calls - Item*/", constructorCalls(itemComponents));

			fileToReplace = fileToReplace.replace("/*Constructor calls - Command*/", registerCommandConstructors(commandComponents));
			
			fileToReplace = fileToReplace.replace("/*Recipes*/", placeGenericCode(recipeComponents));
			
			fileToReplace = fileToReplace.replace("/*Classes*/", placeClasses(allComponents, EnumObjectType.Recipes));
			
			fileToReplace = fileToReplace.replace("/*Constructor calls - Entity*/", registerEntityCalls(entityComponents));

			JavaHelper.writeFile(new File(projectFolder, "CommonProxy.java"), fileToReplace);
			//=============================== [ END ] ===============================



			//================== [ Forge ClientProxy.java Replacement] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"ClientProxy.java"));
			fileToReplace = fileToReplace.replace("/*Mod Package*/", JavaHelper.makeJavaId(main.MOD_NAME));
			fileToReplace = fileToReplace.replace("/*Entity Rendering*/", registerEntityRenderer(entityComponents));
			JavaHelper.writeFile(new File(projectFolder, "ClientProxy.java"), fileToReplace);
			//=============================== [ END ] ===============================



			main.modManager.scanDirectoriesForMods();

		}
		catch(Exception e) {
			PLog.error(e, "Failed to make mod!");
			showToast(EnumToast.ERROR_PROGRAM, "Failed to make mod:" + e.getMessage());
		}

		try {
			JavaHelper.runCMD(forgeDir, "\"" + javaHome + "/bin/java.exe\" -Xincgc -Xmx4G -Xms4G \"-Dorg.gradle.appname=gradlew\" -classpath \"gradle\\wrapper\\gradle-wrapper.jar\" org.gradle.wrapper.GradleWrapperMain runClient" + (main.offlineMode ? " --offline" : ""), false);
			showToast(EnumToast.SUCCESS, "Starting Minecraft...");
		}
		catch(Exception e) {
			PLog.error(e, "Failed to start forge!");
			showToast(EnumToast.ERROR_PROGRAM, "Failed to start forge: " + e.getMessage());
		}
	}
	
	private List<CodeComponent> findComponents(CodeParser codeParser, EnumObjectType type)
	{
		return codeParser.getComponentsOfType(type.clazz);
	}

	private String variables(List<CodeComponent> blockComponents) {
		String result = "";

		for (CodeComponent component: blockComponents) {
			result += "static " + className(component) + " " + variableName(component) + ";" + "\n";
		}

		return result;
	}

	private String placeGenericCode(List<CodeComponent> components) {
		String result = "";

		for (CodeComponent component: components) {
			result += component.getCode();
		}

		return result;		
	}
	
	private String placeClasses(List<CodeComponent> components, EnumObjectType... ignore) {
		String result = "";

		ComponentLoop: for(CodeComponent component: components) {
			for(EnumObjectType type:ignore) {
				if(component.getType().equals(type.clazz)) {
					continue ComponentLoop;
				}
			}
			result += component.getCode();
		}

		return result;		
	}

	private String constructorCalls(List<CodeComponent> components) {
		String result = "";

		for (CodeComponent component: components) {
			result += variableName(component) + " = new " + className(component) + "();" + "\n";
		}

		return result;
	}
	
	/*private String constructorCallsWithoutVariable(List<CodeComponent> components) {
		String result = "";

		for (CodeComponent component: components) {
			result += "new " + className(component) + "();" + "\n";
		}

		return result;
	}*/
	
	private String registerCommandConstructors(List<CodeComponent> components) {
		String result = "";

		for (CodeComponent component: components) {
			result += "event.registerServerCommand(new " + className(component) + "());" + "\n";
		}

		return result;
	}
	
	private String registerEntityCalls(List<CodeComponent> components) {
		String result = "";

		for (CodeComponent component: components) {
			String className = className(component);
			String modelName = component.getValueAsString("model", null);
			result += "createEntity(" + className + ".class, " + className + ".RAW_NAME, " + className + ".NAME, " + className + ".EGG_P, " + className + ".EGG_S); //" + modelName + "\n";
		}

		return result;
	}
	
	private String registerEntityRenderer(List<CodeComponent> components) {
		String result = "";
		
		for (CodeComponent component: components) {
			String className = className(component);
			String modelName = component.getValueAsString("model", null);
			double sx = component.getValueAsDouble("scalex", 1.0);
			double sy = component.getValueAsDouble("scaley", 1.0);
			double sz = component.getValueAsDouble("scalez", 1.0);
			double tx = component.getValueAsDouble("translatex", 0.0);
			double ty = component.getValueAsDouble("translatey", 0.0);
			double tz = component.getValueAsDouble("translatez", 0.0);
			result += "RenderingRegistry.registerEntityRenderingHandler(" + className + ".class, new CustomEntityRenderer(new Model" + modelName + "(), \"" + variableName(component).toLowerCase() + "\", " + sx + ", " + sy + ", " + sz + ", " + tx + ", " + ty + ", " + tz + "));\n";
		}
		
		return result;
	}

	private String worldGenCalls(List<CodeComponent> components) {
		String result = "";
		
		for (CodeComponent component: components) {
			if (component.getType().equals(EnumObjectType.BlockFlower.clazz)) {
				result += "(new WorldGenFlowers(" + variableName(component) + ")).generate(world, random, x, y, z); \n";
			}
			else if (component.getType().equals(EnumObjectType.BlockPlant.clazz)) {
				result += "(new WorldGenCustomPlant(" + variableName(component) + ")).generate(world, random, x, y, z); \n";
			}
		}
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

	private String variableName(CodeComponent component) {
		return "mc" + component.getType() + "_" + component.getName();
	}

	private String className(CodeComponent component) {
		return "Mc" + component.getType() + "_" + component.getName();
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
	
	enum EnumToast{
		ERROR_PROGRAM(0), ERROR_BLOCKS(1), SUCCESS(2), WARNING(3), UPDATE(4);
		
		public final int id;
		EnumToast(int id){
			this.id = id;
		}
	};
	
	public void showToast(EnumToast type, String message) {
		javaApp.call("sendToast", type.id, message);
	}

}

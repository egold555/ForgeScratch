package org.golde.java.scratchforge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.helpers.codeparser.CodeComponent;
import org.golde.java.scratchforge.helpers.codeparser.CodeParser;
import org.golde.java.scratchforge.mod.Mod.Texture;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.application.Platform;
import netscape.javascript.JSObject;

/**
 * The class that JSObject comunicates too and calls functions to
 * @author Eric
 *
 */
public class JSFunctions {

	private File forgeDir;
	private File forgeScratch;
	private File forgeModsIn;
	private File forgeAssets;
	private File modAssets;
	private JSObject javaApp;
	private String javaHome = System.getenv("JAVA_HOME");
	private String MOD_NAME;

	public JSFunctions() {
		this.javaApp = Main.getInstance().window;
		this.forgeDir = Main.getInstance().forge_folder;
		this.forgeScratch = new File(forgeDir, "forgescratch");
		this.forgeModsIn = new File(forgeDir, "src\\main\\java\\org\\golde\\forge\\scratchforge\\mods");
		this.forgeAssets = new File(forgeDir, "src\\main\\resources\\assets");
		
	}

	public String saveXML() {
		createMod();

		String blocklyXML = (String) javaApp.call("saveXML");

		String textures = "";
		for(Texture texture: Main.getInstance().modManager.getMod(Main.getInstance().MOD_NAME).getTextures()) {
			if (texture.hasBeenCreated()) {
				textures += "        <texture name=\"" + texture.getRelativePath() +  "\">" + JavaHelper.base64EncodeFile(texture.getFile()) + "</texture>\n";
			}
		}

		String sfXML = 
				"<?xml version=\"1.0\"?>\n" + 
						"<ScratchForge version= \"" + Main.getInstance().VERSION + "\">\n" +
						"    <modName>" + Main.getInstance().MOD_NAME + "</modName>\n" +
						"    <textures>\n" +
						textures + 
						"    </textures>\n" +
						"    <blockly>" + blocklyXML + "</blockly>\n" + 
						"</ScratchForge>";


		return sfXML;
	}

	public void load(String xml) throws Exception{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(JavaHelper.stringToInputStream(xml));
		doc.getDocumentElement().normalize();

		Element rootElement = (Element) doc.getDocumentElement();
		Main.getInstance().MOD_NAME = ((Element) rootElement.getElementsByTagName("modName").item(0)).getTextContent();

		String version = rootElement.getAttribute("version");
		if(!Main.getInstance().VERSION.equals(version)) {
			showToast(EnumToast.WARNING, "Your version (" + Main.getInstance().VERSION + ") does not match the block mod version (" + version + "). Expect bugs!");
		}

		File rootDirectory = new File(forgeAssets, "sf_" + JavaHelper.makeJavaId(Main.getInstance().MOD_NAME) + "\\textures");
		Element textures = (Element) (rootElement.getElementsByTagName("textures").item(0));

		NodeList textureList = textures.getElementsByTagName("texture");
		for(int i = 0; i < textureList.getLength(); i++) {
			if (textureList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)(textureList.item(i));
				String name = element.getAttribute("name");
				File file = new File(rootDirectory, name);
				file.getParentFile().mkdirs();
				String base64 = element.getTextContent();
				JavaHelper.base64DecodeFile(base64, file);
			}
		}

		String blockly = nodeToString(((Element)rootElement.getElementsByTagName("blockly").item(0)).getElementsByTagName("xml").item(0));
		javaApp.call("loadXML", blockly);
	}

	private static String nodeToString(Node node) throws Exception{
		StringWriter sw = new StringWriter();
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.transform(new DOMSource(node), new StreamResult(sw));
		return sw.toString();
	}

	private enum EnumObjectType{
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

	private void createMod()
	{
		createModFromCode((String) javaApp.call("getBlocklyCode"));
	}

	private void createModFromCode(String sfGenCode)
	{
		PLog.info("Fixing code....");
		MOD_NAME = JavaHelper.makeJavaId(Main.getInstance().MOD_NAME);
		this.modAssets = new File(forgeAssets, "sf_" + MOD_NAME);
		try {
			CodeParser codeParser = new CodeParser();
			codeParser.parseCode(fixCode(sfGenCode));


			//Setup basic variables
			File projectFolder = new File(forgeModsIn, MOD_NAME);
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
			fileToReplace = fileToReplace.replace("/*Mod Package*/", MOD_NAME);

			fileToReplace = fileToReplace.replace("/*Mod Template*/", Main.getInstance().MOD_NAME);


			//write the file
			JavaHelper.writeFile(new File(projectFolder, "ForgeMod.java"), fileToReplace);
			//=============================== [ END ] ===============================

			
			//================== [ Mod Items.java Replacement] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"ModItems.java"));
			fileToReplace = fileToReplace.replace("/*Mod Package*/", MOD_NAME);

			fileToReplace = fileToReplace.replace("/*Variables - Item*/", variables(itemComponents));
			
			//write the file
			JavaHelper.writeFile(new File(projectFolder, "ModItems.java"), fileToReplace);
			//=============================== [ END ] ===============================
			
			//================== [ Mod Blocks.java Replacement] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"ModBlocks.java"));
			fileToReplace = fileToReplace.replace("/*Mod Package*/", MOD_NAME);

			//write the file
			JavaHelper.writeFile(new File(projectFolder, "ModBlocks.java"), fileToReplace);
			//=============================== [ END ] ===============================


			//================== [ Forge CommonProxy.java Replacement ] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"CommonProxy.java"));

			fileToReplace = fileToReplace.replace("/*Mod Package*/", MOD_NAME);

			fileToReplace = fileToReplace.replace("/*Variables - Block*/", variables(blockComponents));
			fileToReplace = fileToReplace.replace("/*Constructor calls - Block*/", constructorCalls(blockComponents));

			fileToReplace = fileToReplace.replace("/*Variables - BlockFlower*/", variables(blockFlowerComponents));
			fileToReplace = fileToReplace.replace("/*Constructor calls - BlockFlower*/", constructorCalls(blockFlowerComponents));
			fileToReplace = fileToReplace.replace("/*WorldGen - Overworld - Flowers*/", worldGenCalls(blockFlowerComponents));

			fileToReplace = fileToReplace.replace("/*Variables - BlockPlant*/", variables(blockPlantComponents));
			fileToReplace = fileToReplace.replace("/*Constructor calls - BlockPlant*/", constructorCalls(blockPlantComponents));
			fileToReplace = fileToReplace.replace("/*WorldGen - Overworld - Plant*/", worldGenCalls(blockPlantComponents));

			
			fileToReplace = fileToReplace.replace("/*Constructor calls - Item*/", constructorCalls(itemComponents));

			fileToReplace = fileToReplace.replace("/*Constructor calls - Command*/", registerCommandConstructors(commandComponents));

			fileToReplace = fileToReplace.replace("/*Recipes*/", placeGenericCode(recipeComponents));

			fileToReplace = fileToReplace.replace("/*Classes*/", placeClasses(allComponents, EnumObjectType.Recipes));

			fileToReplace = fileToReplace.replace("/*Constructor calls - Entity*/", registerEntityCalls(entityComponents));

			JavaHelper.writeFile(new File(projectFolder, "CommonProxy.java"), fileToReplace);
			//=============================== [ END ] ===============================



			//================== [ Forge ClientProxy.java Replacement] ==================
			fileToReplace = JavaHelper.readFile(new File(projectFolder,"ClientProxy.java"));
			fileToReplace = fileToReplace.replace("/*Mod Package*/", MOD_NAME);
			fileToReplace = fileToReplace.replace("/*Entity Rendering*/", registerEntityRenderer(entityComponents));
			JavaHelper.writeFile(new File(projectFolder, "ClientProxy.java"), fileToReplace);
			//=============================== [ END ] ===============================

			writeComponentJson(EnumJsonType.ITEM, itemComponents);

			Main.getInstance().modManager.scanDirectoriesForMods();

		}
		catch(Exception e) {
			PLog.error(e, "Failed to make mod!");
			showToast(EnumToast.ERROR_PROGRAM, "Failed to make mod:" + e.getMessage());
		}


	}

	

	public void run(String sfGenCode) {
		createModFromCode(sfGenCode);
		pause(true);
		
		//The crazy hacky ways that I come up with to get the program to do what....
		new Thread() {
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Platform.runLater(() -> {
					try {
						JavaHelper.runCMD(forgeDir, "\"" + javaHome + "/bin/java.exe\" -Xincgc -Xmx4G -Xms4G \"-Dorg.gradle.appname=gradlew\" -classpath \"gradle\\wrapper\\gradle-wrapper.jar\" org.gradle.wrapper.GradleWrapperMain runClient" + (Main.getInstance().offlineMode ? " --offline" : ""));
						pause(false);
					}
					catch(Exception e) {
						pause(false);
						PLog.error(e, "Failed to start forge!");
						showToast(EnumToast.ERROR_PROGRAM, "Failed to start forge: " + e.getMessage());
					}
				});

			}
		}.start();

	}

	private List<CodeComponent> findComponents(CodeParser codeParser, EnumObjectType type)
	{
		return codeParser.getComponentsOfType(type.clazz);
	}

	private String variables(List<CodeComponent> blockComponents) {
		String result = "";

		for (CodeComponent component: blockComponents) {
			//result += "static " + className(component) + " " + variableName(component) + ";" + "\n";
			result += "@GameRegistry.ObjectHolder(ForgeMod.MOD_ID + \":" + variableName(component) + "\")\n" + "public static " + className(component) + " " + variableName(component) + ";" + "\n\n";
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
			//result += variableName(component) + " = new " + className(component) + "();" + "\n";
			result += "event.getRegistry().register(new " + className(component) + "());" + "\n";
		}

		return result;
	}

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
	
	private void writeComponentJson(EnumJsonType type, List<CodeComponent> components) throws IOException {
		for(CodeComponent component:components) {
			String json = getJson(type, component.getName());
			JavaHelper.writeFile(new File(new File(modAssets, "models/" + type.toString()), component.getName().toLowerCase()+ ".json"), json);
		}
		
	}
	
	private enum EnumJsonType{
		ITEM;
		
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
	private String getJson(EnumJsonType type, String name) throws IOException {
		String json = JavaHelper.readFile(new File(new File(new File(forgeScratch, "Template Assets"), "default/json"), type.toString() + ".json"));
		json = json.replace("*name*", name.toLowerCase());
		json = json.replace("*mod*", "sf_" + MOD_NAME.toLowerCase());
		return json;
	}

	private String variableName(CodeComponent component) {
		return "mc" + component.getType() + "_" + component.getName();
	}

	private String className(CodeComponent component) {
		return "Mc" + component.getType() + "_" + component.getName();
	}

	public void log(String msg) {
		PLog.info("[JS] " + msg);
	}

	public void displayFSError(String msg) {
		log("[FS-Error] " + msg);
	}

	public enum EnumToast{
		ERROR_PROGRAM(0), ERROR_BLOCKS(1), SUCCESS(2), WARNING(3), UPDATE(4), INFO(5);

		public final int id;
		EnumToast(int id){
			this.id = id;
		}
	};

	public void showToast(EnumToast type, String message) {
		javaApp.call("sendToast", type.id, message);
	}

	public void pause(boolean value) {
		javaApp.call("togglePause", value);
	}



}

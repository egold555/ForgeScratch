package org.golde.java.scratchforge.mod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.golde.java.scratchforge.helpers.ImageTool;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;

public class Mod {
	private ModManager modManager;
	private String modName;
	private File modFile;
	private List<Texture> textures;
	private boolean enabled;
	
	public Mod(ModManager modManager, File file, boolean enabled) {
		this.modManager = modManager;
		this.modFile = file;
		this.enabled = enabled;
		
		scanModFile();
		
		/*PLog.info("Read mod file: " + file.getAbsolutePath());
		PLog.info("Enabled: " + enabled);
		PLog.info("Mod name: " + getModName());
		PLog.info("Prefix: " + getPrefix());
		PLog.info("Textures: ");
		for (Texture t: getTextures()) {
			PLog.info("  displayName: " + t.getDisplayName());
			PLog.info("  textureName: " + t.getTextureName());
			PLog.info("  file: " + t.getFile().getAbsolutePath());
			PLog.info("");
		}*/
	}

	public String getModName() {
		return modName;
	}
	
	public String getPrefix()
	{
		return "sf_" + JavaHelper.makeJavaId(modName);
	}

	public File getModFile() {
		return modFile;
	}

	public Texture[] getTextures() {
		return textures.toArray(new Texture[0]);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void delete() {
		modFile.delete();
		modManager.removeMod(this);
	}
	
	@Override
	public String toString() {
		return modName;
	}
	
	public void setEnabled(boolean enabled) {
		File newFile;
		if (enabled == true && this.enabled == false) {
			// Move mod to the enabled folder.
			newFile = new File(modManager.forgeModsIn, modFile.getName());
		}
		else if (enabled == false && this.enabled == true) {
			// Move mod to the disabled folder.
			newFile = new File(modManager.forgeModsOut, modFile.getName());
		}
		else {
			return;
		}
		
		// Move the file.
		modFile.renameTo(newFile);
		modFile = newFile;
		this.enabled = enabled;
	}
	
	private void scanModFile()
	{
		List<String> lines;
		
		
		try {
			lines = Files.readAllLines(modFile.toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		textures = new ArrayList<Texture>();
		
		for(String line: lines) {
			if (line.contains("public static final String MOD_NAME")) {
				int startIndex = line.indexOf("\"") + 1;
				int endIndex = line.indexOf("\"", startIndex);
				this.modName = line.substring(startIndex, endIndex);
			}
			
			if (line.contains("super(BLOCK_ID, CREATIVE_TAB,")) {
				int startIndex = line.indexOf("\"") + 1;
				int endIndex = line.indexOf("\"", startIndex);
				String blockName = line.substring(startIndex, endIndex);
				textures.add(new Texture(blockName, true));
				
			}
		}
	}

	
	public class Texture {
		private String textureName;
		private boolean isBlock;
		private File file;
		
		public Texture(String textureName, boolean isBlock) {
			this.textureName = textureName;
			this.isBlock = isBlock;
		
			file = new File(modManager.forgeDir, "src\\main\\resources\\assets\\" + getPrefix() + "\\textures\\" + (isBlock ? "blocks" : "items") + "\\" + getTextureName() + ".png");
		}

		public boolean hasBeenCreated() {
			return file.exists();
		}
		
		public File getFile() {
			return file;
		}
		
		public String getTextureName() {
			return JavaHelper.makeJavaId(textureName);
		}
		
		public String getDisplayName() {
			return textureName;
		}
		
		public boolean isBlock() {
			return isBlock;
		}
		
		public void createTexture() throws IOException {
			createTexture(16);
		}
		
		public void createTexture(int size) throws IOException {
			BufferedImage image = ImageTool.toBufferedImage(ImageTool.getEmptyImage(size, size));
			file.getParentFile().mkdirs();
			ImageIO.write(image, "png", file);
		}
		
		@Override
		public String toString() {
			return textureName + " (" + (hasBeenCreated() ? "Edit existing Image" : "CreateNew Image") + ")";
		}
		
	}
	
}

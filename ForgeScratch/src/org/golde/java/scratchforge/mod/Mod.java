package org.golde.java.scratchforge.mod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.golde.java.scratchforge.helpers.ImageTool;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;

public class Mod {
	private ModManager modManager;
	private String modName;
	private File modFile_CommonProxy;
	private File modFile_ForgeMod;
	private File modFolder;
	private List<Texture> textures;
	private File assetsDirectory;
	private boolean enabled;


	enum EnumTextureType {
		Block("blocks", 16, 16),
		Item("items", 16, 16),
		Entity_64_32("entity", 64, 32),
		Entity_64_64("entity", 64, 64),
		Entity_64_128("entity", 64, 128),
		Entity_128_128("entity", 128, 128),
		Entity_256_256("entity", 256, 256),
		;

		public final String folder;
		public final int x, y;
		EnumTextureType(String folder, int x, int y){
			this.folder = folder;
			this.x = x;
			this.y = y;
		}
	}

	public Mod(ModManager modManager, File file, boolean enabled) {
		this.modManager = modManager;
		this.modFolder = file;
		this.modFile_CommonProxy = new File(modFolder, "CommonProxy.java");
		this.modFile_ForgeMod = new File(modFolder, "ForgeMod.java");
		this.enabled = enabled;

		scanModFile();

		JavaHelper.copyFolder(new File(modManager.forgeScratch, "Template Assets"), assetsDirectory);
	}

	public String getModName() {
		return modName;
	}

	public String getDisplayName() {
		return (enabled ? "✔" : "✘") + " " + modName;
	}

	public String getPrefix()
	{
		return "sf_" + JavaHelper.makeJavaId(modName);
	}

	public File getModFile() {
		return modFile_CommonProxy;
	}

	public File getModFolder() {
		return modFolder;
	}

	public Texture[] getTextures() {
		return textures.toArray(new Texture[0]);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void delete() {
		JavaHelper.deleteDirectory(modFolder);
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
			newFile = new File(modManager.forgeModsIn, modFolder.getName());
		}
		else if (enabled == false && this.enabled == true) {
			// Move mod to the disabled folder.
			newFile = new File(modManager.forgeModsOut, modFolder.getName());
		}
		else {
			return;
		}

		// Move the file.
		try {
			Files.move(modFolder.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch(Exception e) {
			PLog.error(e, "Failed to toggle mod '" + modName + "' to " + enabled + "!");
		}
		modFolder = newFile;
		this.enabled = enabled;
	}

	private void scanModFile()
	{
		try {
			for(String line: Files.readAllLines(modFile_ForgeMod.toPath(), StandardCharsets.UTF_8)) {
				if (line.contains("public static final String MOD_NAME")) {
					int startIndex = line.indexOf("\"") + 1;
					int endIndex = line.indexOf("\"", startIndex);
					this.modName = line.substring(startIndex, endIndex);
				}
			}
			assetsDirectory = new File(modManager.forgeDir, "src\\main\\resources\\assets\\" + getPrefix());
			textures = new ArrayList<Texture>();
			for(String line: Files.readAllLines(modFile_CommonProxy.toPath(), StandardCharsets.UTF_8)) {
				if(line.contains("super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB,") || line.contains("createEntity(Mcentity_")) {
					int startIndex;
					int endIndex;
					String objName;
					if (line.contains("super(ForgeMod.BLOCK_ID, ForgeMod.CREATIVE_TAB,") && !line.contains("Spawn Egg")) {
						startIndex = line.indexOf("\"") + 1;
						endIndex = line.indexOf("\"", startIndex);
						objName = line.substring(startIndex, endIndex);
						if(line.contains("Material.")) {
							//Blocks
							//16 x 16
							textures.add(new Texture(objName, EnumTextureType.Block));
						}
						else {
							//Items
							//16 x 16
							textures.add(new Texture(objName, EnumTextureType.Item));
						}
					}
					else {
						//Entities
						/*startIndex = line.indexOf("createEntity(") + "createEntity(".length();
						endIndex = line.indexOf(".class", startIndex);
						objName = line.substring(startIndex, endIndex);

						if(line.contains("Dragon")) {
							//256 x 256
							textures.add(new Texture(objName, EnumTextureType.Entity_256_256));
						}
						else if(line.contains("IronGolem") || line.contains("Horse")) {
							//128 x 128
							textures.add(new Texture(objName, EnumTextureType.Entity_128_128));
						}
						else if(line.contains("Witch")) {
							//64 x 128
							textures.add(new Texture(objName, EnumTextureType.Entity_64_128));
						}
						else if(line.contains("Villager") || line.contains("Bat") || line.contains("Wither") || line.contains("Zombie")) {
							//64 x 64
							textures.add(new Texture(objName, EnumTextureType.Entity_64_64));
						}else {
							//64 x 32
							textures.add(new Texture(objName, EnumTextureType.Entity_64_32));
						}*/
						
					}
				}
			}

		} catch (IOException e) {
			PLog.error(e, "Failed to read mod!");
			return;
		}
	}

	public class Texture {
		private String textureName;
		private EnumTextureType type;
		private File file;

		public Texture(String textureName, EnumTextureType type) {
			this.textureName = textureName;
			this.type = type;

			file = new File(assetsDirectory, "textures\\" + type.folder + "\\" + getTextureName() + ".png");
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

		public EnumTextureType getType() {
			return type;
		}

		public void createTexture() throws IOException {
			BufferedImage image = ImageTool.toBufferedImage(ImageTool.getEmptyImage(type.x, type.y));
			file.getParentFile().mkdirs();
			ImageIO.write(image, "png", file);
		}

		@Override
		public String toString() {
			return textureName + " (" + (hasBeenCreated() ? "Edit existing Image" : "Create New Image") + ")";
		}

	}

}
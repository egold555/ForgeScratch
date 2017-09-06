package org.golde.java.scratchforge.mod;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.golde.java.scratchforge.Main;
import org.golde.java.scratchforge.helpers.JavaHelper;

public class ModManager {
	private Set<Mod> mods;
	//private Main main;

	public final File forgeDir;
	private final File forgeScratch;
	public final File forgeModsIn;
	public final File forgeModsOut;

	public ModManager(Main main)
	{
		//this.main = main;
		this.forgeDir = main.forge_folder;
		this.forgeScratch = new File(forgeDir, "forgescratch");
		this.forgeModsIn = new File(forgeDir, "src\\main\\java\\org\\golde\\forge\\scratchforge\\mods");
		this.forgeModsOut = new File(forgeScratch, "unusedMods");

		scanDirectoriesForMods();
	}
	
	public Mod[] allMods()
	{
		return mods.toArray(new Mod[0]);
	}
	
	public Mod getMod(String modNameToFind)
	{
		for (Mod mod: mods) {
			if (mod.getModName().equalsIgnoreCase(modNameToFind))
				return mod;
		}
		
		return null;
	}
	
	public void scanDirectoriesForMods()
	{
		mods = new HashSet<Mod>();
		
		for(File mod: JavaHelper.listFilesForFolder(forgeModsIn)) {
			mods.add(new Mod(this, mod, true));
		}
		for(File mod: JavaHelper.listFilesForFolder(forgeModsOut)) {
			mods.add(new Mod(this, mod, false));
		}
	}
	
	void removeMod(Mod mod)
	{
		mods.remove(mod);
	}
	
	
}

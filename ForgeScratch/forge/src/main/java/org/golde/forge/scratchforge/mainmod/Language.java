package org.golde.forge.scratchforge.mainmod;

import net.minecraft.util.text.TextFormatting;

public class Language {

	public static final String TITLE_INSTALLED = TextFormatting.LIGHT_PURPLE + "ScratchForge installed";
	public static final String CHAT_MISSING_TEXTURES = TextFormatting.GOLD + "Hey! It seems like you do not have textures for "+ TextFormatting.WHITE + "%failed%" + TextFormatting.GOLD + "." ;
	public static final String[] LAN_ALERT = {"Before you attempt to have other's join your lan world, you must make sure ", "that the other people have your mod. If they do not have your mod, they can not play."};
	public static final String[] LAN_REJECT = {"Looks like you do not have the required .blockmod to play!", "Please ask the owner of this lan world to give you the", ".blockmod and then launch your game with their mod."};
	public static final String[] V_1_7_10_WORLD = {"Looks like you tried to load a world from 1.7 or before!", "1.12 does not support these old worlds.", "Please convert them to 1.8 or greater before playing."};
	
}

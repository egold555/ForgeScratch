package org.golde.forge.scratchforge.tutorialmod;

import net.minecraft.util.EnumChatFormatting;

public enum EnumTutorialProgress {
	
	START(1, null, "Congragulations, you have successfully launched the game! Exit the game to start step 2."), 
	BLOCKS(2, null, null),
	FINISH(100, "You have finished all the tutorials! Exit the game to start creating amazing projects with ScratchForge!", null);
	
	private final int id;
	private final String instructions;
	private final String successMessage;
	private final String PREFIX = "[Tutorial] ";
	EnumTutorialProgress(int id, String instructions,  String successMessage) {
		this.id = id;
		this.instructions = instructions;
		this.successMessage = successMessage;
	}
	
	public String getInstructions() {
		return instructions == null ? null : EnumChatFormatting.AQUA + PREFIX + instructions;
	}
	
	public String getSuccessMessage() {
		return successMessage == null ? null : EnumChatFormatting.GREEN + PREFIX + successMessage;
	}
	
	public int getId() {
		return id;
	}
	
	public static EnumTutorialProgress get(int inputId) {
		for(EnumTutorialProgress tp:EnumTutorialProgress.values()) {
			if(tp.id == inputId) {return tp;}
		}
		return null;
	}
}

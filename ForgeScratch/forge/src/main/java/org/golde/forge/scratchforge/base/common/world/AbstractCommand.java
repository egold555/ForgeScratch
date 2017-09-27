package org.golde.forge.scratchforge.base.common.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractCommand implements ICommand {

	public abstract void run(EntityPlayer player, String[] args);
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName();
	}

	@Override
	public List getCommandAliases() {
		return new ArrayList<String>();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(sender instanceof EntityPlayer) {
			run((EntityPlayer)sender, args);
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		return new ArrayList<String>();
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int index) {
		return false;
	}

}

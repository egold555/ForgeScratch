package org.golde.forge.scratchforge.base.common.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractCommand implements ICommand {

	public abstract void run(EntityPlayer player, String[] args);

	@Override
	public boolean checkPermission(MinecraftServer arg0, ICommandSender arg1) { //TODO: Implement permissions
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender instanceof EntityPlayer) {
			run((EntityPlayer)sender, args);
		}
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer arg0, ICommandSender arg1, String[] arg2, BlockPos arg3) {
		return new ArrayList<String>();
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		return "/" + getName();
	}

	@Override
	public int compareTo(ICommand o) { //What does this do?
		return 0;
	}

	@Override
	public boolean isUsernameIndex(String[] arg0, int arg1) { //What does this do?
		return false;
	}
	
	

}

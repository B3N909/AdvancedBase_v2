package me.savant.listener;

import me.savant.util.Database;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("base"))
		{
			if(args.length == 0)
			{
				p.sendMessage(ChatColor.GRAY + "/base database download || /base database upload");
				return true;
			}
			if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase("database"))
				{
					if(args[1].equalsIgnoreCase("download"))
					{
						Database.DownloadNew();
						p.sendMessage(ChatColor.GRAY + "Completed Database Download Sync!");
						return true;
					}
					else if(args[1].equalsIgnoreCase("upload"))
					{
						Database.UploadCurrent();
						p.sendMessage(ChatColor.GRAY + "Completed Database Upload Sync!");
						return true;
					}
					else
					{
						p.sendMessage(ChatColor.GRAY + "Unknown Sub Operation");
						return true;
					}
				}
				else
				{
					p.sendMessage(ChatColor.GRAY + "Unknown Operation");
					return true;
				}
			}
		}
		return false;
	}
	
}

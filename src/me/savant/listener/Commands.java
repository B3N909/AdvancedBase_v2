package me.savant.listener;

import me.savant.util.Chat;
import me.savant.util.Database;
import me.savant.util.Item;
import me.savant.util.Upgrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof ConsoleCommandSender)
		{
			if(cmd.getName().equalsIgnoreCase("upgrade"))
			{
				Player p = (Player) sender;
				if(args.length == 1)
				{
					p.getInventory().addItem(Upgrade.getEmeraldStack(1));
					p.sendMessage(ChatColor.GRAY + "Given!");
					return true;
				}
				if(args.length == 2)
				{
					if(args[0].equalsIgnoreCase("upgrade") || args[0].equalsIgnoreCase("emerald"))
					{
						int i = 0;
						try
						{
							i = Integer.parseInt(args[1]);
							p.sendMessage(ChatColor.GRAY + "Given!");
							p.getInventory().addItem(Upgrade.getEmeraldStack(i));
							return true;
						}
						catch (NumberFormatException e)
						{
							p.sendMessage(Chat.def + "Error " + Chat.red + "Invalid" + Chat.def + " number");
							return true;
						}
					}
					return true;
				}
				p.sendMessage(Chat.def + "/upgrade or /upgrade upgrade AMOUNT");
				return true;
			}
			if(cmd.getName().equalsIgnoreCase("part"))
			{
				if(args.length == 0)
				{
					return true;
				}
				if(args.length == 3)
				{
					Player args1 = null;
					for(Player p1 : Bukkit.getOnlinePlayers())
					{
						if(p1.getName().equalsIgnoreCase(args[1]))
							args1 = p1;
					}
					if(args1 == null)
					{
						return true;
					}
					int amount = 0;
					try
					{
						amount = Integer.parseInt(args[2]);
					}
					catch (NumberFormatException e1)
					{
						return true;
					}
					Item.giveItem(args1, args[0], amount);
					return true;
				}
			}
			return true;
		}
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("part") && p.isOp())
		{
			if(args.length == 0)
			{
				p.sendMessage(ChatColor.GRAY + "/part part_name || /part part_name player_name part_amount");
				return true;
			}
			if(args.length == 1)
			{
				p.sendMessage(ChatColor.GRAY + "Given!");
				Item.giveItem(p, args[0]);
			}
			if(args.length == 3)
			{
				Player args1 = null;
				for(Player p1 : Bukkit.getOnlinePlayers())
				{
					if(p1.getName().equalsIgnoreCase(args[1]))
						args1 = p1;
				}
				if(args1 == null)
				{
					p.sendMessage(ChatColor.GRAY + "Invalid Username");
					return true;
				}
				int amount = 0;
				try
				{
					amount = Integer.parseInt(args[2]);
				}
				catch (NumberFormatException e1)
				{
					p.sendMessage(ChatColor.GRAY + "Invalid Amount");
					return true;
				}
				Item.giveItem(args1, args[0], amount);
				p.sendMessage(ChatColor.GRAY + "Sent!");
				return true;
			}
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("base"))
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

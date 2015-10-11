package me.savant.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.savant.base.Modifier;
import me.savant.base.Schematic;
import me.savant.base.Tag;
import me.savant.cuboid.Cuboid;
import me.savant.util.Core;
import me.savant.util.Damage;
import me.savant.util.Database;
import me.savant.util.Item;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Place implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if(e.getBlock().getType().equals(Material.RED_SANDSTONE))
		{
			Player p = e.getPlayer();
			
			Schematic schematic = null;
			try {
				schematic = Schematic.parseSchematic(ChatColor.stripColor(e.getItemInHand().getItemMeta().getDisplayName()));			
			} catch (IOException e1) {
				return;
			}
			if(schematic != null)
			{
				String display_name = Item.getDisplayName(p.getItemInHand().getItemMeta().getDisplayName());
				
				Database.DownloadNew();
				if(display_name.equalsIgnoreCase("core") && Core.inAnyRange(e.getBlock()))
				{
					p.sendMessage(ChatColor.GRAY + "Cannot place this! You are in range of someone else's core.");
					e.setCancelled(true);
					return;
				}
				
				if(schematic.canPlace(e.getBlock().getLocation(), e.getBlock().getType()))
				{
					if(display_name.equalsIgnoreCase("core") || Database.hasCore(p))
					{
						if(display_name.equalsIgnoreCase("core") && Database.hasCore(p))
						{
							p.sendMessage(ChatColor.GRAY + "Cannot place this! You have already placed your core.");
							e.setCancelled(true);
							return;
						}
						else if(!display_name.equalsIgnoreCase("core") && !Core.isRange((int)Math.abs(Database.getCore(p).getOrigin().getLocation().distance(e.getBlock().getLocation())), Item.getTier(p.getItemInHand().getItemMeta().getDisplayName())))
						{
							p.sendMessage(ChatColor.GRAY + "Cannot place this! You are out of range of your core!");
							e.setCancelled(true);
							return;
						}
						e.getBlock().setType(Material.AIR);
						
						Location centerd = Schematic.Center(e.getBlock().getLocation(), schematic.getLength(), schematic.getWidth());
						
						Schematic.pasteSchematic(p.getWorld(), centerd, schematic, p);
						
						
						Tag tag = new Tag(new Cuboid(centerd.getBlock(), (int)schematic.getLength(), (int)schematic.getWidth(), (int)schematic.getHeight()));
						
						List<String> modifiers = new ArrayList<>();
						
						modifiers.add(Modifier.make("player", p.getName()));
						modifiers.add(Modifier.make("display_name", display_name));
						modifiers.add(Modifier.make("tier", Item.getTier(p.getItemInHand().getItemMeta().getDisplayName()) + ""));
						modifiers.add(Modifier.make("health", "" + Damage.getMaxDamage(Item.getTier(p.getItemInHand().getItemMeta().getDisplayName()))));
						
						tag.Upload(modifiers);
						Database.DownloadNew();
						
						Database.add(tag);
						
						
						
						return;
					}
					else
					{
						p.sendMessage(ChatColor.GRAY + "Cannot place this! You haven not placed your core yet?");
					}
					
				}
				else
				{
					p.sendMessage(ChatColor.GRAY + "Cannot place this here! Something else is in the way...");
				}
				
			}
			else
			{
				p.sendMessage(ChatColor.GRAY + "Contact " + ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "_Savant " + ChatColor.RESET + "" + ChatColor.GRAY + "this is a error!");
			}
			e.setCancelled(true);
		}
	}
	
}

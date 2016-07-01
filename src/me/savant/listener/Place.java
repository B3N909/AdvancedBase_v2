package me.savant.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.savant.base.Modifier;
import me.savant.base.Schematic;
import me.savant.base.Tag;
import me.savant.base.UpgradeCost;
import me.savant.cuboid.Cuboid;
import me.savant.util.Chat;
import me.savant.util.Core;
import me.savant.util.Damage;
import me.savant.util.Database;
import me.savant.util.Item;
import me.savant.util.SafePlayer;
import me.savant.util.Tier;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Place implements Listener
{
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if(e.getBlock().getType().equals(Material.RED_SANDSTONE))
		{
			e.setCancelled(true);
			place(e.getPlayer(), e.getBlock(), ChatColor.stripColor(e.getItemInHand().getItemMeta().getDisplayName()));
		}
	}
	
	public static void place(Player p, Block block, String schematicString)
	{
		Schematic schematic = null;
		try
		{
			schematic = Schematic.parseSchematic(schematicString);			
		}
		catch (IOException e1)
		{
			return;
		}
		if(schematic != null)
		{
			String display_name = Item.getDisplayName(schematicString);
			
			Database.DownloadNew();
			if(display_name.equalsIgnoreCase("core") && Core.inAnyRange(block))
			{
    			ActionBarAPI.sendActionBar(p, Chat.red + "Not enough space!", 40);
				return;
			}
			
			if(schematic.canPlace(block.getLocation(), block.getType()))
			{
				if(display_name.equalsIgnoreCase("core") || Database.hasCore(p))
				{
					if(display_name.equalsIgnoreCase("core") && Database.hasCore(p))
					{
		    			ActionBarAPI.sendActionBar(p, Chat.red + "Core already placed", 40);
						return;
					}
					else if(!display_name.equalsIgnoreCase("core") && !Core.isRange((int)Math.abs(Database.getCore(p).getOrigin().getLocation().distance(block.getLocation())), p))
					{
		    			ActionBarAPI.sendActionBar(p, Chat.red + "Out of range", 40);
						return;
					}
					
					Location centerd = Schematic.Center(block.getLocation(), schematic.getLength(), schematic.getWidth());
											
					Tag tag = new Tag(new Cuboid(centerd.getBlock(), (int)schematic.getLength(), (int)schematic.getWidth(), (int)schematic.getHeight()));
					
					if(!SafePlayer.canPlace(tag.getCuboid(), p))
					{
		    			ActionBarAPI.sendActionBar(p, Chat.red + "Stand Back!", 40);
						return;
					}
					
					UpgradeCost uc = Tier.getCost(Item.getTier(schematicString));
					if(p.getInventory().contains(uc.getType()))
					{
						if(p.getInventory().contains(uc.getType(), uc.getAmount()))
						{
			    			ActionBarAPI.sendActionBar(p, Chat.def + "Placed " + Chat.red + display_name + Chat.def + " for " + Chat.red + uc.getAmount() + Chat.def + " " + uc.getUnit(), 40);
	            			int removed = 0;
	            			for(ItemStack item : p.getInventory().getContents())
	            			{
	            				if(item != null && item.getType() != Material.AIR && item.getType() == uc.getType())
	            				{
	            					if(removed < uc.getAmount())
	            					{
	                					if(item.getAmount() + removed <= uc.getAmount())
	                					{
	                						removed = removed + item.getAmount();
	                						p.getInventory().removeItem(item);
	                					}
	                					else
	                					{
	                						int t = item.getAmount() - (uc.getAmount() - removed);
	                						removed = removed + (uc.getAmount() - removed);
	                						item.setAmount(t);
	                					}
	            					}
	            				}
	            			}
	            			p.updateInventory();
						}
						else
						{
							ActionBarAPI.sendActionBar(p, Chat.def + "Placing " + Chat.red + display_name + Chat.def + " Requires " + Chat.red + uc.getAmount() + Chat.def + " " + uc.getUnit(), 40);
							return;
						}
					}
					else
					{
						ActionBarAPI.sendActionBar(p, Chat.def + "Placing " + Chat.red + display_name + Chat.def + " Requires " + Chat.red + uc.getAmount() + Chat.def + " " + uc.getUnit(), 40);
						return;
					}
					
					Schematic.pasteSchematic(p.getWorld(), centerd, schematic, p);
					
					List<String> modifiers = new ArrayList<>();
					
					modifiers.add(Modifier.make("player", p.getName()));
					modifiers.add(Modifier.make("display_name", display_name));
					modifiers.add(Modifier.make("tier", Item.getTier(schematicString) + ""));
					modifiers.add(Modifier.make("health", "" + Damage.getMaxDamage(Item.getTier(Item.getDisplayName(schematicString)))));
					modifiers.add(Modifier.make("schematic", schematic.getName()));
					
					SafePlayer.start(tag.getCuboid(), p);
					
					tag.Upload(modifiers);
					Database.DownloadNew();
					
					Database.add(tag);
					
					return;
				}
				else
				{
					ActionBarAPI.sendActionBar(p, Chat.def + "Cannot place this! You haven not placed your core yet?", 40);
				}
				
			}
			else
			{
    			ActionBarAPI.sendActionBar(p, Chat.red + "Not enough space!", 40);
			}
			
		}
		else
		{
			p.sendMessage(Chat.def + "Contact " + ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "_Savant " + ChatColor.RESET + "" + Chat.def + "this is a error!");
		}
	}
	
}

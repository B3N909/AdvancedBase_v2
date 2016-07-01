package me.savant.listener;

import java.util.List;

import me.savant.base.Main;
import me.savant.base.Menu;
import me.savant.base.Modifier;
import me.savant.base.Tag;
import me.savant.base.UpgradeCost;
import me.savant.util.Chat;
import me.savant.util.Core;
import me.savant.util.IconMenu;
import me.savant.util.Name;
import me.savant.util.Price;
import me.savant.util.Tier;
import me.savant.util.Upgrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Click implements Listener {
	
	public static Main plugin;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(final PlayerInteractEvent e)
	{
		if(e.getItem() != null && e.getItem().getType() == Material.PAPER && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName())
		{
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
			{
				Player p = e.getPlayer();
				Tag tag = Modifier.getTag(p.getTargetBlock(null, 6));
				if(tag == null || tag.getCuboid() == null)
					return;
				List<String> modifiers = tag.DownlaodData();
				if(Modifier.isOwner(modifiers, p) || Modifier.isMember(modifiers, p))
				{
					if(Tier.getTier(modifiers) != 10)
					{
						int tier = Tier.getTier(modifiers) + 1;
						UpgradeCost uc = Tier.getCost(tier);
						if(p.getInventory().contains(uc.getType()))
						{
							if(p.getInventory().contains(uc.getType(), uc.getAmount()))
							{
								ActionBarAPI.sendActionBar(p, ChatColor.GRAY + "Upgraded " + Chat.red + Modifier.getSuffix(modifiers, "display_name") + Chat.def + " to " + Chat.red + "Tier " + tier + Chat.def + " for " + Chat.red + uc.getAmount() + Chat.def + " " + uc.getUnit(), 40);
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
		            			Upgrade.upgradeAt(p, tier);
		            			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 15, 15);
							}
		            		else
		            		{
		            			ActionBarAPI.sendActionBar(p, Chat.def + "Upgrading to " + Chat.red + "T" + tier + Chat.def + " Requires " + Chat.red + uc.getAmount() + Chat.def + " " + uc.getUnit(), 40);
		            		}
						}
	            		else
	            		{
	            			ActionBarAPI.sendActionBar(p, Chat.def + "Upgrading to " + Chat.red + "T" + tier + Chat.def + " Requires " + Chat.red + uc.getAmount() + Chat.def + " " + uc.getUnit(), 40);
	            		}
					}
				}
			}
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				final Player p = e.getPlayer();
				if(p.isSneaking())
				{
					IconMenu menu = new IconMenu(Chat.red + "Building Plan", 18, null, new IconMenu.OptionClickEventHandler()
			        {
			            @Override
			            public void onOptionClick(IconMenu.OptionClickEvent ev)
			            {
			            	if(work())
			            	{
				            	String name = ChatColor.stripColor(ev.getName());
				            	ItemStack item = e.getItem();
				            	ItemMeta meta = item.getItemMeta();
				            	meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Building Plan" + ChatColor.RESET + "" + ChatColor.GRAY + " - " + name);
				            	item.setItemMeta(meta);
				            	p.setItemInHand(item);
				            	p.sendMessage(Chat.def + "Selected " + Chat.red + name);
			            	}
			            }
			        }, plugin)
					.setOption(0, new ItemStack(Material.BEACON, 1), ChatColor.GOLD + "core", new String[] { ChatColor.GRAY + "1.) Used to Manage your base.", ChatColor.GRAY + "2.) You have to place this first", ChatColor.GRAY + "3.) You want to defend this within your base", ChatColor.GRAY + "4.) Don't let people get access to this"})
					.setOption(1, new ItemStack(Material.ANVIL, 1), ChatColor.GOLD + "foundation", new String[] { ChatColor.GRAY + "A floor"})
					.setOption(2, new ItemStack(Material.COBBLE_WALL, 1), ChatColor.GOLD + "wall", new String[] {})
					.setOption(3, new ItemStack(Material.DISPENSER, 1), ChatColor.GOLD + "scanner",  new String[] { ChatColor.GRAY + "A doorway that stops unwelcome visitors!"})
					.setOption(4,  new ItemStack(Material.CHEST, 1), ChatColor.GOLD + "chest", new String[] {})
					.setOption(5, new ItemStack(Material.LADDER, 1), ChatColor.GOLD + "ladder", new String[] {})
					.setOption(6, new ItemStack(Material.FURNACE, 1), ChatColor.GOLD + "furnace", new String[] {});
					menu.open(p);
				}
				else
				{
					String name = ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName());
					if(name.contains("-"))
					{
						String partName = name.split("- ")[1] + "_t1";
						Block loc = p.getTargetBlock(null, 10).getRelative(BlockFace.UP);
						if(loc.getType() == Material.AIR)
						{
							Place.place(p, loc, partName);
						}
						else
						{
	            			ActionBarAPI.sendActionBar(p, Chat.red + "Not enough space!", 40);
						}
					}
					else
					{
            			ActionBarAPI.sendActionBar(p, Chat.def + "Select building part first", 40);
					}
				}
			}
			return;
		}
		if(e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			Player p = e.getPlayer();
			
			if(p.getTargetBlock(null, 2) != null)
			{
				Tag tag = Modifier.getTag(p.getTargetBlock(null, 2));
				if(tag == null || tag.getCuboid() == null)
					return;
				List<String> modifiers = tag.DownlaodData();
				if(p.isSneaking())
				{
					if(((Player)Modifier.getOwner(tag.DownlaodData())).getName().equalsIgnoreCase(p.getName()))
					{
						if(Core.isCore(modifiers))
						{
							Menu.openDestroyConfirmation(p, tag, modifiers);
						}
						else
						{
							tag.Break(p);
						}
					}
				}
				else if(p.getItemInHand() != null && p.getItemInHand().getType().equals(Material.EMERALD_BLOCK))
				{
					if(Modifier.isOwner(modifiers, p) || Modifier.isMember(modifiers, p))
					{
						if(Tier.getTier(modifiers) != 10)
						{
							int tier = Tier.getTier(modifiers) + 1;
							int required = Price.getRawPrice(Name.getRawName(modifiers), tier);
							if(p.getInventory().contains(Material.EMERALD_BLOCK, required))
							{
								Upgrade.upgradeAt(p, tier);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 15);
								p.getInventory().removeItem(Upgrade.getEmeraldStack(required));
								return;
							}
						}
						p.playSound(p.getLocation(), Sound.CLICK, 1, 15);
					}
				}
			}
		}
		else if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Player p = e.getPlayer();
			
			Tag tag = Modifier.getTag(p);
			if(p.getTargetBlock(null, 2) != null && tag != null)
			{
				List<String> modifiers = tag.DownlaodData();
				if(p.isSneaking())
				{
					if(Modifier.isOwner(modifiers, p) || Modifier.isMember(modifiers, p))
					{
						Menu.openInventory(p, modifiers, tag);
					}
				}
			}
		}
	}
	
	static boolean canWork = true;
	public static boolean work()
	{
		if(canWork)
		{
			canWork = false;
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
			{
				@Override
				public void run() {
					canWork = true;
				}
				
			}, 5L);
			return true;
		}
		else
		{
			return false;
		}
	}
	
}

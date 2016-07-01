package me.savant.base;

import java.util.ArrayList;
import java.util.List;

import me.savant.util.Chat;
import me.savant.util.Core;
import me.savant.util.IconMenu;
import me.savant.util.IconMenu.OptionClickEvent;
import me.savant.util.Tier;
import me.savant.util.Upgrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Menu
{
	public static Main plugin;
	
	private static Tag tmp_tag;
	
	public static void openDestroyConfirmation(final Player p, final Tag tag, List<String> modifiers)
	{
		tmp_tag = tag;
		IconMenu menu = new IconMenu(Chat.red + "Confirm Destory...", 9, tag, new IconMenu.OptionClickEventHandler() 
		{
			public void onOptionClick(OptionClickEvent event) 
			{
				if(!work())
					return;
				if(event.getName().equalsIgnoreCase(Chat.def + "Accept"))
				{
					tmp_tag.Break(p);
				}
				else
				{
					event.setWillClose(true);
					event.setWillDestroy(true);
				}
			}
		}, plugin)
		.setOption(3, getAccept())
		.setOption(5, getDeny());
		menu.open(p);
	}
	
	public static ItemStack getAccept()
	{
		ItemStack item = new ItemStack(Material.getMaterial(159), 1, (short) 5);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Chat.def + "Accept");
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getDeny()
	{
		ItemStack item = new ItemStack(Material.getMaterial(159), 1, (short) 14);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Chat.red + "Reject");
		item.setItemMeta(meta);
		return item;
	}
	
	public static void openInventory(Player p, final List<String> modifiers, final Tag tag)
	{
        IconMenu menu = new IconMenu(Chat.red + Modifier.getFormatedName(modifiers), 27, tag, new IconMenu.OptionClickEventHandler()
        {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e) {
            	if(work())
            	{
                	final Player p = e.getPlayer();
                    if(ChatColor.stripColor(e.getName()).contains("Upgrade Required"))
                    {
                    	int tier = Integer.parseInt(ChatColor.stripColor(e.getName()).split("T")[1]);
                    	UpgradeCost uc = Tier.getCost(tier);
                    	if(p.getInventory().contains(uc.getType()))
                    	{
                    		if(p.getInventory().contains(uc.getType(), uc.getAmount()))
                    		{
                    			p.closeInventory();
                    			p.sendMessage(ChatColor.GRAY + "Upgraded " + Chat.red + Modifier.getSuffix(modifiers, "display_name") + Chat.def + " to " + Chat.red + "Tier " + tier + Chat.def + " for " + Chat.red + uc.getAmount() + Chat.def + " " + uc.getUnit());
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
                    			p.sendMessage(Chat.def + "Not enough " + Chat.red + "Resources");
                    		}
                    	}
                    	else
                    	{
                			p.sendMessage(Chat.def + "Not enough " + Chat.red + "Resources");
                    	}
                    }
                    else if(e.getName().equalsIgnoreCase(Chat.def + "Open Clan Menu"))
                    {
                    	p.playSound(p.getLocation(), Sound.LEVEL_UP, 15, 15);
                    	e.setWillClose(true);
                    	e.setWillDestroy(true);
                    	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                    	{
                    		public void run()
                    		{
                    			if(Modifier.isOwner(modifiers, p))
                    			{
                                	openClan(p, modifiers, tag);
                    			}
                    			else
                    			{
                    				p.sendMessage(Chat.def + "Only the " + Chat.red + "Owner" + Chat.def + " can open this menu");
                    			}
                    		}
                    	}, 2L);
                    	return;
                    }
                    else
                    {
                    	e.setWillClose(true);
                    	e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.CLICK1, 15);
                    }
            	}
            }
        }, plugin)
        .setOption(0, Tier.getTierIcon(1), ChatColor.GRAY + "T1", "")
        .setOption(1, Tier.getTierIcon(2), ChatColor.GRAY + "T2", "")
        .setOption(2, Tier.getTierIcon(3), ChatColor.GRAY + "T3", "")
        .setOption(3, Tier.getTierIcon(4), ChatColor.GRAY + "T4", "")
        .setOption(4, Tier.getTierIcon(5), ChatColor.GRAY + "T5", "")
        .setOption(5, Tier.getTierIcon(6), ChatColor.GRAY + "T6", "")
        .setOption(6, Tier.getTierIcon(7), ChatColor.GRAY + "T7", "")
        .setOption(7, Tier.getTierIcon(8), ChatColor.GRAY + "T8", "")
        .setOption(8, Tier.getTierIcon(9), ChatColor.GRAY + "T9", "")
        .setOption(9, getTierMenuItem(modifiers, 1))
        .setOption(10, getTierMenuItem(modifiers, 2))
        .setOption(11, getTierMenuItem(modifiers, 3))
        .setOption(12, getTierMenuItem(modifiers, 4))
        .setOption(13, getTierMenuItem(modifiers, 5))
        .setOption(14, getTierMenuItem(modifiers, 6))
        .setOption(15, getTierMenuItem(modifiers, 7))
        .setOption(16, getTierMenuItem(modifiers, 8))
        .setOption(17, getTierMenuItem(modifiers, 9))
        .setOption(18, getCoreUpgradeNext(modifiers));
        menu.open(p);
	}
	
	public static void openClan(Player p, final List<String> modifiers, final Tag tag)
	{
        IconMenu menu1 = new IconMenu(Chat.red + Modifier.getFormatedName(modifiers), 45, tag, new IconMenu.OptionClickEventHandler() {
            @SuppressWarnings("deprecation")
			@Override
            public void onOptionClick(IconMenu.OptionClickEvent e) {
            	if(work())
            	{
                	final Player p = e.getPlayer();
                	String name = ChatColor.stripColor(e.getName());
                	String lore = ChatColor.stripColor(e.getClicked().getItemMeta().getLore().get(0));
                	if(lore.equalsIgnoreCase("[Click to add to Base]"))
                	{
                		Player newMember = Bukkit.getPlayer(name);
                		newMember.sendMessage(Chat.red + p.getName() + Chat.def + " has added you to his " + Chat.red + "base");
                		Modifier.addMember(tag, modifiers, newMember);
                    	p.playSound(p.getLocation(), Sound.ORB_PICKUP, 15, 15);
                	}
                	else if(lore.equalsIgnoreCase("[Member]"))
                	{
                		Player oldMember = Bukkit.getPlayer(name);
                		oldMember.sendMessage(Chat.red + p.getName() + Chat.def + " removed you from his " + Chat.red + "base");
                		Modifier.removeMember(tag, modifiers, oldMember);
                    	p.playSound(p.getLocation(), Sound.ORB_PICKUP, 15, 15);
                	}
                	else if(lore.equalsIgnoreCase("[Owner]"))
                	{
                		p.sendMessage(Chat.def + "Cannot remove the Owner");
                    	p.playSound(p.getLocation(), Sound.CLICK, 15, 15);
                	}
                	else
                	{
                		p.sendMessage(Chat.def + "Contact " + Chat.red + "_Savant " + Chat.def + "this is a error!");
                    	p.playSound(p.getLocation(), Sound.CLICK, 15, 15);
                	}
            	}
            }
        }, plugin)
        .setOption(36, getBack());
        
        
        int slots = 0;
        for(ItemStack item : getHeads(modifiers))
        {
        	menu1.setOption(slots, item);
        	slots++;
        }
        
        menu1.open(p);
	}
	
	public static List<ItemStack> getHeads(List<String> modifiers)
	{
		List<ItemStack> list = new ArrayList<>();
		List<OfflinePlayer> processed = new ArrayList<>();
		List<OfflinePlayer> members = Modifier.getMembers(modifiers);
		for(OfflinePlayer p : members)
		{
			processed.add(p);
			list.add(getHead(modifiers, p));
		}
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(!isInside(processed, p))
			{
				list.add(getHead(modifiers, p));
			}
		}
		return list;
	}
	
	public static boolean isInside(List<OfflinePlayer> check, Player p)
	{
		for(OfflinePlayer p1 : check)
		{
			if(p1.getName().equalsIgnoreCase(p.getName()))
				return true;
		}
		return false;
	}
	
	public static ItemStack getHead(List<String> modifiers, OfflinePlayer p)
	{
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
		ItemMeta meta = item.getItemMeta();
		
		if(Modifier.isMember(modifiers, (Player)p))
			meta.setDisplayName(Chat.red + p.getName());
		else
			meta.setDisplayName(Chat.def + p.getName());
		
		List<String> lore = new ArrayList<>();
		if(Modifier.getOwner(modifiers).getName().equalsIgnoreCase(p.getName()))
			lore.add(Chat.red + "[Owner]");
		else if (Modifier.isMember(modifiers, (Player) p))
			lore.add(Chat.def + "[Member]");
		else
			lore.add(Chat.def + "[Click to add to Base]");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setOwner(p.getName());
		item.setItemMeta(skull);
		
		return item;
	}
	
	public static ItemStack getBack()
	{
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) 14);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Back!");
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getCoreUpgradeNext(List<String> modifiers)
	{
		ItemStack item;
		if(Core.isCore(modifiers))
		{
			item = new ItemStack(Material.WOOL, 1, (short) 5);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Chat.def + "Open Clan Menu");
			List<String> lore = new ArrayList<>();
			lore.add("Click to open Clan menu;");
			lore.add("Clan menu allows you to share");
			lore.add("your base with other players!");
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		else
		{
			item = new ItemStack(Material.WOOL, 1, (short) 14);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Chat.red + "Clan Menu Disabled");
			List<String> lore = new ArrayList<>();
			lore.add("<Clan Menu>");
			lore.add("Unlocked via accessing");
			lore.add("through the bases core!");
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return item;
	}
	
	public static ItemStack getTierMenuItem(List<String> modifiers, int tier)
	{
		if(Tier.getTierIcon(tier).getType() == Material.GLASS)
		{
			ItemStack item = new ItemStack(Material.GLASS, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GRAY + "Tier Unavailable");
			item.setItemMeta(meta);
			return item;
		}
		if(Tier.getTier(modifiers) >= tier)
		{
			return getTierGreen(tier);
		}
		else
		{
			return getTierRed(modifiers, tier, Modifier.getSuffix(modifiers, "name"));
		}
	}
	
	public static ItemStack getTierGreen(int tier)
	{
		ItemStack item = new ItemStack(Material.getMaterial(160), 1, (short)5);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "UNLOCKED");
		List<String> l = new ArrayList<>();
		l.add(ChatColor.GRAY + "You have already unlocked");
		l.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Tier " + tier);
		meta.setLore(l);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getTierRed(List<String> modifiers, int tier, String rawName)
	{
		ItemStack item = new ItemStack(Material.getMaterial(160), 1, (short)14);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Upgrade Required T" + tier);
		List<String> l = new ArrayList<>();
		UpgradeCost uc = Tier.getCost(tier);
		l.add(ChatColor.GRAY + "Cost " + uc.getUnit() + " " + uc.getAmount());
		l.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Click to Upgrade");
		meta.setLore(l);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getMoneyAmountItem(Player p)
	{
		ItemStack item = null;
		if(p.getInventory().contains(Material.EMERALD_BLOCK))
		{
			item = new ItemStack(Material.DIAMOND_ORE, 1);
			ItemMeta meta = item.getItemMeta();
			int amount = 0;
			for(ItemStack stack : p.getInventory().getContents())
			{
				if(stack != null && stack.getType() == Material.EMERALD_BLOCK)
				{
					amount = amount + stack.getAmount();
				}
			}
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "EMERALDS: " + amount);
			List<String> l = new ArrayList<>();
			l.add(ChatColor.GRAY + "Used as upgrade points!");
			meta.setLore(l);
			item.setItemMeta(meta);
			item.setAmount(amount);
		}
		else
		{
			item = new ItemStack(Material.WOOL, 1, (short) 14);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE NO MONEY");
			List<String> l = new ArrayList<>();
			l.add(ChatColor.GRAY + "Add Emerald Blocks...");
			l.add(ChatColor.GRAY + "For use as Upgrade points!");
			meta.setLore(l);
			item.setItemMeta(meta);
		}
		return item;
	}
	
	public static int getMoneyAmount(Player p)
	{
		int amount = 0;
		for(ItemStack item : p.getInventory().getContents())
		{
			if(item != null && item.getType() == Material.EMERALD_BLOCK)
			{
				amount = amount + item.getAmount();
			}
		}
		return amount;
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

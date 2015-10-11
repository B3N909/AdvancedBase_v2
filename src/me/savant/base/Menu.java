package me.savant.base;

import java.util.ArrayList;
import java.util.List;

import me.savant.util.Core;
import me.savant.util.IconMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Menu {
	
	public static Main plugin;
	
	public static void openInventory(Player p, final Block block)
	{
		final List<String> modifiers = Modifier.getTag(block).DownlaodData();
        IconMenu menu = new IconMenu(Modifier.getFormatedName(modifiers), 27, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e) {
            	if(work())
            	{
                	final Player p = e.getPlayer();
                    if(ChatColor.stripColor(e.getName()).contains("UNLOCK COST"))
                    {
                    	int price = Integer.parseInt(e.getName().split(": ")[1]);
                    	int tier = Integer.parseInt(ChatColor.stripColor(e.getName()).split(" ")[0].replace("T", ""));
                    	if(p.getInventory().contains(Material.EMERALD_BLOCK))
            			{
                    		if(p.getInventory().contains(Material.EMERALD_BLOCK, price))
                    		{
                    			p.closeInventory();
                    			p.sendMessage(ChatColor.GRAY + "Upgraded " + Modifier.getSuffix(modifiers, "display_name") + ChatColor.RESET + "" + ChatColor.GRAY + " to " + ChatColor.BOLD + "Tier " + tier + ChatColor.RESET + "" + ChatColor.GRAY + " for " + price + " Emeralds!");
                    			p.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK, price));
                    			p.updateInventory();
                    			TagUtil.upgradeAt(p, tier);
                            	p.playSound(p.getLocation(), Sound.ORB_PICKUP, 15, 15);
                    		}
                    		else
                    		{
                    			p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You do not have enough to upgrade this!");
                    		}
            			}
                    	else
                    	{
                			p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You do not have enough to upgrade this!");
                    	}
                    }
                    else if(e.getName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Clan Menu"))
                    {
                    	p.playSound(p.getLocation(), Sound.LEVEL_UP, 15, 15);
                    	e.setWillClose(true);
                    	e.setWillDestroy(true);
                    	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                    	{
                    		public void run()
                    		{
                            	openClan(p, block);
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
        .setOption(0, new ItemStack(Material.WOOD, 1), ChatColor.GRAY + "T1", "")
        .setOption(1, new ItemStack(Material.STONE, 1), ChatColor.GRAY + "T2", "")
        .setOption(2, new ItemStack(Material.getMaterial(98), 1), ChatColor.GRAY + "T3", "")
        .setOption(3, new ItemStack(Material.OBSIDIAN, 1), ChatColor.GRAY + "T4", "")
        .setOption(4, new ItemStack(Material.DIAMOND_BLOCK, 1), ChatColor.GRAY + "T5", "")
        .setOption(5, new ItemStack(Material.BEDROCK, 1), ChatColor.GRAY + "T6", "")
        .setOption(6, new ItemStack(Material.SLIME_BLOCK, 1), ChatColor.GRAY + "T7", "")
        .setOption(7, new ItemStack(Material.EMERALD_BLOCK, 1), ChatColor.GRAY + "T8", "")
        .setOption(8, new ItemStack(Material.ENDER_PORTAL_FRAME, 1), ChatColor.GRAY + "T9", "")
        .setOption(9, getTierMenuItem(block, 1))
        .setOption(10, getTierMenuItem(block, 2))
        .setOption(11, getTierMenuItem(block, 3))
        .setOption(12, getTierMenuItem(block, 4))
        .setOption(13, getTierMenuItem(block, 5))
        .setOption(14, getTierMenuItem(block, 6))
        .setOption(15, getTierMenuItem(block, 7))
        .setOption(16, getTierMenuItem(block, 8))
        .setOption(17, getTierMenuItem(block, 9))
        .setOption(26, getMoneyAmountItem(p))
        .setOption(18, getCoreUpgradeNext(block));
        menu.open(p);
	}
	
	public static void openClan(Player p, final Block block)
	{
		Tag tag = Modifier.getTag(block);
		List<String> modifiers = tag.DownlaodData();
        IconMenu menu1 = new IconMenu(Modifier.getFormatedName(modifiers), 45, new IconMenu.OptionClickEventHandler() {
            @SuppressWarnings("deprecation")
			@Override
            public void onOptionClick(IconMenu.OptionClickEvent e) {
            	if(work())
            	{
                	final Player p = e.getPlayer();
                	String name = ChatColor.stripColor(e.getName());
                	System.out.println(name);
                	if(name.equalsIgnoreCase("Back!"))
                	{
                		p.closeInventory();
                    	Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable()
                    	{
                    		public void run()
                    		{
                        		openInventory(p, p.getTargetBlock(null, 2));
                    		}
                    	}, 5L);
                    	p.playSound(p.getLocation(), Sound.LEVEL_UP, 15, 15);
                	}
                	else if(!name.contains("[O] "))
                	{
                		Player clickedPlayer = Bukkit.getPlayer(name);
                		if(TagUtil.isMember(clickedPlayer, block))
                		{
                    		TagUtil.removeMember(p, clickedPlayer);
                    		p.closeInventory();
                    		p.sendMessage(ChatColor.GRAY + "Removed member " + ChatColor.BOLD + clickedPlayer.getName());
                		}
                		else
                		{
                    		TagUtil.addMember(p, clickedPlayer);
                    		p.closeInventory();
                    		p.sendMessage(ChatColor.GRAY + "Added member " + ChatColor.BOLD + clickedPlayer.getName());
                		}
                	}
            	}
            }
        }, plugin)
        .setOption(36, getBack());
        
        
        int slots = 0;
        for(Player player : Bukkit.getOnlinePlayers())
        {
        	menu1.setOption(slots, getClanHead(player, modifiers));
        	slots++;
        }
        
        menu1.open(p);
	}
	
	public static ItemStack getClanHead(Player p, List<String> modifiers)
	{
		if(((Player)Modifier.getOwner(modifiers)).getName().equalsIgnoreCase(p.getName()))
		{
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "[O] " + p.getName());
			List<String> l = new ArrayList<>();
			l.add(ChatColor.GRAY + "This is the Clan owner!");
			meta.setLore(l);
			item.setItemMeta(meta);	
			
			SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
			skullMeta.setOwner(p.getName());
			item.setItemMeta(skullMeta);
			return item;
		}
		if(TagUtil.hasMembers(block))
		{
			if(TagUtil.isMember(p, block))
			{
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
				
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + p.getName());
				List<String> l = new ArrayList<>();
				l.add(ChatColor.GRAY + "Already in Clan!");
				l.add(ChatColor.GRAY + "Click to remove from Clan!");
				meta.setLore(l);
				item.setItemMeta(meta);	
				
				SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
				skullMeta.setOwner(p.getName());
				item.setItemMeta(skullMeta);
				return item;
			}
			else
			{
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
				
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + p.getName());
				List<String> l = new ArrayList<>();
				l.add(ChatColor.GRAY + "Click to add to Clan!");
				l.add(ChatColor.GRAY + "This player will be able to build!");
				meta.setLore(l);
				item.setItemMeta(meta);	
				
				SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
				skullMeta.setOwner(p.getName());
				item.setItemMeta(skullMeta);
				return item;
			}
		}
		else
		{
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + p.getName());
			List<String> l = new ArrayList<>();
			l.add(ChatColor.GRAY + "Click to add to Clan!");
			l.add(ChatColor.GRAY + "This player will be able to build!");
			meta.setLore(l);
			item.setItemMeta(meta);	
			
			SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
			skullMeta.setOwner(p.getName());
			item.setItemMeta(skullMeta);
			return item;
		}
	}
	
	public static ItemStack getBack()
	{
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) 14);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Back!");
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getCoreUpgradeNext(Block block)
	{
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) 8);
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "'Clan Menu' disabled");
		
		List<String> l = new ArrayList<>();
		l.add(ChatColor.GRAY + "Open Menu on Core");
		l.add(ChatColor.GRAY + "To Activate this option");
		meta.setLore(l);
		
		item.setItemMeta(meta);
		
		
		if(Core.isCore(block))
		{
			item = new ItemStack(Material.WOOL, 1, (short) 5);
			
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Clan Menu");
			
			List<String> l1 = new ArrayList<>();
			l1.add(ChatColor.GRAY + "Opens Clan Menu");
			l1.add(ChatColor.GRAY + "Ability to add Part Owners");
			meta.setLore(l1);
			
			item.setItemMeta(meta);
			
		}
		return item;
	}
	
	public static ItemStack getTierMenuItem(List<String> modifiers, int tier)
	{
		if(Integer.parseInt(Modifier.getSuffix(modifiers, "tier")) >= tier)
		{
			return getTierGreen(tier);
		}
		else
		{
			return getTierRed(block, tier, TagUtil.getRawName(block));
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
	
	public static ItemStack getTierRed(Block block, int tier, String rawName)
	{
		int currentTier = TagUtil.getTier(block);
		ItemStack item = new ItemStack(Material.getMaterial(160), 1, (short)14);
		ItemMeta meta = item.getItemMeta();
		int price = PriceUtil.getRawPrice(rawName, tier) - PriceUtil.getRawPrice(rawName, currentTier);
		meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "T" + tier + " " + "UNLOCK COST: " + price);
		List<String> l = new ArrayList<>();
		l.add(ChatColor.GRAY + "Cost: " + price);
		l.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Emeralds Unlock Tokens");
		l.add(ChatColor.GRAY + "Click to Unlock!");
		l.add(ChatColor.GRAY + "" + ChatColor.BOLD + "Tier " + tier);
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
				
			}, 20L);
			return true;
		}
		else
		{
			return false;
		}
	}
}

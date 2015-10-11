package me.savant.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	
	public static void giveItem(Player p, String name)
	{
		ItemStack item = new ItemStack(Material.RED_SANDSTONE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + name);
		List<String> l = new ArrayList<>();
		l.add("AdvancedBase");
		l.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "Plugin by...");
		l.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "_Savant");
		meta.setLore(l);
		item.setItemMeta(meta);
		p.getInventory().addItem(new ItemStack[] { item } );
		p.updateInventory();
	}
	
	public static void giveItem(Player p, String name, int amount)
	{
		ItemStack item = new ItemStack(Material.RED_SANDSTONE, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + name);
		List<String> l = new ArrayList<>();
		l.add("AdvancedBase");
		l.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "Plugin by...");
		l.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "_Savant");
		meta.setLore(l);
		item.setItemMeta(meta);
		p.getInventory().addItem(new ItemStack[] { item } );
		p.updateInventory();
	}
	
	public static String getDisplayName(String rawName)
	{
		rawName = ChatColor.stripColor(rawName);
		if(rawName.contains("_"))
			return rawName.split("_")[0];
		return rawName;
	}
	
	public static int getTier(String rawName)
	{
		rawName = ChatColor.stripColor(rawName);
		if(rawName.contains("_t"))
			return Integer.parseInt(rawName.split("_t")[1]);
		return 0;
	}
}

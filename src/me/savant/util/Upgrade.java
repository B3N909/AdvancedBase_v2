package me.savant.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import me.savant.base.Modifier;
import me.savant.base.Schematic;
import me.savant.base.Tag;
import me.savant.cuboid.Cuboid;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Upgrade 
{
	/**
	 * Old Function
	 * 
	 * 	public static void upgradeAt(Player p, int tier)
	{
		Block block = p.getTargetBlock(null, 20);
		Tag tag = Modifier.getTag(block);
		List<String> modifiers = tag.DownlaodData();
		int width = tag.getCuboid().getWidth();
		int length = tag.getCuboid().getLength();
		int height = tag.getCuboid().getHeight();
		Location origin = tag.getCuboid().getOrigin().getLocation();
		
		String name = Modifier.getSuffix(modifiers, "display_name") + "_t" + Modifier.getSuffix(modifiers, "tier");
		
		Tag.tagRegion(origin, width, length, height, plugin, "Tier " + tier);
		Tag.tagRegion(origin, width, length, height, plugin, "Name " + name);
		Index index = new Index(Schematic.Center(origin, length, width), new Part(SchematicRefrence.Parse(name)), p);
	}
	 * 
	 */
	public static void upgradeAt(Player p, int tier)
	{
		Block target = p.getTargetBlock((HashSet<Byte>)null, 20);
		Tag tag = Modifier.getTag(target);
		List<String> modifiers = tag.DownlaodData();
		Cuboid region = tag.getCuboid();
		try 
		{
			Schematic schematic = Schematic.parseSchematic(Name.buildFromTier(modifiers, tier));
			Schematic.pasteSchematic(region.getWorld(), region.getOrigin().getLocation(), schematic, p);
			Database.updateUpload(tag, Modifier.modify(modifiers, "tier", tier + ""));
		} 
		catch (IOException e) 
		{
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error");
			e.printStackTrace();
		}
	}
	
	public static ItemStack getEmeraldStack(int amount)
	{
		ItemStack upgrade = new ItemStack(Material.EMERALD_BLOCK, amount);
		return upgrade;
	}
	
}

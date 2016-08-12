package me.savant.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.savant.base.Main;
import me.savant.base.Modifier;
import me.savant.base.UpgradeCost;

public class Tier
{
	public static Main file;
	
	public static ItemStack getTierIcon(int tier)
	{
		if(file.getConfig().getString("tier." + tier) == "" || file.getConfig().getString("tier." + tier) == null)
			file.getConfig().set("tier." + tier, String.valueOf("STONE"));
		Material material = Material.getMaterial(file.getConfig().getString("tier." + tier));
		ItemStack item = new ItemStack(material, 1);
		file.saveConfig();
		return item;
	}
	
	public static int getMaxTier()
	{
		if(file.getConfig().getConfigurationSection("tier.size") == null || file.getConfig().getInt("tier.size") == 0)
			file.getConfig().set("tier.size", Integer.valueOf(9));
		int size = file.getConfig().getInt("tier.size");
		file.saveConfig();
		return size;
	}
	
	public static int getTier(List<String> modifiers)
	{
		return Integer.parseInt(Modifier.getSuffix(modifiers, "tier"));
	}
	
	public static UpgradeCost getCost(int tier)
	{
		if(tier == 1)
			return new UpgradeCost("Wood", 32, Material.LOG);
		else if(tier == 2)
			return new UpgradeCost("Wood", 128, Material.LOG);
		else if(tier == 3)
			return new UpgradeCost("Stone", 156, Material.COBBLESTONE);
		else if(tier == 4)
			return new UpgradeCost("Metal", 56, Material.IRON_INGOT);
		else if(tier == 5)
			return new UpgradeCost("High Quality Metal", 15, Material.DIAMOND);
		else
			return new UpgradeCost("Not Defined", 1, Material.ANVIL);
	}
}

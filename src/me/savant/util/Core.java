package me.savant.util;

import java.util.List;

import me.savant.base.Modifier;
import me.savant.base.Tag;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Core 
{	
	public static int getRange(int tier)
	{
		return (tier * 5) + 8;
	}
	
	public static int getRange(Player p)
	{
		List<String> modifiers = Database.getCoreModifiers(p);
		return getRange(Tier.getTier(modifiers));
	}
	
	public static boolean isRange(int current, int tier)
	{
		return current <= getRange(tier);
	}
	
	public static boolean isRange(int current, Player p)
	{
		return current <= getRange(p);
	}
	
	public static boolean inAnyRange(Block block)
	{
		for(Tag tag : Database.getTags())
		{
			if(isCore(tag.getModifiers()) && Math.abs(tag.getCuboid().getOrigin().getLocation().distance(block.getLocation())) <= 60)
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isCore(List<String> modifiers)
	{
		return Modifier.contains(modifiers, "display_name", "core");
	}
}

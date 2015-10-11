package me.savant.util;

import me.savant.base.Modifier;
import me.savant.base.Tag;
import me.savant.cuboid.Cuboid;

import org.bukkit.block.Block;

public class Core 
{	
	public static int getRange(int tier)
	{
		return (tier * 5) + 8;
	}	
	public static boolean isRange(int current, int tier)
	{
		return current <= getRange(tier);
	}
	public static boolean inAnyRange(Block block)
	{
		for(Tag tag : Database.getTags())
		{
			if(Math.abs(tag.getCuboid().getOrigin().getLocation().distance(block.getLocation())) <= 60)
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isCore(Block block)
	{
		for(Tag tag : Database.getTags())
			if(Modifier.getSuffix(tag.DownlaodData(), "display_name").equalsIgnoreCase("core"))
				return true;
		return false;
	}
}

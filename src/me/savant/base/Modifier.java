package me.savant.base;

import java.util.ArrayList;
import java.util.List;

import me.savant.util.Database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Modifier 
{	
	public static String getPrefix(String modifier)
	{
		if(modifier.contains(" "))
			return modifier.split(" ")[0];
		return modifier;
	}
	public static String getSuffix(String modifier)
	{
		if(modifier.contains(" "))
			return modifier.split(" ")[1];
		return modifier;
	}
	public static boolean has(String modifier, String prefix, String suffix)
	{
		if(modifier.contains(" "))
			if(modifier.split(" ")[0].equalsIgnoreCase(prefix))
				if(modifier.split(" ")[1].equalsIgnoreCase(suffix))
					return true;
		return false;
	}
	public static boolean contains(List<String> modifiers, String prefix, String suffix)
	{
		for(String modifier : modifiers)
			if(has(modifier, prefix, suffix))
				return true;
		return false;
	}
	public static String make(String prefix, String suffix)
	{
		return prefix + " " + suffix;
	}
	public static String getSuffix(List<String> modifiers, String prefix)
	{
		for(String modifier : modifiers)
		{
			if(getPrefix(modifier).equalsIgnoreCase(prefix))
				return getSuffix(modifier);
		}
		return prefix;
	}
	public static Tag getTag(Player p)
	{
		@SuppressWarnings("deprecation")
		Block block = p.getTargetBlock(null, 20);
		if(block != null)
		{
			for(Tag tag : Database.getTags())
				if(tag.getCuboid().isInside(block))
					return tag;
		}
		return null;
	}
	public static Tag getTag(Block block)
	{
		for(Tag tag : Database.getTags())
			if(tag.getCuboid().isInside(block))
				return tag;
		return null;
	}
	public static List<String> modify(List<String> modifiers, String prefix, String newSuffix)
	{
		List<String> l = new ArrayList<>();
		for(String modifier : modifiers)
		{
			if(getPrefix(modifier).equalsIgnoreCase(prefix))
			{
				l.add(make(prefix, newSuffix));
			}
			else
			{
				l.add(modifier);
			}
		}
		return l;
	}
	public static String getFormatedName(List<String> modifiers)
	{
		return getSuffix(modifiers, "display_name") + " " + getSuffix(modifiers, "tier");
	}
	public static OfflinePlayer getOwner(List<String> modifiers)
	{
		return Bukkit.getOfflinePlayer(getSuffix(modifiers, "player"));
	}
}

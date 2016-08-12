package me.savant.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import me.savant.util.Database;

import org.bukkit.Bukkit;
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
		Block block = p.getTargetBlock((HashSet<Byte>)null, 20);
		if(block != null)
			for(Tag tag : Database.getTags())
				if(tag.getCuboid() != null && tag.getCuboid().isInside(block))
					return tag;
		return null;
	}
	public static Tag getTag(Block block)
	{
		Tag tag1 = null;
		for(Tag tag : Database.getTags())
			if(tag.getCuboid() != null && tag.getCuboid().isInside(block))
				tag1 = tag;
		return tag1;
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
	public static List<String> remove(List<String> modifiers, String prefix, String suffix)
	{
		List<String> build = new ArrayList<>();
		for(String s : modifiers)
		{
			if(!Modifier.has(s, prefix, suffix))
				build.add(s);
			else
				System.out.println("REMOVED: " + s);
		}
		return build;
	}
	public static String getFormatedName(List<String> modifiers)
	{
		return getSuffix(modifiers, "display_name") + " " + getSuffix(modifiers, "tier");
	}
	public static OfflinePlayer getOwner(List<String> modifiers)
	{
		return Bukkit.getOfflinePlayer(getSuffix(modifiers, "player"));
	}
	public static List<OfflinePlayer> getMembers(List<String> modifiers)
	{
		List<OfflinePlayer> l = new ArrayList<>();
		for(String s : modifiers)
			if(Modifier.getPrefix(s).equalsIgnoreCase("Member"))
				l.add(Bukkit.getOfflinePlayer(Modifier.getSuffix(s)));
		return l;
	}
	public static boolean isOwner(List<String> modifiers, Player p)
	{
		return Modifier.getOwner(modifiers).getName().equalsIgnoreCase(p.getName());
	}
	public static boolean isMember(List<String> modifiers, Player p)
	{
		if(!Modifier.isOwner(modifiers, p))
			return Modifier.contains(modifiers, "Member", p.getName().toLowerCase());
		return true;
	}
	public static void addMember(Tag tag, List<String> modifiers, Player member)
	{
		modifiers.add(Modifier.make("Member", member.getName().toLowerCase()));
		tag.Upload(modifiers);
		Database.DownloadNew();
	}
	public static void removeMember(Tag tag, List<String> modifiers, Player member)
	{
		if(isMember(modifiers, member))
		{
			List<String> newModifiers = Modifier.remove(modifiers, "Member", member.getName().toLowerCase());
			System.out.println("BEGIN DEBUG:");
			for(String s : newModifiers)
			{
				System.out.println(s);
			}
			tag.Upload(newModifiers);

		}
		else
			System.out.println("[AdvancedBasev2] Attempting to remove Member that is not a member of Tag.");
		Database.DownloadNew();
	}
}

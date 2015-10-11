package me.savant.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.savant.base.Modifier;
import me.savant.base.Tag;
import me.savant.cuboid.Cuboid;

public class Database {
	
	
	public static List<Tag> containments = new ArrayList<>();
	
	public static void initialize()
	{
		containments = Config.getTags();
		System.out.println("===================================================================");
		System.out.println("[AdvancedBase] Finished Downloading Config --> Interal Database");
		System.out.println("===================================================================");
	}
	
	public static List<Tag> getTags()
	{
		return containments;
	}
	
	public static void DownloadNew()
	{
		containments.clear();
		containments = Config.getTags();
	}
	
	public static void UploadCurrent()
	{
		if(containments == null || containments.toArray().length == 0)
			return;
		for(Tag tag : containments)
		{
			containments.remove(tag);
			tag.Upload();
			containments.add(tag);
		}
	}
	
	public static void add(Tag tag)
	{
		containments.add(tag);
	}
	
	public static void remove(Tag tag)
	{
		containments.remove(tag);
	}
	
	public static void update(Tag tag)
	{
		containments.remove(tag);
		tag.Downlaod();
		containments.add(tag);
	}
	
	public static boolean hasCore(Player p)
	{
		for(Tag tag : containments)
		{
			List<String> modifiers = tag.DownlaodData();
			if(Modifier.contains(modifiers, "Player", p.getName()))
				if(Modifier.contains(modifiers, "display_name", "core"))
					return true;
		}
		return false;
	}
	public static Cuboid getCore(Player p)
	{
		for(Tag tag : containments)
		{
			List<String> modifiers = tag.DownlaodData();
			if(Modifier.contains(modifiers, "Player", p.getName()))
				if(Modifier.contains(modifiers, "display_name", "core"))
					return tag.getCuboid();
		}
		return null;
	}
	public static boolean isCore(Block block)
	{
		for(Tag tag : containments)
		{
			if(tag.getCuboid().isInside(block))
				if(Modifier.contains(tag.DownlaodData(), "display_name", "core"))
					return true;
		}
		return false;
	}
}

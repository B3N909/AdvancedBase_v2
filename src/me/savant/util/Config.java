package me.savant.util;

import java.util.ArrayList;
import java.util.List;
import me.savant.base.Main;
import me.savant.base.Tag;
import me.savant.cuboid.Cuboid;

public class Config {
	
	public static Main plugin;
	
	public static Main file;
	
	public static void Save()
	{
		plugin.saveConfig();
	}
	
	public static List<Tag> getTags()
	{
		List<Tag> list = new ArrayList<>();
		if(file == null)
		{
			System.out.println("[AdvancedBasev2] Config not set?");
		}
		if(file.getConfig().getConfigurationSection("") == null)
		{
			System.out.println("[AdvancedBasev2] Config Section not set?");
		}
		for(String key : file.getConfig().getConfigurationSection("").getKeys(false))
		{
			if(!key.equalsIgnoreCase("tier"))
			{
				list.add(new Tag(Cuboid.parseCuboid(key), getModifiers(key)));
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getModifiers(String key)
	{
		return (List<String>) file.getConfig().getList(key);
	}
	
	public static void setModifiers(String key, List<String> list)
	{
		file.getConfig().set(key, list);
		Save();
	}
	
	public static void setEmpty(String key)
	{
		file.getConfig().set(key, null);
		Save();
		Database.DownloadNew();
	}
}

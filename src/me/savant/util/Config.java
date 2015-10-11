package me.savant.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.savant.base.Main;
import me.savant.base.Tag;
import me.savant.cuboid.Cuboid;

public class Config {
	
	public static Main plugin;
	
	public static FileConfiguration file;
	public static File io;
	
	public static void Save()
	{
		try {
			file.save(io);
		} catch (IOException e) {
			System.out.println("[AdvancedBase_v2] Cannot save database to config.");
		}
	}
	
	public static List<Tag> getTags()
	{
		List<Tag> list = new ArrayList<>();
		for(String key : file.getConfigurationSection("").getKeys(false))
		{
			list.add(new Tag(Cuboid.parseCuboid(key), getModifiers(key)));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getModifiers(String key)
	{
		return (List<String>) file.getList(key);
	}
	
	public static void setModifiers(String key, List<String> list)
	{
		file.set(key, list);
		Save();
	}
	
	public static void setEmpty(String key)
	{
		file.set(key, null);
		Save();
		Database.DownloadNew();
	}
}

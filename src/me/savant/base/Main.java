package me.savant.base;

import me.savant.listener.Break;
import me.savant.listener.Click;
import me.savant.listener.Commands;
import me.savant.listener.Explosion;
import me.savant.listener.Place;

import java.io.File;
import java.io.IOException;

import me.savant.util.Config;
import me.savant.util.Database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static float version = 1.1f;
	public float Version()
	{
		return version;
	}
	
	public void onEnable()
	{
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Place(), this);
		pm.registerEvents(new Break(), this);
		pm.registerEvents(new Click(), this);
		pm.registerEvents(new Explosion(), this);
		
		getCommand("part").setExecutor(new Commands());
		getCommand("base").setExecutor(new Commands());
		
		Tag.plugin = this;
		Config.plugin = this;
		
		File f = new File("base.yml");
		Config.file = new YamlConfiguration();
		Config.io = f;
		try {
			Config.file.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("===================================================================");
		System.out.println("[AdvancedBase] Enabled Version " + Version() + " Created by _Savant");
		System.out.println("===================================================================");
		System.out.println("===================================================================");
		System.out.println("[AdvancedBase] Starting Internal Download of Config --> Database");
		System.out.println("===================================================================");
		
		Database.initialize();
	}
	
}

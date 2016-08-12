package me.savant.base;

import me.savant.listener.Commands;
import me.savant.util.Config;
import me.savant.util.Database;
import me.savant.util.Tier;
import me.savant.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public static float version = 2.9f;
	public float Version()
	{
		return version;
	}
	
	PluginManager pm;
	public void onEnable()
	{
		pm = Bukkit.getPluginManager();
		pm.registerEvents(new Util(), this);
		
		getCommand("part").setExecutor(new Commands());
		getCommand("base").setExecutor(new Commands());
		
		Tag.plugin = this;
		Config.plugin = this;
		Menu.plugin = this;
		
		Config.file = this;
		Tier.file = this;
		
		System.out.println("===================================================================");
		System.out.println("[AdvancedBase] Enabled Version " + Version() + " Created by _Savant");
		System.out.println("===================================================================");
		System.out.println("===================================================================");
		System.out.println("[AdvancedBase] Starting Internal Download of Config --> Database");
		System.out.println("===================================================================");
		
		Database.initialize();
	}
	
	public void registerEvents(Listener listener)
	{
		pm.registerEvents(listener, this);
	}
	
}

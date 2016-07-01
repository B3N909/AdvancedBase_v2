package me.savant.base;

import me.savant.listener.Break;
import me.savant.listener.Click;
import me.savant.listener.Commands;
import me.savant.listener.Explosion;
import me.savant.listener.Move;
import me.savant.listener.Place;
import me.savant.util.Config;
import me.savant.util.Database;
import me.savant.util.Tier;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public static float version = 2.2f;
	public float Version()
	{
		return version;
	}
	
	PluginManager pm;
	public void onEnable()
	{
		pm = Bukkit.getPluginManager();
		pm.registerEvents(new Place(), this);
		pm.registerEvents(new Break(), this);
		pm.registerEvents(new Click(), this);
		pm.registerEvents(new Explosion(), this);
		pm.registerEvents(new Move(), this);
		
		getCommand("part").setExecutor(new Commands());
		getCommand("base").setExecutor(new Commands());
		
		Tag.plugin = this;
		Config.plugin = this;
		Menu.plugin = this;
		Click.plugin = this;
		
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

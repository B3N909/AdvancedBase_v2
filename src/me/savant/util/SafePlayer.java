package me.savant.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.savant.cuboid.Cuboid;

public class SafePlayer {
	
	public static boolean canPlace(Cuboid region, Player p)
	{
		return !region.isInside(p.getLocation().getBlock());
	}
	
	public static void start(final Cuboid region, final Player p)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("AdvancedBasev2"), new Runnable()
		{
			@Override
			public void run() {
				Location pLoc = p.getLocation();
				if(region.isInside(pLoc.getBlock()))
				{
					int x = pLoc.getBlockX();
					int z = pLoc.getBlockZ();
					while(!new Location(pLoc.getWorld(), x, pLoc.getBlockY(), pLoc.getBlockZ()).getBlock().getType().equals(Material.AIR))
						x++;
					while(!new Location(pLoc.getWorld(), pLoc.getBlockX(), pLoc.getBlockY(), z).getBlock().getType().equals(Material.AIR))
						z++;
					x = x + 2;
					z = z + 2;
					p.teleport(new Location(pLoc.getWorld(), x, pLoc.getBlockY(), z));
					p.sendMessage(Chat.def + "Moved outside of " + Chat.red + "block");
				}
			}
			
		}, 20L);
	}
	
	public static void fix(Player p)
	{
		Location pLoc = p.getLocation();
		if(!pLoc.getBlock().getType().equals(Material.AIR))
		{
			int x = pLoc.getBlockX();
			int z = pLoc.getBlockZ();
			while(!new Location(pLoc.getWorld(), x, pLoc.getBlockY(), pLoc.getBlockZ()).getBlock().getType().equals(Material.AIR))
				x++;
			while(!new Location(pLoc.getWorld(), pLoc.getBlockX(), pLoc.getBlockY(), z).getBlock().getType().equals(Material.AIR))
				z++;
			x = x + 2;
			z = z + 2;
			
			p.teleport(new Location(pLoc.getWorld(), x, pLoc.getBlockY(), z));
			
			p.sendMessage(Chat.def + "Avoided Damage; Moved you outside of " + Chat.red + "block");
		}
	}
	
}

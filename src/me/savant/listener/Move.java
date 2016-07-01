package me.savant.listener;

import java.util.List;

import me.savant.base.Modifier;
import me.savant.util.Database;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Move implements Listener
{
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		Block above = getBlockAboveHead(e.getTo().clone()).getBlock();
		if(above.getType() == Material.EMERALD_BLOCK)
		{ 
			if(Modifier.getTag(above) != null)
			{
				List<String> modifiers = Modifier.getTag(above).DownlaodData();
				List<String> coreModifiers = Database.getCoreModifiers(Modifier.getOwner(modifiers).getPlayer());
				if(!Modifier.isOwner(coreModifiers, p))
				{
					if(!Modifier.isMember(coreModifiers, p))
					{
						p.teleport(e.getFrom());
						ActionBarAPI.sendActionBar(p, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Access Denied", 10);
					}
				}
			}
		}
	}
	
	private Location getBlockAboveHead(Location loc)
	{
		Location loc1 = loc.add(0, 2, 0);
		return loc1;
	}
}

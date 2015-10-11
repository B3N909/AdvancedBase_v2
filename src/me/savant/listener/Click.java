package me.savant.listener;

import me.savant.base.Modifier;
import me.savant.base.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Click implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{
		if(e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			Player p = e.getPlayer();
			Tag tag = Modifier.getTag(p);
			if(p.getTargetBlock(null, 2) != null && tag != null)
			{
				if(p.isSneaking())
				{
					if(((Player)Modifier.getOwner(tag.DownlaodData())).getName().equalsIgnoreCase(p.getName()))
					{
						tag.Break(p);
					}
				}
			}
		}
		else if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Player p = e.getPlayer();
			Tag tag = Modifier.getTag(p);
			if(p.getTargetBlock(null, 2) != null && tag != null)
			{
				if(p.isSneaking())
				{
					if(((Player)Modifier.getOwner(tag.DownlaodData())).getName().equalsIgnoreCase(p.getName()))
					{
						
					}
				}
			}
		}
	}
	
}

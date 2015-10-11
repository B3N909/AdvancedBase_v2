package me.savant.listener;

import me.savant.base.Modifier;
import me.savant.base.Tag;
import me.savant.util.Damage;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Explosion implements Listener {

	@EventHandler
	public void onTNT(EntityExplodeEvent e)
	{
		for(Block b : e.blockList())
		{
			Tag tag = Modifier.getTag(b);
			if(tag != null)
			{
				Damage.takeDamage(tag);
			}
		}
		e.setCancelled(true);
	}
}

package me.savant.listener;

import me.savant.base.Tag;
import me.savant.util.Database;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		//TODO: Optimize Better
		Block block = e.getBlock();
		for(Tag tag : Database.getTags())
		{
			if(tag.getCuboid() != null && tag.getCuboid().isInside(block))
			{
				tag.Break(e.getPlayer());
				return;
			}
		}
	}
	
}

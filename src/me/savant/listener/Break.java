package me.savant.listener;

import java.util.List;

import me.savant.base.Modifier;
import me.savant.base.Tag;
import me.savant.util.Chat;
import me.savant.util.Damage;
import me.savant.util.Database;
import me.savant.util.Name;
import me.savant.util.Tier;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
				List<String> modifiers = tag.DownlaodData();
				e.setCancelled(true);
				if(e.getPlayer().isOp() && e.getPlayer().isSneaking())
				{
					tag.Break(e.getPlayer());
				}
				if(Modifier.getOwner(modifiers).getName().equalsIgnoreCase(e.getPlayer().getName()))
				{
					tag.Break(e.getPlayer());
					return;
				}
				else
				{
					if(Modifier.getOwner(modifiers).isOnline())
					{
						Damage.takeDamage(tag);
						int maxDamage = Damage.getMaxDamage(Tier.getTier(modifiers));
						int damage = Damage.getDamage(tag);
						e.getPlayer().sendMessage(Chat.red + Name.getRawName(modifiers).split("_t")[0] + Chat.def + " -> " + Chat.red + " (" + damage + "/" + maxDamage + ")");
						((Player)Modifier.getOwner(modifiers)).sendMessage(Chat.msg + "[ Base -> me ] Pssst.... You'r base is being raided!");
						return;
					}
					else
					{
						e.getPlayer().sendMessage(Chat.def + "Owner " + Chat.red + Modifier.getOwner(modifiers).getName() + Chat.def + " is not online");
						return;
					}
				}
			}
		}
	}
	
}

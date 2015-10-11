package me.savant.base;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import me.savant.cuboid.Cuboid;
import me.savant.util.Config;
import me.savant.util.Database;
import me.savant.util.Item;
import me.savant.util.Name;

public class Tag {
	
	
	Cuboid region;
	List<String> modifiers;
	
	public Tag(Cuboid region)
	{
		this.region = region;
	}
	
	public Cuboid getCuboid()
	{
		return region;
	}
	
	public Tag(Cuboid region, List<String> modifiers)
	{
		this.region = region;
		this.modifiers = modifiers;
	}
	
	public void Upload(List<String> modifiers)
	{
		this.modifiers = modifiers;
		Config.setModifiers(region.toString(), modifiers);
	}
	
	public void Upload()
	{
		Config.setModifiers(region.toString(), modifiers);
	}
	
	public void Downlaod()
	{
		modifiers = Config.getModifiers(region.toString());
	}
	
	public void empty()
	{
		Config.setEmpty(region.toString());
	}
	
	public List<String> DownlaodData()
	{
		modifiers = Config.getModifiers(region.toString());
		return modifiers;
	}
	
	public void Break(Player breaker)
	{
		Location origin = region.getOrigin().getLocation();
		for(int x = 0; x < region.getLength(); x++)
		{
			final int x1 = x;
			for(int y = 0; y < region.getHeight(); y++)
			{
				final int y1 = y;
				for(int z = 0; z < region.getWidth(); z++)
				{
					final int z1 = z;
					new Location(region.getWorld(), x1 + origin.getX(), y1 + origin.getY(), z1 + origin.getZ()).getBlock().setType(Material.AIR);;
				}
			}
		}
		Item.giveItem(breaker, Name.getRawName(this));
		empty();
	}
	
	public static Main plugin;
	public static void Break(final Tag tag)
	{
		final Location origin = tag.getCuboid().getOrigin().getLocation();
		for(int x = 0; x < tag.getCuboid().getLength(); x++)
		{
			final int x1 = x;
			for(int y = 0; y < tag.getCuboid().getHeight(); y++)
			{
				final int y1 = y;
				for(int z = 0; z < tag.getCuboid().getWidth(); z++)
				{
					final int z1 = z;
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
					{
						public void run()
						{
							Block block = new Location(tag.getCuboid().getWorld(), x1 + origin.getX(), y1 + origin.getY(), z1 + origin.getZ()).getBlock();
							block.setType(Material.AIR);
							for(Player p : Bukkit.getOnlinePlayers())
							{
								p.playEffect(block.getLocation(), Effect.STEP_SOUND, 15);
							}
						}
					}, 5 * x + 5 * y + 5 * z);
				}
			}
		}
		tag.empty();
	}
	
}

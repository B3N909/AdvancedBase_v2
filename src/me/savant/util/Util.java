package me.savant.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class Util implements Listener
{
	
	static boolean canWork = true;
	public static boolean work()
	{
		if(canWork)
		{
			canWork = false;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("AdvancedBaseAPI"), new Runnable()
			{
				@Override
				public void run() {
					canWork = true;
				}
				
			}, 5L);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Vector getCardinalDirection(Player player)
	{
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5)
         {
            return new Vector(-1, 0, 0);
        }
         else if (22.5 <= rotation && rotation < 67.5)
         {
            return new Vector(-1, 0, -1);
        }
        else if (67.5 <= rotation && rotation < 112.5)
        {
            return new Vector(0, 0, -1);
        } 
       else if (112.5 <= rotation && rotation < 157.5)
       {
            return new Vector(1, 0, -1);
        } 
       else if (157.5 <= rotation && rotation < 202.5)
       {
            return new Vector(1, 0, 0);
        }
        else if (202.5 <= rotation && rotation < 247.5)
        {
            return new Vector(1, 0, 1);
        }
        else if (247.5 <= rotation && rotation < 292.5)
        {
            return new Vector(0, 0, 1);
        }
        else if (292.5 <= rotation && rotation < 337.5)
        {
            return new Vector(-1, 0, 1);
        }
        else if (337.5 <= rotation && rotation < 360.0)
        {
            return new Vector(-1, 0, 0);
        }
        else
        {
            return null;
        }
    }
	
	public static Vector getCardinalOpositeDirection(Player player)
	{
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5)
        {
           return new Vector(1, 0, 0);
       }
        else if (22.5 <= rotation && rotation < 67.5)
        {
           return new Vector(1, 0, 1);
       }
       else if (67.5 <= rotation && rotation < 112.5)
       {
           return new Vector(0, 0, 1);
       } 
      else if (112.5 <= rotation && rotation < 157.5)
      {
           return new Vector(-1, 0, 1);
       } 
      else if (157.5 <= rotation && rotation < 202.5)
      {
           return new Vector(-1, 0, 0);
       }
       else if (202.5 <= rotation && rotation < 247.5)
       {
           return new Vector(-1, 0, -1);
       }
       else if (247.5 <= rotation && rotation < 292.5)
       {
           return new Vector(0, 0, -1);
       }
       else if (292.5 <= rotation && rotation < 337.5)
       {
           return new Vector(1, 0, -1);
       }
       else if (337.5 <= rotation && rotation < 360.0)
       {
           return new Vector(1, 0, 0);
       }
       else
       {
           return null;
       }
    }
}

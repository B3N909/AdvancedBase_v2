package me.savant.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Cuboid {
	
	Block origin;
	int length;
	int height;
	int width;
	
	public Block getOrigin() {
		return origin;
	}
	
	public World getWorld()
	{
		return origin.getWorld();
	}

	public void setOrigin(Block origin) {
		this.origin = origin;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Cuboid(Block origin, int length, int width, int height)
	{
		this.origin = origin;
		this.length = length;
		this.width = width;
		this.height = height;
	}
	
	public boolean isInside(Block block)
	{
		for(int x = (int)origin.getX(); x < (int) origin.getX() + length; x++)
		{
			for(int y = (int)origin.getY(); y < (int) origin.getY() + height; y++)
			{
				for(int z = (int)origin.getZ(); z < (int) origin.getZ() + width; z++)
				{
					if(new Location(origin.getWorld(), x, y, z).equals(block.getLocation()))
						return true;
				}
			}
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		Location loc = origin.getLocation();
		return "Cuboid [origin=" + loc.getWorld().getName() + "/" + loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ()
				+ " length=" + length
				+ " width=" + width
				+ " height=" + height + "]";
	}

	public static Cuboid parseCuboid(String string) throws NumberFormatException
	{
		String[] args1 = string.split("=");
		String[] args2 = args1[1].split("/");
		World world = Bukkit.getWorld(args1[1].split("/")[0]);
		int x = Integer.parseInt(args2[1]);
		int y = Integer.parseInt(args2[2]);
		int z = Integer.parseInt(args2[3].split(" ")[0]);
		Location loc = new Location(world, x, y, z);
		int length = Integer.parseInt(args1[2].replace(" width", ""));
		int width = Integer.parseInt(args1[3].replace(" height", ""));
		int height = Integer.parseInt(args1[4].replace("]", ""));
		return new Cuboid(loc.getBlock(), length, width, height);
	}
	
	
	
	
	
	
	
	
	
	
}

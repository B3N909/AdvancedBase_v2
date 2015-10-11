package me.savant.util;

import me.savant.base.Modifier;
import me.savant.base.Tag;

public class Damage {
	public static int getDamage(Tag tag)
	{
		return Integer.parseInt(Modifier.getSuffix(tag.DownlaodData(), "health"));
	}
	public static void takeDamage(Tag tag)
	{
		tag.Upload(Modifier.modify(tag.DownlaodData(), "health", (getDamage(tag) - 100) + ""));
		if(getDamage(tag) <= 0)
		{
			Tag.Break(tag);
		}
	}
	public static int getMaxDamage(int tier)
	{
		return (tier *  100) + 400;
	}
}

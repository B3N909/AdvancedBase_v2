package me.savant.util;

public class Price 
{
	public static int getRawPrice(String rawName, int tier)
	{
		String name = rawName.split("_")[0];
		if(name.equalsIgnoreCase("core"))
		{
			return tier * 30;
		}
		else if(name.equalsIgnoreCase("wall"))
		{
			return (int)((int)tier * 1.25);
		}
		else if(name.equalsIgnoreCase("corner"))
		{
			return tier * 5;
		}
		else if(name.equalsIgnoreCase("chest"))
		{
			return tier * 10;
		}
		else
		{
			return tier * 10;
		}
	}
	
	public static int getTier(String rawName, int price)
	{
		String name = rawName.split("_")[0];
		if(name.equalsIgnoreCase("core"))
		{
			return price / 30;
		}
		else if(name.equalsIgnoreCase("wall"))
		{
			return (int)((int)price / 2);
		}
		else if(name.equalsIgnoreCase("corner"))
		{
			return price / 5;
		}
		else if(name.equalsIgnoreCase("chest"))
		{
			return price / 10;
		}
		else
		{
			return price / 10;
		}
	}
}

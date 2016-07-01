package me.savant.util;

import java.util.List;

import me.savant.base.Modifier;

public class Size {
	public static int getWidth(List<String> modifiers)
	{
		return Integer.parseInt(Modifier.getSuffix(modifiers, "width"));
	}
	public static int getLength(List<String> modifiers)
	{
		return Integer.parseInt(Modifier.getSuffix(modifiers, "length"));
	}
	public static int getHeight(List<String> modifiers)
	{
		return Integer.parseInt(Modifier.getSuffix(modifiers, "height"));
	}
}

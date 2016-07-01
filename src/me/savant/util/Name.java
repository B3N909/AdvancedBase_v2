package me.savant.util;

import java.util.List;

import me.savant.base.Modifier;
import me.savant.base.Tag;

public class Name {
	public static String getRawName(Tag tag)
	{
		return Modifier.getSuffix(tag.DownlaodData(), "display_name") + "_t" + Modifier.getSuffix(tag.DownlaodData(), "tier");
	}
	public static String getRawName(List<String> modifiers)
	{
		return Modifier.getSuffix(modifiers, "display_name") + "_t" + Modifier.getSuffix(modifiers, "tier");
	}
	public static String buildFromTier(List<String> modifiers, int tier)
	{
		System.out.println(getRawName(modifiers).split("_t")[0] + tier);
		return getRawName(modifiers).split("_t")[0] + "_t" + tier;
	}
}

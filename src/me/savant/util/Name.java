package me.savant.util;

import me.savant.base.Modifier;
import me.savant.base.Tag;

public class Name {
	public static String getRawName(Tag tag)
	{
		return Modifier.getSuffix(tag.DownlaodData(), "display_name") + "_t" + Modifier.getSuffix(tag.DownlaodData(), "tier");
	}
}

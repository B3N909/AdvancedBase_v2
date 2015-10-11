package me.savant.util;

import java.util.List;

import me.savant.base.Index;
import me.savant.base.Modifier;
import me.savant.base.Part;
import me.savant.base.SchematicRefrence;
import me.savant.base.Tag;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Tier {
	public static void upgradeAt(Player p, int tier)
	{
		Block block = p.getTargetBlock(null, 20);
		Tag tag = Modifier.getTag(block);
		List<String> modifiers = tag.DownlaodData();
		int width = tag.getCuboid().getWidth();
		int length = tag.getCuboid().getLength();
		int height = tag.getCuboid().getHeight();
		Location origin = tag.getCuboid().getOrigin().getLocation();
		
		String name = Modifier.getSuffix(modifiers, "display_name") + "_t" + Modifier.getSuffix(modifiers, "tier");
		
		Tag.tagRegion(origin, width, length, height, plugin, "Tier " + tier);
		Tag.tagRegion(origin, width, length, height, plugin, "Name " + name);
		Index index = new Index(Schematic.Center(origin, length, width), new Part(SchematicRefrence.Parse(name)), p);
	}
}

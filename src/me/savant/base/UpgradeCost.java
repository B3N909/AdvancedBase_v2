package me.savant.base;

import org.bukkit.Material;

public class UpgradeCost
{
	private String unit;
	private int amount;
	private Material type;
	
	public UpgradeCost(String unit, int amount, Material type)
	{
		this.unit = unit;
		this.amount = amount;
		this.type = type;
	}
	
	public String getUnit()
	{
		return unit;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public Material getType()
	{
		return type;
	}
}

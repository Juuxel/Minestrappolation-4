package minestrapteam.mods.minestrappolation.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MStacks
{
	public static boolean equals(ItemStack input, ItemStack target)
	{
		return itemEquals(input, target);
	}
	
	public static boolean itemEquals(ItemStack input, ItemStack target)
	{
		if (input == target)
			return true;
		else if (input == null)
			return target == null;
		else if (target == null)
			return false;
		return equals(input.getItem(), input.getItemDamage(), input.stackSize, target.getItem(), target.getItemDamage(), target.stackSize);
	}
	
	public static boolean equals(Item item1, int meta1, int amount, Item item2, int meta2, int amount2)
	{
		return item1 == item2 && (meta1 == meta2 || meta1 == OreDictionary.WILDCARD_VALUE || meta2 == OreDictionary.WILDCARD_VALUE) && amount >= amount2;
	}
	
	public static int mergeItemStack(ItemStack[] stacks, int start, ItemStack stack)
	{
		return mergeItemStack(stacks, stacks.length, stack);
	}
	
	public static int mergeItemStack(ItemStack[] stacks, int start, int end, ItemStack stack)
	{
		int i = -1;
		int max = stack.getMaxStackSize();
		ItemStack stack1;
		
		for (int j = start; j < end && stack.isStackable() && stack.stackSize > 0; j++)
		{
			stack1 = stacks[j];
			if (stack1 != null && equals(stack, stack1))
			{
				int k = stack1.stackSize + stack.stackSize;
				if (k <= max)
				{
					stack.stackSize = 0;
					stack1.stackSize = k;
					i = j;
				}
				else if (stack1.stackSize < max)
				{
					stack.stackSize -= max - stack1.stackSize;
					stack1.stackSize = max;
					i = j;
				}
			}
		}
		
		if (stack.stackSize > 0)
		{
			for (int j = start; j < end; j++)
			{
				stack1 = stacks[j];
				if (stack1 == null)
				{
					stacks[j] = stack.copy();
					stack.stackSize = 0;
					return j;
				}
			}
		}
		return i;
	}
}

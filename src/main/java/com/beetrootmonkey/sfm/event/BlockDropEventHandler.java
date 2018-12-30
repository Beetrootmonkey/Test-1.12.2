package com.beetrootmonkey.sfm.event;

import java.util.ArrayList;
import java.util.List;

import com.beetrootmonkey.sfm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class BlockDropEventHandler {
	
	@SubscribeEvent
	public void onBlockDrop(BlockEvent.HarvestDropsEvent event) {
		System.out.println("onBlockDrop");
		if (event.getState().getBlock().equals(Blocks.IRON_ORE)) {
			replaceDrop(Item.getItemFromBlock(Blocks.IRON_ORE), new ItemStack(ModItems.IRON_ORE), event);
		} else if (event.getState().getBlock().equals(Blocks.GOLD_ORE)) {
			replaceDrop(Item.getItemFromBlock(Blocks.GOLD_ORE), new ItemStack(ModItems.GOLD_ORE), event);
		}
	}


	private void replaceDrop(Item filter, ItemStack replacement, BlockEvent.HarvestDropsEvent event) {
		List<ItemStack> toRemove = new ArrayList<ItemStack>();
		for (ItemStack itemStack : event.getDrops()) {
			if (itemStack.getItem().equals(filter)) {
				replacement.setCount(itemStack.getCount());
				toRemove.add(itemStack);
				break;
			}
		}
		
		if(!toRemove.isEmpty()) {
			event.getDrops().removeAll(toRemove);
			event.getDrops().add(replacement);
		}
		
	}
}

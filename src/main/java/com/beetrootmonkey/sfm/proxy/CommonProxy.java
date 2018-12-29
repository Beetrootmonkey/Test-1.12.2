package com.beetrootmonkey.sfm.proxy;

import com.beetrootmonkey.sfm.blocks.ModBlocks;
import com.beetrootmonkey.sfm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public abstract class CommonProxy {

	public void preInit() {
		
	}

	public void init() {
	}

	public void postInit() {
	}

	abstract public boolean isDedicatedServer();

	public abstract void registerItemRenderer(Item item, int meta, String id);
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.registerBlocks();
	}
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
		ModItems.registerItems();
	}
}

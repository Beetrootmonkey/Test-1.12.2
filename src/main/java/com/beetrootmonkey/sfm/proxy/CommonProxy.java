package com.beetrootmonkey.sfm.proxy;

import com.beetrootmonkey.sfm.items.ModItems;

import net.minecraft.item.Item;

public abstract class CommonProxy {

	public void preInit() {
		ModItems.registerItems();
	}

	public void init() {
	}

	public void postInit() {
	}

	abstract public boolean isDedicatedServer();

	public void registerItemRenderer(Item item, int meta, String id) {
		
	}
}

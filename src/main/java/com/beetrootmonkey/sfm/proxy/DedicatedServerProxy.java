package com.beetrootmonkey.sfm.proxy;

import net.minecraft.item.Item;

public class DedicatedServerProxy extends CommonProxy {

	public void preInit() {
		super.preInit();
	}

	public void init() {
		super.init();
	}

	public void postInit() {
		super.postInit();
	}

	@Override
	public boolean isDedicatedServer() {
		return true;
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		// Should not do anything except when on client
		
	}
}

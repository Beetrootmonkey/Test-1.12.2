package com.beetrootmonkey.sfm.proxy;

import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientOnlyProxy extends CommonProxy {

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
		return false;
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Main.MOD_ID + ":" + id, "inventory");
	    final int DEFAULT_ITEM_SUBTYPE = 0;
	    ModelLoader.setCustomModelResourceLocation(item, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	}
}
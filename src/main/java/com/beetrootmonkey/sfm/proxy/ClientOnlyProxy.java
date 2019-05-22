package com.beetrootmonkey.sfm.proxy;

import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientOnlyProxy extends CommonProxy {

	@Override
    public void preInit() {
		super.preInit();
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
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
	    ModelLoader.setCustomModelResourceLocation(item, meta, itemModelResourceLocation);
	}
}
package com.beetrootmonkey.sfm.proxy;

import com.beetrootmonkey.sfm.blocks.ModBlocks;
import com.beetrootmonkey.sfm.crafting.ModRecipes;
import com.beetrootmonkey.sfm.items.ModItems;
import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public abstract class CommonProxy {

	public void preInit() {
		
	}

	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiProxy());
	}

	public void postInit() {
		
	}

	abstract public boolean isDedicatedServer();

	public abstract void registerItemRenderer(Item item, int meta, String id);
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.register();
	}
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
		ModItems.register();
	}
	
	@SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		ModRecipes.register();
	}
}

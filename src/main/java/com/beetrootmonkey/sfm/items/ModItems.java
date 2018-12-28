package com.beetrootmonkey.sfm.items;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static Item item1;
	
	public static void registerItems() {
		item1 = register(new ItemBase("item1"));
	}
	
	private static <T extends ItemBase> T register(T item) {
		ForgeRegistries.ITEMS.register(item);

		((ItemModelProvider) item).registerItemModel(item);

		return item;
	}
}

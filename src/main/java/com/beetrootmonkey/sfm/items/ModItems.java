package com.beetrootmonkey.sfm.items;

import java.util.ArrayList;
import java.util.List;

import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import scala.actors.threadpool.Arrays;

@ObjectHolder(Main.MOD_ID)
public class ModItems {

	@ObjectHolder("item1")
	public static Item PITCHFORK;
	
	@ObjectHolder("iron_ore")
	public static Item IRON_ORE;
	
	@ObjectHolder("gold_ore")
	public static Item GOLD_ORE;
	
	private static ItemBase[] items = new ItemBase[]
	{
		new ItemBase("item1"),
		new ItemBase("iron_ore"),
		new ItemBase("gold_ore")
	};

	public static void register() {
		List<ItemBase> list = new ArrayList<>(Arrays.asList(items));
		list.stream().forEach(i -> register(i));
	}

	private static void register(ItemBase item) {
		ForgeRegistries.ITEMS.register(item);

		((ItemModelProvider) item).registerItemModel();
	}
}

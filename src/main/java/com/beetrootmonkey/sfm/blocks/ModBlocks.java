package com.beetrootmonkey.sfm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.beetrootmonkey.sfm.blocks.trough.TroughBlock;
import com.beetrootmonkey.sfm.blocks.trough.TroughTE;
import com.beetrootmonkey.sfm.items.ItemBase;
import com.beetrootmonkey.sfm.items.ItemModelProvider;
import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import scala.actors.threadpool.Arrays;

@ObjectHolder(Main.MOD_ID)
public class ModBlocks {
	
	@ObjectHolder("block1")
	public static Item block1;
	
	private static BlockBase[] blocks = new BlockBase[]
	{
		new TroughBlock("block1")
	};

	public static void register() {
		List<BlockBase> list = new ArrayList<>(Arrays.asList(blocks));
		list.stream().forEach(b -> register(b));
	}

	private static void register(BlockBase block) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(block.itemBlock);

		((ItemModelProvider) block).registerItemModel();
		Class clazz = block.getTEClass();
		if (clazz != null) {
			GameRegistry.registerTileEntity(clazz, new ResourceLocation(Main.MOD_ID + ":blocktrough"));
		}
	}
}

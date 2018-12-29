package com.beetrootmonkey.sfm.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockTrough extends BlockBase {

	 public BlockTrough(String name) {
		 super(Material.WOOD, name);
		 
		 setHardness(2f);
		 setResistance(12f);
	 }
}

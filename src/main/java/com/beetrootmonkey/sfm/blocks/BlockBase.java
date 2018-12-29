package com.beetrootmonkey.sfm.blocks;

import java.util.Random;

import com.beetrootmonkey.sfm.blocks.trough.TroughTE;
import com.beetrootmonkey.sfm.items.ItemModelProvider;
import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBase extends Block implements ItemModelProvider {

	protected String name;
	protected ItemBlock itemBlock;

	public BlockBase(Material material, String name) {
		super(material);
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.creativeTab);
		setHarvestLevel("axe", 1);
		
		itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(name);
		itemBlock.setUnlocalizedName(name);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public void registerItemModel() {
		Main.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	
	public Class<? extends TileEntity> getTEClass() {
		return null;
	}
}

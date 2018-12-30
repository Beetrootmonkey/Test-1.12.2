package com.beetrootmonkey.sfm.blocks.barnfloor;

import java.util.List;
import java.util.Random;

import com.beetrootmonkey.sfm.blocks.BlockBase;
import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BarnFloorBlock extends BlockBase {

	private int counter = 0;
	private static final int maxCounter = 10;
	private static final int rangeH = 1;
	private static final int rangeV = 2;
	private int meta = 0;
	private static final int maxMeta = 4;
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, maxMeta);

	public BarnFloorBlock(String name) {
		super(Material.GROUND, name);

		setHardness(2f);
		setResistance(12f);
		setLightOpacity(0);
		setTickRandomly(true);
		setHarvestLevel("pickaxe", 1);
		
		itemBlock = new BarnFloorItemBlock(this);
		itemBlock.setRegistryName(name);
		itemBlock.setUnlocalizedName(name);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(state));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, Integer.valueOf(meta));
	}
	
//	@Override
//	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
//			EntityPlayer player) {
//		return getItem(world, pos, getStateFromMeta(0));
//	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(TYPE)).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public void registerItemModel() {
		Main.proxy.registerItemRenderer(itemBlock, 0, name + "_0");
		Main.proxy.registerItemRenderer(itemBlock, 1, name + "_1");
		Main.proxy.registerItemRenderer(itemBlock, 2, name + "_2");
		Main.proxy.registerItemRenderer(itemBlock, 3, name + "_3");
		Main.proxy.registerItemRenderer(itemBlock, 4, name + "_4");
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		if (worldIn.isRemote) {
			// Client
		} else {
			// Server
			counter++;
			if (counter >= maxCounter) {
				counter = 0;
				// Now update for real
				
				int type = getMetaFromState(state);
				if (type >= maxMeta) {
					return;
				}

				List<EntityAnimal> list = worldIn.getEntitiesWithinAABB(EntityAnimal.class,
						new AxisAlignedBB(pos.add(-rangeH, 0, -rangeH), pos.add(rangeH, rangeV, rangeH)));
				System.out.println("updateTick: " + type + " -> " + (type + 1));
				if (!list.isEmpty()) {
					worldIn.setBlockState(pos, this.getStateFromMeta(type + 1));
				}
			}
		}
	}

}

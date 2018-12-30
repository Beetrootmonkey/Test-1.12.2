package com.beetrootmonkey.sfm.blocks.nest;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.beetrootmonkey.sfm.blocks.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BarnFloorBlock extends BlockBase {

	private int counter = 0;
	private static final int maxCounter = 10;
	private static final int rangeH = 1;
	private static final int rangeV = 2;
	
	public BarnFloorBlock(String name) {
		super(Material.GROUND, name);

		setHardness(2f);
		setResistance(12f);
		setLightOpacity(0);
		setTickRandomly(true);
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

				List<EntityAnimal> list = worldIn.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(
						pos.add(-rangeH, 0, -rangeH), pos.add(rangeH, rangeV, rangeH)));

				System.out.println("updateTick: " + list.size());
				if (!list.isEmpty()) {
					worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
				}
			}
		}
	}
	
}

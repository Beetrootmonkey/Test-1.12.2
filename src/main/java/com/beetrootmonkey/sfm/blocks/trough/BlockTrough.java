package com.beetrootmonkey.sfm.blocks.trough;

import com.beetrootmonkey.sfm.blocks.BlockBase;
import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTrough extends BlockBase implements ITileEntityProvider {

	public BlockTrough(String name) {
		super(Material.WOOD, name);

		setHardness(2f);
		setResistance(12f);
		setLightOpacity(0);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		return super.canBeConnectedTo(world, pos, facing);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTrough();
	}

	@Override
	public Class<? extends TileEntity> getTEClass() {
		return TileEntityTrough.class;
	}
}

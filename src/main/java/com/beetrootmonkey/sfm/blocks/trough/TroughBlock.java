package com.beetrootmonkey.sfm.blocks.trough;

import com.beetrootmonkey.sfm.blocks.BlockBase;
import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TroughBlock extends BlockBase implements ITileEntityProvider {

	public static final int GUI_ID = 1;
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 4);
	protected static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D);

	public TroughBlock(String name) {
		super(Material.WOOD, name);

		setHardness(2f);
		setResistance(12f);
		setLightOpacity(0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
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
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return true;
		}
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.BOWL;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TroughTE();
	}

	@Override
	public Class<? extends TileEntity> getTEClass() {
		return TroughTE.class;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TroughTE) {
			TroughTE te = (TroughTE)tileEntity;
			int level = (int) Math.ceil(te.getItemStack().getCount() / 16f);
			level = MathHelper.clamp(level, 0, 4);
			return getDefaultState().withProperty(LEVEL, level);
		}
		return state;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TroughTE)) {
			return false;
		}
		TroughTE troughTe = (TroughTE) te;

		if (side == EnumFacing.UP) {
			ItemStack stackInTrough = troughTe.getItemStack();
			ItemStack stackInHand = player.getHeldItem(hand);
			boolean swap = true;

			if (!stackInTrough.isEmpty() && stackInTrough.getItem() == stackInHand.getItem()
					&& (!stackInHand.getHasSubtypes() || stackInHand.getMetadata() == stackInTrough.getMetadata())
					&& ItemStack.areItemStackTagsEqual(stackInHand, stackInTrough)) {
				
				int j = stackInTrough.getCount() + stackInHand.getCount();
				int maxSize = 64;

				if (j <= maxSize) {
					ItemStack copy1 = stackInTrough.copy();
					copy1.grow(stackInHand.getCount());

					troughTe.setItemStack(copy1);					
					player.setHeldItem(hand, ItemStack.EMPTY);
					swap = false;
				} else if (stackInTrough.getCount() < maxSize) {
					ItemStack copy1 = stackInTrough.copy();
					copy1.setCount(maxSize);
					
					ItemStack copy2 = stackInHand.copy();
					copy2.shrink(maxSize - stackInTrough.getCount());

					troughTe.setItemStack(copy1);					
					player.setHeldItem(hand, copy2);
					swap = false;
				} else if (stackInTrough.getCount() == maxSize) {
					swap = false;
				}
			}
			
			if (swap) {
				player.setHeldItem(hand, stackInTrough);
				troughTe.setItemStack(stackInHand);
			}

//			if (stackInTrough == ItemStack.EMPTY) {
//				if (stackInHand == ItemStack.EMPTY) {
//					// Do nothing
//					System.out.println("Both empty");
//				} else {
//					// Place item in trough
//					System.out.println("Place item in trough");
//					player.setHeldItem(hand, ItemStack.EMPTY);
//					troughTe.setItemStack(stackInHand);
//				}
//			} else {
//				if (stackInHand == ItemStack.EMPTY) {
//					// Retrieve item
//					System.out.println("Retrieve item");
//					player.setHeldItem(hand, stackInTrough);
//					troughTe.setItemStack(ItemStack.EMPTY);
//				} else {
//					// Do nothing
//					System.out.println("Both not empty");
//				}
//			}

		} else {
			player.openGui(Main.instance, GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	public void setLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
		worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 4))), 2);
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(LEVEL)).intValue();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LEVEL });
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TroughTE) {
			TroughTE troughTE = (TroughTE) te;
			ItemStack stack = troughTE.getItemStack();
			if (stack != null) {
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}

		super.breakBlock(world, pos, state);
	}
}

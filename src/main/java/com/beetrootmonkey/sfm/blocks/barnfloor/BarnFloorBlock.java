package com.beetrootmonkey.sfm.blocks.barnfloor;

import java.util.List;
import java.util.Random;

import com.beetrootmonkey.sfm.blocks.BlockBase;
import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BarnFloorBlock extends BlockBase {

	private int counter = 0;
	private static final int maxCounter = 10;
	private static final int rangeH = 1;
	private static final int rangeV = 2;
	private int meta = 0;
	private static final int maxMeta = 4;
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, maxMeta);
	protected static final AxisAlignedBB BB = new AxisAlignedBB(0, 0, 0, 1, 0.0625, 1);

	public BarnFloorBlock(String name) {
		super(Material.GROUND, name);
		this.useNeighborBrightness = true;
		setHardness(2f);
		setResistance(12f);
		setLightOpacity(0);
		setTickRandomly(true);
		setHarvestLevel("shovel", 1);
		
		itemBlock = new BarnFloorItemBlock(this);
		itemBlock.setRegistryName(name);
		itemBlock.setUnlocalizedName(name);
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	/**
	 * Determines if an entity can path through this block
	 *
	 * @param worldIn
	 * @param pos
	 */
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	/**
	 * @param state
	 * @return true if the state occupies all of its 1x1x1 cube
	 */
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 *
	 * @param state
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public int getLightOpacity(IBlockState state) {
		return 0;
	}

	/**
	 * Indicate if a material is a normal solid opaque cube
	 *
	 * @param state
	 */
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(final IBlockState state) {
		return false;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(state));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public void registerItemModel() {
		Main.proxy.registerItemRenderer(itemBlock, 0, name + "_0");
		Main.proxy.registerItemRenderer(itemBlock, 1, name + "_1");
		Main.proxy.registerItemRenderer(itemBlock, 2, name + "_2");
		Main.proxy.registerItemRenderer(itemBlock, 3, name + "_3");
		Main.proxy.registerItemRenderer(itemBlock, 4, name + "_4");
	}

	/**
	 * Checks if the block is a solid face on the given side, used by placement logic.
	 *
	 * @param base_state The base state, getActualState should be called first
	 * @param world      The current world
	 * @param pos        Block position in world
	 * @param side       The side to check
	 * @return True if the block is solid on the specified side.
	 */
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	/**
	 * Used by getTopSolidOrLiquidBlock while placing biome decorations, villages, etc
	 * Also used to determine if the player can spawn on this block.
	 *
	 * @param world
	 * @param pos
	 * @return False to disallow spawning
	 */
	@Override
	public boolean isFoliage(IBlockAccess world, BlockPos pos) {
		return false;
	}

	/**
	 * Used in the renderer to apply ambient occlusion
	 *
	 * @param state
	 */
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}

	/**
	 * Return true if an entity can be spawned inside the block (used to get the player's bed spawn location)
	 */
	@Override
	public boolean canSpawnInBlock() {
		return true;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Determines if another block can connect to this block
	 *
	 * @param world  The current world
	 * @param pos    The position of this block
	 * @param facing The side the connecting block is on
	 * @return True to allow another block to connect to this block
	 */
	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		return false;
	}

	public BarnFloorBlock(Material material, String name) {
		super(material, name);
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
						new AxisAlignedBB(pos.add(-rangeH, -0.5, -rangeH), pos.add(rangeH, rangeV, rangeH)));
				System.out.println("updateTick: " + type + " -> " + (type + 1));
				if (!list.isEmpty()) {
					worldIn.setBlockState(pos, this.getStateFromMeta(type + 1));
				}
			}
		}
	}

}

package com.beetrootmonkey.sfm.blocks.trough;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TroughTE extends TileEntity implements ITickable {

	private int counter = 0;
	private static final int maxCounter = 100;
	private static final int minAnimalCount = 2;
	private static final int maxAnimalCount = 10;
	private static final int rangeH = 10;
	private static final int rangeV = 5;

	public static final int SIZE = 1;

	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			TroughTE.this.markDirty();
			updateBlockState();
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack) {
			super.setStackInSlot(slot, stack);
		}
	};

	private void updateBlockState() {
		IBlockState iblockstate = getWorld().getBlockState(pos);
		int itemCount = itemStackHandler.getStackInSlot(0).getCount();
//		System.out.println("itemCount: " + itemCount);
		int value = (int) Math.ceil(itemCount / 16f);
//		System.out.println("Set to " + value);
		getWorld().setBlockState(pos, iblockstate.withProperty(TroughBlock.LEVEL, value), 2);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	public ItemStack getItemStack() {
		return itemStackHandler.getStackInSlot(0);
	}

	public void setItemStack(ItemStack stack) {
		itemStackHandler.setStackInSlot(0, stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", itemStackHandler.serializeNBT());
		return compound;
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
		}
		return super.getCapability(capability, facing);
	}

	public String getName() {
		return "container.trough.name";
	}

	@Nullable
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(this.getName());
	}

	@Override
	public void update() {
		if (getWorld().isRemote) {
			// Client
		} else {
			// Server
			counter++;
			if (counter >= maxCounter) {
				counter = 0;
				// Now update for real
				updateBlockState();

				ItemStack stack = itemStackHandler.getStackInSlot(0);
				if (stack == ItemStack.EMPTY) {
					return;
				}

				List<EntityAnimal> list = getWorld().getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(
						getPos().add(-rangeH, -rangeV, -rangeH), getPos().add(rangeH, rangeV, rangeH)));

				list = list.stream().filter(e -> !e.isInLove() && e.getGrowingAge() == 0 && e.isBreedingItem(stack))
						.collect(Collectors.toList());

				Map<Class<EntityAnimal>, List<EntityAnimal>> map = new HashMap<>();
				list.forEach(e -> {
					Class clazz = e.getClass();
					List<EntityAnimal> group = map.get(clazz);
					if (group == null) {
						group = new ArrayList<>();
						map.put(clazz, group);
					}
					group.add(e);
				});

				map.keySet().forEach(key -> {
					List<EntityAnimal> group = map.get(key);
					System.out.println(key.getSimpleName() + ": " + group.size());
				});

				map.keySet().forEach(key -> {
					List<EntityAnimal> group = map.get(key);
					final int count = group.size();
					if (count >= minAnimalCount && count <= maxAnimalCount) {

						for (int i = 0; i + 1 < count && itemStackHandler.getStackInSlot(0).getCount() >= 2; i += 2) {
							group.get(i).setInLove(null);
							group.get(i + 1).setInLove(null);

							itemStackHandler.extractItem(0, 2, false);
						}
					}
				});
			}
		}
	}
}

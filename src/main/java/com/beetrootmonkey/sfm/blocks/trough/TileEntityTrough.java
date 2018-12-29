package com.beetrootmonkey.sfm.blocks.trough;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityTrough extends TileEntity implements ITickable {
	
	private int counter = 0;
	private int maxCounter = 100;
	private int minAnimalCount = 2;
	private int maxAnimalCount = 10;
	
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return compound;
    }

	@Override
	public void update() {
		if (getWorld().isRemote) {
			// Client
		} else {
			// Server
			counter++;
			while (counter >= maxCounter) {
				// Now update for real
				List<EntityLivingBase> list = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPos().add(-10, -10, -10), getPos().add(10, 10, 10)));
				List<EntityAnimal> feedingList = new LinkedList<>();
				for(EntityLivingBase e : list) {
					if (e instanceof EntityAnimal) {
						EntityAnimal animal = (EntityAnimal) e;
						if (!animal.isInLove() && animal.getGrowingAge() == 0) {
							feedingList.add(animal);
						}
					}
			    }
				final int count = feedingList.size();
				if (count >= minAnimalCount && count <= maxAnimalCount) {
					if (count % 2 != 0) {
						feedingList.remove(getWorld().rand.nextInt(count));
					}
					
					for(EntityAnimal a : feedingList) {
						a.setInLove(null);
					}
				}
				
				System.out.println("Found " + count + " Animals");
				
				counter -= maxCounter;
			}
		}
	}
}

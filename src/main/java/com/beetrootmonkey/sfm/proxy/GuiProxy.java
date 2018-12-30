package com.beetrootmonkey.sfm.proxy;

import com.beetrootmonkey.sfm.blocks.nest.NestContainer;
import com.beetrootmonkey.sfm.blocks.nest.NestContainerGui;
import com.beetrootmonkey.sfm.blocks.nest.NestTE;
import com.beetrootmonkey.sfm.blocks.trough.TroughContainer;
import com.beetrootmonkey.sfm.blocks.trough.TroughContainerGui;
import com.beetrootmonkey.sfm.blocks.trough.TroughTE;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TroughTE) {
            return new TroughContainer(player.inventory, (TroughTE) te);
        } else if (te instanceof NestTE) {
            return new NestContainer(player.inventory, (NestTE) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TroughTE) {
        	TroughTE containerTileEntity = (TroughTE) te;
            return new TroughContainerGui(containerTileEntity, new TroughContainer(player.inventory, containerTileEntity));
        } else if (te instanceof NestTE) {
        	NestTE containerTileEntity = (NestTE) te;
            return new NestContainerGui(containerTileEntity, new NestContainer(player.inventory, containerTileEntity));
        }
        return null;
    }
}

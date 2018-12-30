package com.beetrootmonkey.sfm.blocks.nest;

import net.minecraftforge.items.ItemStackHandler;

public class NestTEItemStackHandler extends ItemStackHandler {
	public void markContentsAsChanged(int slot) {
		onContentsChanged(slot);
	}
}

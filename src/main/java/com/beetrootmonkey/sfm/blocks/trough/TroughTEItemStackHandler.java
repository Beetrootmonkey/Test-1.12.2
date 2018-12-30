package com.beetrootmonkey.sfm.blocks.trough;

import net.minecraftforge.items.ItemStackHandler;

public class TroughTEItemStackHandler extends ItemStackHandler {
	public void markContentsAsChanged(int slot) {
		onContentsChanged(slot);
	}
}

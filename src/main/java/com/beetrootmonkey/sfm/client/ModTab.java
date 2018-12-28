package com.beetrootmonkey.sfm.client;

import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModTab extends CreativeTabs {

	public ModTab() {
		super(Main.MOD_ID);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.DIAMOND);
	}
}


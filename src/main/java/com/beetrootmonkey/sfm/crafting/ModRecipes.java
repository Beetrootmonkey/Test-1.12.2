package com.beetrootmonkey.sfm.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.beetrootmonkey.sfm.items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

	private static String group = "sfm";

	private static IRecipe[] recipes = new IRecipe[]
	{
		new ShapelessRecipes(group, new ItemStack(ModItems.PITCHFORK), toIngredients(
			new ItemStack(Items.IRON_NUGGET),
			new ItemStack(Items.STICK))).setRegistryName("item1")
	};
	
	public static void registerSmeltingRecipes() {
		GameRegistry.addSmelting(ModItems.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.35f);
		GameRegistry.addSmelting(ModItems.GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 0.35f);
	}

	public static void register() {
		List<IRecipe> list = new ArrayList<>(Arrays.asList(recipes));
		list.stream().forEach(r -> ForgeRegistries.RECIPES.register(r));
		
		registerSmeltingRecipes();
	}

	private static NonNullList<Ingredient> toIngredients(Ingredient... ingredients) {
		NonNullList<Ingredient> list = NonNullList.create();
		list.addAll(Arrays.asList(ingredients));
		return list;
	}

	private static NonNullList<Ingredient> toIngredients(Item... ingredients) {
		Ingredient[] list = new Ingredient[ingredients.length];
		for (int i = 0; i < ingredients.length; i++) {
			list[i] = Ingredient.fromItems(ingredients[i]);
		}
		return toIngredients(list);
	}
	
	private static NonNullList<Ingredient> toIngredients(ItemStack... ingredients) {
		Ingredient[] list = new Ingredient[ingredients.length];
		for (int i = 0; i < ingredients.length; i++) {
			list[i] = Ingredient.fromStacks(ingredients[i]);
		}
		return toIngredients(list);
	}
}

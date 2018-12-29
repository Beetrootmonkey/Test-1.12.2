package com.beetrootmonkey.sfm.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.beetrootmonkey.sfm.main.Main;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeUtils {

	public static List<IRecipe> findRecipes(Object output) {
		List<IRecipe> recipesToReturn = new ArrayList<IRecipe>();
		if (!isItem(output)) {
			return recipesToReturn;
		}

		for (ResourceLocation key : CraftingManager.REGISTRY.getKeys()) {
			IRecipe recipe = CraftingManager.getRecipe(key);
			if (equals(output, recipe.getRecipeOutput())) {
				recipesToReturn.add(recipe);
			}
		}
		return recipesToReturn;
	}

	public static void removeRecipes(Object output, Object... input) {
		if (!isItem(output)) {
			return;
		}
		List<IRecipe> recipesToRemove = new ArrayList<IRecipe>();

		for (ResourceLocation key : CraftingManager.REGISTRY.getKeys()) {
			IRecipe recipe = CraftingManager.getRecipe(key);
			if (input.length == 0) {
				if (equals(output, recipe.getRecipeOutput())) {
					recipesToRemove.add(recipe);
				}
			} else {
				boolean validRecipe = equals(output, recipe.getRecipeOutput());
				if (validRecipe) {
					for (Object objIn : input) {
						if (!isIngredient(recipe, objIn)) {
							validRecipe = false;
							break;
						}
					}
					if (validRecipe) {
						recipesToRemove.add(recipe);
					}
				}
			}
		}
		
		// "Remove" (= replace) recipes
		IRecipe dummy = new DummyRecipe();
		for (ResourceLocation key : CraftingManager.REGISTRY.getKeys()) {
			CraftingManager.REGISTRY.putObject(key, dummy);
		}
		
		if (output instanceof String) {
			Main.LOG.info("Removed " + recipesToRemove.size() + " recipes with output " + (String) output + "!");
		} else {
			Main.LOG.info("Removed " + recipesToRemove.size() + " recipes with output "
					+ convertToStack(output).getUnlocalizedName() + "!");
		}

	}

	public static boolean isObjectInList(Object[] list, Object item) {
		return isObjectInList(Arrays.asList(list), item);
	}

	public static boolean isObjectInList(List<? extends Object> list, Object item) {
		for (Object stack : completeList(list)) {
			if (isItem(stack)) {
				if (equals(stack, item)) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<Object> completeList(List<? extends Object> list) {
		List<Object> listToReturn = new ArrayList<Object>();
		for (Object item : list) {
			if (isItem(item)) {
				listToReturn.add(item);
			} else if (item instanceof List<?>) {
				for (Object oreItem : (List<?>) item) {
					if (isItem(oreItem)) {
						listToReturn.add(oreItem);
					}
				}
			}
		}
		return listToReturn;
	}

	public static boolean isIngredient(IRecipe recipe, Object item) {
		List<ItemStack> input = new LinkedList<>();
		List<Ingredient> ingredients = null;
		if (recipe instanceof ShapedRecipes) {
			ingredients = ((ShapedRecipes) recipe).getIngredients();
		} else if (recipe instanceof ShapelessRecipes) {
			ingredients = ((ShapelessRecipes) recipe).getIngredients();
		} else if (recipe instanceof ShapedOreRecipe) {
			ingredients = ((ShapedOreRecipe) recipe).getIngredients();
		} else if (recipe instanceof ShapelessOreRecipe) {
			ingredients = ((ShapelessOreRecipe) recipe).getIngredients();
		} else {
			return false;
		}
		
		for(Ingredient i : ingredients) {
			input.addAll(Arrays.asList(i.getMatchingStacks()));
		}
		return isObjectInList(ingredients, item);
	}

	public static ItemStack convertToStack(Object object) {
		if (object instanceof Block) {
			return new ItemStack(Item.getItemFromBlock((Block) object), 0);
		} else if (object instanceof Item) {
			return new ItemStack((Item) object, 0);
		} else if (object instanceof ItemStack) {
			ItemStack oldStack = (ItemStack)object;
			ItemStack stack = new ItemStack(oldStack.getItem(), 0, oldStack.getMetadata());
			return stack;
		}
		return null;
	}

	public static boolean isItem(Object object) {
		return object instanceof String || object instanceof Item || object instanceof ItemStack
				|| object instanceof Block;
	}

	public static boolean equals(Object object1, Object object2) {
		if (!isItem(object1) || !isItem(object2)) {
			return false;
		}

		if (object1 instanceof String) {
			if (object2 instanceof String) {
				return isOreEqualToOre((String) object1, (String) object2);
			} else {
				return equals((String) object1, convertToStack(object2));
			}
		} else {
			if (object2 instanceof String) {
				return isItemStackEqualToOre(convertToStack(object1), (String) object2);
			} else {
				return equals(convertToStack(object1), convertToStack(object2));
			}
		}
	}

	public static boolean isItemStackEqualToOre(ItemStack stack, String ore) {
		for (ItemStack entry : OreDictionary.getOres((String) ore)) {
			ItemStack entryStack = new ItemStack(entry.getItem(), 0, entry.getMetadata());
			if (ItemStack.areItemStacksEqual(entryStack, stack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean equals(String ore, ItemStack stack) {
		return isItemStackEqualToOre(stack, ore);
	}

	public static boolean isOreEqualToOre(String ore1, String ore2) {
		return ore1.equals(ore2);
	}
}

package gory_moon.moarsigns.util;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class SignInitialization {

    public static void addWoodMaterial(HashMap<String, ItemStack> materials, ArrayList<ItemStack> loadedPlanks) {

        // Vanilla
        ItemStack vanillaStack = null;
        for (ItemStack planks: loadedPlanks) {
            if (planks.getUnlocalizedName().equals("tile.wood.oak")) {
                vanillaStack = planks.copy();
                break;
            }
        }

        if (vanillaStack != null) {
            for (int i = 0; i < 4; i++) {
                vanillaStack.setItemDamage(i);
                materials.put(vanillaStack.getUnlocalizedName(), vanillaStack.copy());
            }
        }

        //Modded
        if (Loader.isModLoaded("Natura")) {
            ItemStack stack = null;
            for (ItemStack planks: loadedPlanks) {
                if (planks.getUnlocalizedName().equals("eucalyptusNPlanks")) {
                    stack = planks.copy();
                    break;
                }
            }

            if (stack != null) {
                for (int i = 0; i < 14; i++) {
                    stack.setItemDamage(i);
                    materials.put(stack.getUnlocalizedName(), stack.copy());
                }
            }
        }

        if (Loader.isModLoaded("Forestry")) {
            ItemStack stack1 = null;
            ItemStack stack2 = null;

            for (ItemStack planks: loadedPlanks) {
                if (stack1 == null && planks.getUnlocalizedName().equals("tile.for.planks.32767")) { stack1 = planks.copy(); }
                if (stack2 == null && planks.getUnlocalizedName().equals("tile.for.planks2.32767")) { stack2 = planks.copy(); }
                if (stack1 != null && stack2 != null) break;
            }

            if (stack1 != null) {
                for (int i = 0; i < 16; i++) {
                    stack1.setItemDamage(i);
                    materials.put(stack1.getUnlocalizedName(), stack1.copy());
                }
            }

            if (stack2 != null) {
                for (int i = 0; i < 8; i++) {
                    stack2.setItemDamage(i);
                    materials.put(stack2.getUnlocalizedName(), stack2.copy());
                }
            }
        }

        if (Loader.isModLoaded("BiomesOPlenty")) {

            ItemStack stack = null;
            for (ItemStack planks: loadedPlanks) {
                if (planks.getUnlocalizedName().equals("tile.bop.planks.acaciaPlank")) {
                    stack = planks.copy();
                    break;
                }
            }

            if (stack != null) {
                for (int i = 0; i < 14; i++) {
                    stack.setItemDamage(i);
                    materials.put(stack.getUnlocalizedName(), stack.copy());
                }
            }
        }
    }

    public static void addMetalMaterial(HashMap<String, ItemStack> materials, ArrayList<ItemStack> ingots) {

        //Vanilla
        ItemStack iron = new ItemStack(Item.ingotIron);
        ItemStack gold = new ItemStack(Item.ingotGold);
        ItemStack diamond = new ItemStack(Item.diamond);
        ItemStack emerald = new ItemStack(Item.emerald);
        materials.put(iron.getUnlocalizedName(), iron);
        materials.put(gold.getUnlocalizedName(), gold);
        materials.put(diamond.getUnlocalizedName(), diamond);
        materials.put(emerald.getUnlocalizedName(), emerald);

        //Modded
        for (ItemStack stack: ingots) {
            materials.put(stack.getUnlocalizedName(), stack);
        }
    }
}

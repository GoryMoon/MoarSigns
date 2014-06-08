package gory_moon.moarsigns;

import cpw.mods.fml.common.Loader;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.SignRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class SignInitialization {

    public static void addWoodMaterial(ArrayList<ItemStack> loadedPlanks) {

        // Vanilla
        ItemStack vanillaStack = null;
        for (ItemStack planks: loadedPlanks) {
            if (planks.getUnlocalizedName().equals("tile.wood.oak")) {
                vanillaStack = planks.copy();
                break;
            }
        }

        if (vanillaStack != null) {
            SignRegistry.register("oak_sign", null, "oak", "", false, new ItemStack(vanillaStack.getItem(), 1, 0));
            SignRegistry.register("spruce_sign", null, "spruce", "", false, new ItemStack(vanillaStack.getItem(), 1, 1));
            SignRegistry.register("birch_sign", null, "birch", "", false, new ItemStack(vanillaStack.getItem(), 1, 2));
            SignRegistry.register("jungle_sign", null, "jungle", "", false, new ItemStack(vanillaStack.getItem(), 1, 3));
            SignRegistry.register("acacia_sign", null, "acacia", "", false, new ItemStack(vanillaStack.getItem(), 1, 4));
            SignRegistry.register("big_oak_sign", null, "big_oak", "", false, new ItemStack(vanillaStack.getItem(), 1, 5));
        }


        //Modded
        if (Loader.isModLoaded("Natura")) {
            ItemStack stack = null;
            for (ItemStack planks: loadedPlanks) {
                if (planks.getUnlocalizedName().equals("block.eucalyptus.NPlanks")) {
                    stack = planks.copy();
                    break;
                }
            }

            if (stack != null) {
                SignRegistry.register("eucalyptus_sign", null, "eucalyptus", "natura/", false, new ItemStack(stack.getItem(), 1, 0));
                SignRegistry.register("sakura_sign", null, "sakura", "natura/", false, new ItemStack(stack.getItem(), 1, 1));
                SignRegistry.register("ghostwood_sign", null, "ghostwood", "natura/", false, new ItemStack(stack.getItem(), 1, 2));
                SignRegistry.register("redwood_sign", null, "redwood", "natura/", false, new ItemStack(stack.getItem(), 1, 3));
                SignRegistry.register("bloodwood_sign", null, "bloodwood", "natura/", false, new ItemStack(stack.getItem(), 1, 4));
                SignRegistry.register("hopseed_sign", null, "hopseed", "natura/", false, new ItemStack(stack.getItem(), 1, 5));
                SignRegistry.register("maple_sign", null, "maple", "natura/", false, new ItemStack(stack.getItem(), 1, 6));
                SignRegistry.register("silverbell_sign", null, "silverbell", "natura/", false, new ItemStack(stack.getItem(), 1, 7));
                SignRegistry.register("purpleheart_sign", null, "purpleheart", "natura/", false, new ItemStack(stack.getItem(), 1, 8));
                SignRegistry.register("tiger_sign", null, "tiger", "natura/", false, new ItemStack(stack.getItem(), 1, 9));
                SignRegistry.register("willow_sign", null, "willow", "natura/", false, new ItemStack(stack.getItem(), 1, 10));
                SignRegistry.register("darkwood_sign", null, "darkwood", "natura/", false, new ItemStack(stack.getItem(), 1, 11));
                SignRegistry.register("fusewood_sign", null, "fusewood", "natura/", false, new ItemStack(stack.getItem(), 1, 12));
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
                SignRegistry.register("larch_sign", null, "larch", "for/", false, new ItemStack(stack1.getItem(), 1, 0));
                SignRegistry.register("teak_sign", null, "teak", "for/", false, new ItemStack(stack1.getItem(), 1, 1));
                SignRegistry.register("acacia_sign", null, "acacia", "for/", false, new ItemStack(stack1.getItem(), 1, 2));
                SignRegistry.register("lime_sign", null, "lime", "for/", false, new ItemStack(stack1.getItem(), 1, 3));
                SignRegistry.register("chestnut_sign", null, "chestnut", "for/", false, new ItemStack(stack1.getItem(), 1, 4));
                SignRegistry.register("wenge_sign", null, "wenge", "for/", false, new ItemStack(stack1.getItem(), 1, 5));
                SignRegistry.register("baobab_sign", null, "baobab", "for/", false, new ItemStack(stack1.getItem(), 1, 6));
                SignRegistry.register("sequoia_sign", null, "sequoia", "for/", false, new ItemStack(stack1.getItem(), 1, 7));
                SignRegistry.register("kapok_sign", null, "kapok", "for/", false, new ItemStack(stack1.getItem(), 1, 8));
                SignRegistry.register("ebony_sign", null, "ebony", "for/", false, new ItemStack(stack1.getItem(), 1, 9));
                SignRegistry.register("mahogany_sign", null, "mahogany", "for/", false, new ItemStack(stack1.getItem(), 1, 10));
                SignRegistry.register("balsa_sign", null, "balsa", "for/", false, new ItemStack(stack1.getItem(), 1, 11));
                SignRegistry.register("willow_sign", null, "willow", "for/", false, new ItemStack(stack1.getItem(), 1, 12));
                SignRegistry.register("walnut_sign", null, "walnut", "for/", false, new ItemStack(stack1.getItem(), 1, 13));
                SignRegistry.register("greenheart_sign", null, "greenheart", "for/", false, new ItemStack(stack1.getItem(), 1, 14));
                SignRegistry.register("cherry_sign", null, "cherry", "for/", false, new ItemStack(stack1.getItem(), 1, 15));
            }

            if (stack2 != null) {
                SignRegistry.register("mahoe_sign", null, "mahoe", "for/", false, new ItemStack(stack2.getItem(), 1, 0));
                SignRegistry.register("poplar_sign", null, "poplar", "for/", false, new ItemStack(stack2.getItem(), 1, 1));
                SignRegistry.register("palm_sign", null, "palm", "for/", false, new ItemStack(stack2.getItem(), 1, 2));
                SignRegistry.register("papaya_sign", null, "papaya", "for/", false, new ItemStack(stack2.getItem(), 1, 3));
                SignRegistry.register("pine_sign", null, "pine", "for/", false, new ItemStack(stack2.getItem(), 1, 4));
                SignRegistry.register("plum_sign", null, "plum", "for/", false, new ItemStack(stack2.getItem(), 1, 5));
                SignRegistry.register("maple_sign", null, "maple", "for/", false, new ItemStack(stack2.getItem(), 1, 6));
                SignRegistry.register("citrus_sign", null, "citrus", "for/", false, new ItemStack(stack2.getItem(), 1, 7));
            }
        }

        if (Loader.isModLoaded("BiomesOPlenty")) {
            ItemStack stack = null;
            for (ItemStack planks: loadedPlanks) {
                if (planks.getUnlocalizedName().equals("tile.planks.sacredoakPlank")) {
                    stack = planks.copy();
                    break;
                }
            }

            if (stack != null) {
                SignRegistry.register("sacredoak_sign", null, "sacredoak", "bop/", false, new ItemStack(stack.getItem(), 1, 0));
                SignRegistry.register("cherry_sign", null, "cherry", "bop/", false, new ItemStack(stack.getItem(), 1, 1));
                SignRegistry.register("dark_sign", null, "dark", "bop/", false, new ItemStack(stack.getItem(), 1, 2));
                SignRegistry.register("fir_sign", null, "fir", "bop/", false, new ItemStack(stack.getItem(), 1, 3));
                SignRegistry.register("holy_sign", null, "holy", "bop/", false, new ItemStack(stack.getItem(), 1, 4));
                SignRegistry.register("magic_sign", null, "magic", "bop/", false, new ItemStack(stack.getItem(), 1, 5));
                SignRegistry.register("mangrove_sign", null, "mangrove", "bop/", false, new ItemStack(stack.getItem(), 1, 6));
                SignRegistry.register("palm_sign", null, "palm", "bop/", false, new ItemStack(stack.getItem(), 1, 7));
                SignRegistry.register("redwood_sign", null, "redwood", "bop/", false, new ItemStack(stack.getItem(), 1, 8));
                SignRegistry.register("willow_sign", null, "willow", "bop/", false, new ItemStack(stack.getItem(), 1, 9));
                //TODO see and maybe add the missing plank
                //SignRegistry.register("", null, "", "bop/", false, new ItemStack(stack.getItem(), 1, 10));
                SignRegistry.register("pine_sign", null, "pine", "bop/", false, new ItemStack(stack.getItem(), 1, 11));
                SignRegistry.register("hellbark_sign", null, "hellbark", "bop/", false, new ItemStack(stack.getItem(), 1, 12));
                SignRegistry.register("jacaranda_sign", null, "jacaranda", "bop/", false, new ItemStack(stack.getItem(), 1, 13));
                SignRegistry.register("mahogany_sign", null, "mahogany", "bop/", false, new ItemStack(stack.getItem(), 1, 14));
            }
        }
    }

    public static void addMetalMaterial(ArrayList<ItemStack> ingots) {

        //Vanilla

        ItemStack iron = new ItemStack(Items.iron_ingot);
        ItemStack gold = new ItemStack(Items.gold_ingot);
        ItemStack diamond = new ItemStack(Items.diamond);
        ItemStack emerald = new ItemStack(Items.emerald);

        SignRegistry.register("iron_sign", null, "iron", "", false, iron).setMetal(true);
        SignRegistry.register("gold_sign", null, "gold", "", true, gold).setMetal(true);
        SignRegistry.register("diamond_sign", null, "diamond", "", false, diamond).setMetal(true);
        SignRegistry.register("emerald_sign", null, "emerald", "", false, emerald).setMetal(true);

        //Modded
        //TODO add the modded metals

    }
}

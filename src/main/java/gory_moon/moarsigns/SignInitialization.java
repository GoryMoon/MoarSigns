package gory_moon.moarsigns;

import cpw.mods.fml.common.Loader;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.signproperties.DiamondProperty;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SignInitialization {

    private static final String NATURA_TAG = "Natura";
    private static final String FORESTRY_TAG = "Forestry";
    private static final String BOP_TAG = "BoP";
    private static final String IC2_TAG = "ic2";
    private static final String TCONSTRUCT_TAG = "tconstruct";
    private static final String FACTORIZATION_TAG = "factorization";

    public static void addWoodMaterial(ArrayList<ItemStack> loadedPlanks) {

        // Vanilla
        Item vanillaItem = null;
        for (ItemStack planks : loadedPlanks) {
            if (planks.getUnlocalizedName().equals("tile.wood.oak")) {
                vanillaItem = planks.copy().getItem();
                break;
            }
        }

        SignRegistry.register("oak_sign", null, "oak", "", false, new ItemStack(vanillaItem, 1, 0), ModInfo.ID);
        SignRegistry.register("spruce_sign", null, "spruce", "", false, new ItemStack(vanillaItem, 1, 1), ModInfo.ID);
        SignRegistry.register("birch_sign", null, "birch", "", false, new ItemStack(vanillaItem, 1, 2), ModInfo.ID);
        SignRegistry.register("jungle_sign", null, "jungle", "", false, new ItemStack(vanillaItem, 1, 3), ModInfo.ID);
        SignRegistry.register("acacia_sign", null, "acacia", "", false, new ItemStack(vanillaItem, 1, 4), ModInfo.ID);
        SignRegistry.register("big_oak_sign", null, "big_oak", "", false, new ItemStack(vanillaItem, 1, 5), ModInfo.ID);


        //Modded
        Item naturaItem = null;
        Item bopItem = null;
        Item forestryItem1 = null;
        Item forestryItem2 = null;


        for (ItemStack planks : loadedPlanks) {
            if (planks.getUnlocalizedName().equals("block.eucalyptus.NPlanks")) {
                naturaItem = planks.copy().getItem();
                break;
            }
        }

        SignRegistry.register("eucalyptus_sign", null, "eucalyptus", "natura/", false, new ItemStack(naturaItem, 1, 0), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("sakura_sign", null, "sakura", "natura/", false, new ItemStack(naturaItem, 1, 1), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("ghostwood_sign", null, "ghostwood", "natura/", false, new ItemStack(naturaItem, 1, 2), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("redwood_sign", null, "redwood", "natura/", false, new ItemStack(naturaItem, 1, 3), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("bloodwood_sign", null, "bloodwood", "natura/", false, new ItemStack(naturaItem, 1, 4), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("hopseed_sign", null, "hopseed", "natura/", false, new ItemStack(naturaItem, 1, 5), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("maple_sign", null, "maple", "natura/", false, new ItemStack(naturaItem, 1, 6), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("silverbell_sign", null, "silverbell", "natura/", false, new ItemStack(naturaItem, 1, 7), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("purpleheart_sign", null, "purpleheart", "natura/", false, new ItemStack(naturaItem, 1, 8), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("tiger_sign", null, "tiger", "natura/", false, new ItemStack(naturaItem, 1, 9), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("willow_sign", null, "willow", "natura/", false, new ItemStack(naturaItem, 1, 10), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("darkwood_sign", null, "darkwood", "natura/", false, new ItemStack(naturaItem, 1, 11), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("fusewood_sign", null, "fusewood", "natura/", false, new ItemStack(naturaItem, 1, 12), ModInfo.ID, NATURA_TAG);

        for (ItemStack planks : loadedPlanks) {
            if (forestryItem1 == null && planks.getItem().getUnlocalizedName().equals("tile.for.planks")) {
                forestryItem1 = planks.copy().getItem();
            }
            if (forestryItem2 == null && planks.getItem().getUnlocalizedName().equals("tile.for.planks2")) {
                forestryItem2 = planks.copy().getItem();
            }
            if (forestryItem1 != null && forestryItem2 != null) break;
        }

        SignRegistry.register("larch_sign", null, "larch", "for/", false, new ItemStack(forestryItem1, 1, 0), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("teak_sign", null, "teak", "for/", false, new ItemStack(forestryItem1, 1, 1), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("acacia_sign", null, "acacia", "for/", false, new ItemStack(forestryItem1, 1, 2), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("lime_sign", null, "lime", "for/", false, new ItemStack(forestryItem1, 1, 3), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("chestnut_sign", null, "chestnut", "for/", false, new ItemStack(forestryItem1, 1, 4), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("wenge_sign", null, "wenge", "for/", false, new ItemStack(forestryItem1, 1, 5), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("baobab_sign", null, "baobab", "for/", false, new ItemStack(forestryItem1, 1, 6), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("sequoia_sign", null, "sequoia", "for/", false, new ItemStack(forestryItem1, 1, 7), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("kapok_sign", null, "kapok", "for/", false, new ItemStack(forestryItem1, 1, 8), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("ebony_sign", null, "ebony", "for/", false, new ItemStack(forestryItem1, 1, 9), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("mahogany_sign", null, "mahogany", "for/", false, new ItemStack(forestryItem1, 1, 10), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("balsa_sign", null, "balsa", "for/", false, new ItemStack(forestryItem1, 1, 11), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("willow_sign", null, "willow", "for/", false, new ItemStack(forestryItem1, 1, 12), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("walnut_sign", null, "walnut", "for/", false, new ItemStack(forestryItem1, 1, 13), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("greenheart_sign", null, "greenheart", "for/", false, new ItemStack(forestryItem1, 1, 14), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("cherry_sign", null, "cherry", "for/", false, new ItemStack(forestryItem1, 1, 15), ModInfo.ID, FORESTRY_TAG);

        SignRegistry.register("mahoe_sign", null, "mahoe", "for/", false, new ItemStack(forestryItem2, 1, 0), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("poplar_sign", null, "poplar", "for/", false, new ItemStack(forestryItem2, 1, 1), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("palm_sign", null, "palm", "for/", false, new ItemStack(forestryItem2, 1, 2), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("papaya_sign", null, "papaya", "for/", false, new ItemStack(forestryItem2, 1, 3), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("pine_sign", null, "pine", "for/", false, new ItemStack(forestryItem2, 1, 4), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("plum_sign", null, "plum", "for/", false, new ItemStack(forestryItem2, 1, 5), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("maple_sign", null, "maple", "for/", false, new ItemStack(forestryItem2, 1, 6), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("citrus_sign", null, "citrus", "for/", false, new ItemStack(forestryItem2, 1, 7), ModInfo.ID, FORESTRY_TAG);

        for (ItemStack planks : loadedPlanks) {
            if (planks.getUnlocalizedName().equals("tile.planks.sacredoakPlank")) {
                bopItem = planks.copy().getItem();
                break;
            }
        }

        SignRegistry.register("sacred_oak_sign", null, "sacred_oak", "bop/", false, new ItemStack(bopItem, 1, 0), ModInfo.ID, BOP_TAG);
        SignRegistry.register("cherry_sign", null, "cherry", "bop/", false, new ItemStack(bopItem, 1, 1), ModInfo.ID, BOP_TAG);
        SignRegistry.register("dark_sign", null, "dark", "bop/", false, new ItemStack(bopItem, 1, 2), ModInfo.ID, BOP_TAG);
        SignRegistry.register("fir_sign", null, "fir", "bop/", false, new ItemStack(bopItem, 1, 3), ModInfo.ID, BOP_TAG);
        SignRegistry.register("holy_sign", null, "holy", "bop/", false, new ItemStack(bopItem, 1, 4), ModInfo.ID, BOP_TAG);
        SignRegistry.register("magic_sign", null, "magic", "bop/", false, new ItemStack(bopItem, 1, 5), ModInfo.ID, BOP_TAG);
        SignRegistry.register("mangrove_sign", null, "mangrove", "bop/", false, new ItemStack(bopItem, 1, 6), ModInfo.ID, BOP_TAG);
        SignRegistry.register("palm_sign", null, "palm", "bop/", false, new ItemStack(bopItem, 1, 7), ModInfo.ID, BOP_TAG);
        SignRegistry.register("redwood_sign", null, "redwood", "bop/", false, new ItemStack(bopItem, 1, 8), ModInfo.ID, BOP_TAG);
        SignRegistry.register("willow_sign", null, "willow", "bop/", false, new ItemStack(bopItem, 1, 9), ModInfo.ID, BOP_TAG);
        //TODO see and maybe add the missing plank
        //SignRegistry.register("", null, "", "bop/", false, new ItemStack(bopItem, 1, 10), ModInfo.ID, BOP_TAG);
        SignRegistry.register("pine_sign", null, "pine", "bop/", false, new ItemStack(bopItem, 1, 11), ModInfo.ID, BOP_TAG);
        SignRegistry.register("hellbark_sign", null, "hellbark", "bop/", false, new ItemStack(bopItem, 1, 12), ModInfo.ID, BOP_TAG);
        SignRegistry.register("jacaranda_sign", null, "jacaranda", "bop/", false, new ItemStack(bopItem, 1, 13), ModInfo.ID, BOP_TAG);
        SignRegistry.register("mahogany_sign", null, "mahogany", "bop/", false, new ItemStack(bopItem, 1, 14), ModInfo.ID, BOP_TAG);


        if (Loader.isModLoaded("Natura")) {
            SignRegistry.activateTag(NATURA_TAG);
        }

        if (Loader.isModLoaded("Forestry")) {
            SignRegistry.activateTag(FORESTRY_TAG);
        }

        if (Loader.isModLoaded("BiomesOPlenty")) {
            SignRegistry.activateTag(BOP_TAG);
        }
    }


    public static void addMetalMaterial(ArrayList<ItemStack> ingots) {

        //Vanilla

        ItemStack iron = new ItemStack(Items.iron_ingot);
        ItemStack gold = new ItemStack(Items.gold_ingot);
        ItemStack diamond = new ItemStack(Items.diamond);
        ItemStack emerald = new ItemStack(Items.emerald);

        SignRegistry.register("iron_sign", null, "iron", "", false, iron, ModInfo.ID).setMetal(true);
        SignRegistry.register("gold_sign", null, "gold", "", true, gold, ModInfo.ID).setMetal(true);
        SignRegistry.register("diamond_sign", new DiamondProperty(), "diamond", "", false, diamond, ModInfo.ID).setMetal(true);
        SignRegistry.register("emerald_sign", null, "emerald", "", false, emerald, ModInfo.ID).setMetal(true);


        //Modded
        Item ic2Item = null;
        Item tconstructItem = null;
        Item factorizationSilverItem = null;
        Item factorizationLeadItem = null;


        for (ItemStack stacks : ingots) {
            if (stacks.getUnlocalizedName().equals("ic2.itemIngotCopper")) {
                ic2Item = stacks.copy().getItem();
                break;
            }
        }

        SignRegistry.register("copper_sign", null, "copper", "ic2/", false, new ItemStack(ic2Item, 1, 0), ModInfo.ID, IC2_TAG).setMetal(true);
        SignRegistry.register("tin_sign", null, "tin", "ic2/", false, new ItemStack(ic2Item, 1, 1), ModInfo.ID, IC2_TAG).setMetal(true);
        SignRegistry.register("bronze_sign", null, "bronze", "ic2/", false, new ItemStack(ic2Item, 1, 2), ModInfo.ID, IC2_TAG).setMetal(true);
        //TODO add lead sign textures
        //SignRegistry.register("lead_sign", null, "lead", "ic2/", false, new ItemStack(ic2Item, 1, 5)).setMetal(true);

        for (ItemStack stacks : ingots) {
            if (stacks.getUnlocalizedName().equals("item.tconstruct.Materials.CopperIngot")) {
                tconstructItem = stacks.copy().getItem();
                break;
            }
        }

        SignRegistry.register("copper_sign", null, "copper", "tconstruct/", true, new ItemStack(tconstructItem, 1, 9), ModInfo.ID, TCONSTRUCT_TAG).setMetal(true);
        SignRegistry.register("tin_sign", null, "tin", "tconstruct/", true, new ItemStack(tconstructItem, 1, 10), ModInfo.ID, TCONSTRUCT_TAG).setMetal(true);
        SignRegistry.register("bronze_sign", null, "bronze", "tconstruct/", true, new ItemStack(tconstructItem, 1, 13), ModInfo.ID, TCONSTRUCT_TAG).setMetal(true);
        SignRegistry.register("steel_sign", null, "steel", "tconstruct/", true, new ItemStack(tconstructItem, 1, 16), ModInfo.ID, TCONSTRUCT_TAG).setMetal(true);

        for (ItemStack stacks : ingots) {
            if (stacks.getUnlocalizedName().equals("item.factorization:silver_ingot")) {
                factorizationSilverItem = stacks.copy().getItem();
            }
            if (stacks.getUnlocalizedName().equals("item.factorization:lead_ingot")) {
                factorizationLeadItem = stacks.copy().getItem();
            }
            if (factorizationSilverItem != null && factorizationLeadItem != null) break;
        }

        SignRegistry.register("silver_sign", null, "silver", "factorization/", true, new ItemStack(factorizationSilverItem, 1, 0), ModInfo.ID, FACTORIZATION_TAG).setMetal(true);
        SignRegistry.register("lead_sign", null, "lead", "factorization/", true, new ItemStack(factorizationLeadItem, 1, 0), ModInfo.ID, FACTORIZATION_TAG).setMetal(true);


        //TODO add thermal expansion when updated


        if (Loader.isModLoaded("IC2")) {
            SignRegistry.activateTag(IC2_TAG);
        }

        if (Loader.isModLoaded("TConstruct")) {
            SignRegistry.activateTag(TCONSTRUCT_TAG);
        }

        if (Loader.isModLoaded("factorization")) {
            SignRegistry.activateTag(FACTORIZATION_TAG);
        }

    }
}

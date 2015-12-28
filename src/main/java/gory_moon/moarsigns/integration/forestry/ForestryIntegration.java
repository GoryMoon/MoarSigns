package gory_moon.moarsigns.integration.forestry;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ForestryIntegration implements ISignRegistration {

    private static final String FORESTRY_TAG = "Forestry";
    private Item forestryItem1 = null;
    private Item forestryItem2 = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {
        for (ItemStack plank : planks) {
            if (forestryItem1 == null && plank.getItem().getUnlocalizedName().equals("tile.for.planks")) {
                forestryItem1 = plank.copy().getItem();
            }
            if (forestryItem2 == null && plank.getItem().getUnlocalizedName().equals("tile.for.planks2")) {
                forestryItem2 = plank.copy().getItem();
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

    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> ingots) {

    }

    @Override
    public String getActivateTag() {
        return FORESTRY_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(FORESTRY_TAG);
    }
}

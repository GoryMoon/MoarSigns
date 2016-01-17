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
    private static final String PATH = "for/";
    private Item item = null;
    private Item itemIngot1 = null;
    private Item itemIngot2 = null;
    private Item itemIngot3 = null;
    private Item blockMetal = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {
        for (ItemStack plank : planks) {
            if (item == null && plank.getItem().getUnlocalizedName().equals("tile.for.planks")) {
                item = plank.copy().getItem();
                break;
            }
        }

        SignRegistry.register("larch_sign", null, "larch", PATH, false, new ItemStack(item, 1, 0), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("teak_sign", null, "teak", PATH, false, new ItemStack(item, 1, 1), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("acacia_sign", null, "acacia", PATH, false, new ItemStack(item, 1, 2), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("lime_sign", null, "lime", PATH, false, new ItemStack(item, 1, 3), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("chestnut_sign", null, "chestnut", PATH, false, new ItemStack(item, 1, 4), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("wenge_sign", null, "wenge", PATH, false, new ItemStack(item, 1, 5), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("baobab_sign", null, "baobab", PATH, false, new ItemStack(item, 1, 6), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("sequoia_sign", null, "sequoia", PATH, false, new ItemStack(item, 1, 7), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("kapok_sign", null, "kapok", PATH, false, new ItemStack(item, 1, 8), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("ebony_sign", null, "ebony", PATH, false, new ItemStack(item, 1, 9), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("mahogany_sign", null, "mahogany", PATH, false, new ItemStack(item, 1, 10), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("balsa_sign", null, "balsa", PATH, false, new ItemStack(item, 1, 11), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("willow_sign", null, "willow", PATH, false, new ItemStack(item, 1, 12), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("walnut_sign", null, "walnut", PATH, false, new ItemStack(item, 1, 13), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("greenheart_sign", null, "greenheart", PATH, false, new ItemStack(item, 1, 14), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("cherry_sign", null, "cherry", PATH, false, new ItemStack(item, 1, 15), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("mahoe_sign", null, "mahoe", PATH, false, new ItemStack(item, 1, 16), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("poplar_sign", null, "poplar", PATH, false, new ItemStack(item, 1, 17), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("palm_sign", null, "palm", PATH, false, new ItemStack(item, 1, 18), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("papaya_sign", null, "papaya", PATH, false, new ItemStack(item, 1, 19), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("pine_sign", null, "pine", PATH, false, new ItemStack(item, 1, 20), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("plum_sign", null, "plum", PATH, false, new ItemStack(item, 1, 21), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("maple_sign", null, "maple", PATH, false, new ItemStack(item, 1, 22), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("citrus_sign", null, "citrus", PATH, false, new ItemStack(item, 1, 23), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("giantsequoia_sign", null, "giant_sequoia", PATH, false, new ItemStack(item, 1, 24), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("ipe_sign", null, "ipe", PATH, false, new ItemStack(item, 1, 25), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("padauk_sign", null, "padauk", PATH, false, new ItemStack(item, 1, 26), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("cocobolo_sign", null, "cocobolo", PATH, false, new ItemStack(item, 1, 27), ModInfo.ID, FORESTRY_TAG);
        SignRegistry.register("zebrawood_sign", null, "zebrawood", PATH, false, new ItemStack(item, 1, 28), ModInfo.ID, FORESTRY_TAG);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("item.for.ingotTin") && itemIngot1 == null) {
                itemIngot1 = stack.copy().getItem();
            }

            if (stack.getUnlocalizedName().equals("item.for.ingotBronze") && itemIngot2 == null) {
                itemIngot2 = stack.copy().getItem();
            }

            if (stack.getUnlocalizedName().equals("item.for.ingotCopper") && itemIngot3 == null) {
                itemIngot3 = stack.copy().getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.for.resourceStorage.1") && blockMetal == null) {
                blockMetal = stack.copy().getItem();
            }

            if (itemIngot1 != null && itemIngot2 != null && itemIngot3 != null && blockMetal != null) break;
        }

        SignRegistry.register("tin_sign", null, "tin", PATH, false, new ItemStack(itemIngot1), new ItemStack(blockMetal, 1, 2), ModInfo.ID, FORESTRY_TAG).setMetal();
        SignRegistry.register("bronze_sign", null, "bronze", PATH, false, new ItemStack(itemIngot2), new ItemStack(blockMetal, 1, 3), ModInfo.ID, FORESTRY_TAG).setMetal();
        SignRegistry.register("copper_sign", null, "copper", PATH, false, new ItemStack(itemIngot3), new ItemStack(blockMetal, 1, 1), ModInfo.ID, FORESTRY_TAG).setMetal();
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

package gory_moon.moarsigns.integration.forestry;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ForestryIntegration implements ISignRegistration {

    private static final String FORESTRY_TAG = "forestry";
    private static final String FORESTRY_NAME = "Forestry for Minecraft";
    private static final String PATH = "for/";
    private Item item = null;
    private Item item2 = null;
    private Item itemIngot1 = null;
    private Item itemIngot2 = null;
    private Item itemIngot3 = null;
    private Item blockMetal = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        for (ItemStack plank : planks) {
            if (item == null && plank.getItem().getUnlocalizedName().equals("tile.for.planks.0")) {
                item = plank.copy().getItem();
            }

            if (item2 == null && plank.getItem().getUnlocalizedName().equals("tile.for.planks.1")) {
                item2 = plank.copy().getItem();
            }

            if (item != null && item2 != null)
                break;
        }

        registerWood("larch_sign",          null, "larch",          new ItemStack(item, 1, 0));
        registerWood("teak_sign",           null, "teak",           new ItemStack(item, 1, 1));
        registerWood("acacia_sign",         null, "acacia",         new ItemStack(item, 1, 2));
        registerWood("lime_sign",           null, "lime",           new ItemStack(item, 1, 3));
        registerWood("chestnut_sign",       null, "chestnut",       new ItemStack(item, 1, 4));
        registerWood("wenge_sign",          null, "wenge",          new ItemStack(item, 1, 5));
        registerWood("baobab_sign",         null, "baobab",         new ItemStack(item, 1, 6));
        registerWood("sequoia_sign",        null, "sequoia",        new ItemStack(item, 1, 7));
        registerWood("kapok_sign",          null, "kapok",          new ItemStack(item, 1, 8));
        registerWood("ebony_sign",          null, "ebony",          new ItemStack(item, 1, 9));
        registerWood("mahogany_sign",       null, "mahogany",       new ItemStack(item, 1, 10));
        registerWood("balsa_sign",          null, "balsa",          new ItemStack(item, 1, 11));
        registerWood("willow_sign",         null, "willow",         new ItemStack(item, 1, 12));
        registerWood("walnut_sign",         null, "walnut",         new ItemStack(item, 1, 13));
        registerWood("greenheart_sign",     null, "greenheart",     new ItemStack(item, 1, 14));
        registerWood("cherry_sign",         null, "cherry",         new ItemStack(item, 1, 15));
        registerWood("mahoe_sign",          null, "mahoe",          new ItemStack(item2, 1, 0));
        registerWood("poplar_sign",         null, "poplar",         new ItemStack(item2, 1, 1));
        registerWood("palm_sign",           null, "palm",           new ItemStack(item2, 1, 2));
        registerWood("papaya_sign",         null, "papaya",         new ItemStack(item2, 1, 3));
        registerWood("pine_sign",           null, "pine",           new ItemStack(item2, 1, 4));
        registerWood("plum_sign",           null, "plum",           new ItemStack(item2, 1, 5));
        registerWood("maple_sign",          null, "maple",          new ItemStack(item2, 1, 6));
        registerWood("citrus_sign",         null, "citrus",         new ItemStack(item2, 1, 7));
        registerWood("giantsequoia_sign",   null, "giant_sequoia",  new ItemStack(item2, 1, 8));
        registerWood("ipe_sign",            null, "ipe",            new ItemStack(item2, 1, 9));
        registerWood("padauk_sign",         null, "padauk",         new ItemStack(item2, 1, 10));
        registerWood("cocobolo_sign",       null, "cocobolo",       new ItemStack(item2, 1, 11));
        registerWood("zebrawood_sign",      null, "zebrawood",      new ItemStack(item2, 1, 12));
    }

    private void registerWood(String name, SignSpecialProperty property, String materialName, ItemStack material) throws IntegrationException {
        SignRegistry.register(name, property, materialName, PATH, false, material, ModInfo.ID, FORESTRY_TAG);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("item.for.ingotTin") && itemIngot1 == null) {
                itemIngot1 = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("item.for.ingotBronze") && itemIngot2 == null) {
                itemIngot2 = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("item.for.ingotCopper") && itemIngot3 == null) {
                itemIngot3 = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.for.resourceStorage.1") && blockMetal == null) {
                blockMetal = stack.getItem();
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

    @Override
    public String getModName() {
        return FORESTRY_NAME;
    }
}

package gory_moon.moarsigns.integration.techreborn;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TechRebornIntegration implements ISignRegistration {

    private static final String TECHREBORN_TAG = "techreborn";
    private static final String TECHREBORN_NAME = "TechReborn";
    private static final String PATH = "techreborn/";
    private Item item = null;
    private Item blockItem1 = null;
    private Item blockItem2 = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("item.techreborn.ingot.aluminum") && item == null) {
                item = stack.getItem();
            } else if (stack.getUnlocalizedName().equals("tile.techreborn.storage.silver") && blockItem1 == null) {
                blockItem1 = stack.getItem();
            } else if (stack.getUnlocalizedName().equals("tile.techreborn.storage2.tungstensteel") && blockItem2 == null)

                if (item != null && blockItem1 != null && blockItem2 != null)
                    break;
        }

        registerMetal("aluminium_sign",     null, "aluminium",      new ItemStack(item, 1, 0),  new ItemStack(blockItem1, 1, 1));
        registerMetal("brass_sign",         null, "brass",          new ItemStack(item, 1, 1),  new ItemStack(blockItem1, 1, 5));
        registerMetal("chrome_sign",        null, "chrome",         new ItemStack(item, 1, 3),  new ItemStack(blockItem1, 1, 3));
        registerMetal("copper_sign",        null, "copper",         new ItemStack(item, 1, 4),  new ItemStack(blockItem2, 1, 1));
        registerMetal("electrum_sign",      null, "electrum",       new ItemStack(item, 1, 5),  new ItemStack(blockItem1, 1, 7));
        registerMetal("invar_sign",         null, "invar",          new ItemStack(item, 1, 6),  new ItemStack(blockItem1, 1, 12));
        registerMetal("iridium_sign",       null, "iridium",        new ItemStack(item, 1, 7),  new ItemStack(blockItem1, 1, 13));
        registerMetal("lead_sign",          null, "lead",           new ItemStack(item, 1, 8),  new ItemStack(blockItem1, 1, 6));
        registerMetal("nickel_sign",        null, "nickel",         new ItemStack(item, 1, 9),  new ItemStack(blockItem1, 1, 11));
        registerMetal("platinum_sign",      null, "platinum",       new ItemStack(item, 1, 10), new ItemStack(blockItem1, 1, 9));
        registerMetal("silver_sign",        null, "silver",         new ItemStack(item, 1, 11), new ItemStack(blockItem1, 1, 0));
        registerMetal("steel_sign",         null, "steel",          new ItemStack(item, 1, 12), new ItemStack(blockItem1, 1, 4));
        registerMetal("tin_sign",           null, "tin",            new ItemStack(item, 1, 13), new ItemStack(blockItem2, 1, 9));
        registerMetal("titanium_sign",      null, "titanium",       new ItemStack(item, 1, 14), new ItemStack(blockItem1, 1, 2));
        registerMetal("tungsten_sign",      null, "tungsten",       new ItemStack(item, 1, 15), new ItemStack(blockItem1, 1, 10));
        registerMetal("tungstensteel_sign", null, "tungstensteel",  new ItemStack(item, 1, 17), new ItemStack(blockItem2, 1, 0));
        registerMetal("zinc_sign",          null, "zinc",           new ItemStack(item, 1, 18), new ItemStack(blockItem1, 1, 8));
        registerMetal("refinediron_sign",   null, "refinediron",    new ItemStack(item, 1, 19), new ItemStack(blockItem2, 1, 10));

    }

    private void registerMetal(String name, SignSpecialProperty property, String materialName, ItemStack material, ItemStack materialBlock) throws IntegrationException {
        SignRegistry.register(name, property, materialName, PATH, false, material, materialBlock, Reference.MODID, TECHREBORN_TAG).setMetal();
    }

    @Override
    public String getActivateTag() {
        return TECHREBORN_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(TECHREBORN_TAG);
    }

    @Override
    public String getModName() {
        return TECHREBORN_NAME;
    }
}

package gory_moon.moarsigns.integration.techreborn;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.ArrayList;

public class TechRebornIntegration implements ISignRegistration {

    private static final String TECHREBORN_TAG = "techreborn";
    private static final String TECHREBORN_NAME = "TechReborn";
    private static final String PATH = "techreborn/";

    @ObjectHolder("techreborn:ingot")
    public static Item item = null;

    @ObjectHolder("techreborn:nuggets")
    public static Item nugget = null;

    @ObjectHolder("techreborn:techreborn.storage")
    public static Block blockItem1 = null;

    @ObjectHolder("techreborn:techreborn.storage2")
    public static Block blockItem2 = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        registerMetal("aluminium_sign",     "aluminium",      nugget, 0,  item, 0,  blockItem1, 1);
        registerMetal("brass_sign",         "brass",          nugget, 1,  item, 1,  blockItem1, 5);
        registerMetal("chrome_sign",        "chrome",         nugget, 3,  item, 3,  blockItem1, 3);
        registerMetal("copper_sign",        "copper",         nugget, 4,  item, 4,  blockItem2, 1);
        registerMetal("electrum_sign",      "electrum",       nugget, 5,  item, 5,  blockItem1, 7);
        registerMetal("invar_sign",         "invar",          nugget, 6,  item, 6,  blockItem1, 12);
        registerMetal("iridium_sign",       "iridium",        nugget, 7,  item, 7,  blockItem1, 13);
        registerMetal("lead_sign",          "lead",           nugget, 8,  item, 8,  blockItem1, 6);
        registerMetal("nickel_sign",        "nickel",         nugget, 9,  item, 9,  blockItem1, 11);
        registerMetal("platinum_sign",      "platinum",       nugget, 10, item, 10, blockItem1, 9);
        registerMetal("silver_sign",        "silver",         nugget, 11, item, 11, blockItem1, 0);
        registerMetal("steel_sign",         "steel",          nugget, 12, item, 12, blockItem1, 4);
        registerMetal("tin_sign",           "tin",            nugget, 13, item, 13, blockItem2, 9);
        registerMetal("titanium_sign",      "titanium",       nugget, 14, item, 14, blockItem1, 2);
        registerMetal("tungsten_sign",      "tungsten",       nugget, 15, item, 15, blockItem1, 10);
        registerMetal("tungstensteel_sign", "tungstensteel",  nugget, 17, item, 17, blockItem2, 0);
        registerMetal("zinc_sign",          "zinc",           nugget, 18, item, 18, blockItem1, 8);
        registerMetal("refinediron_sign",   "refinediron",    nugget, 19, item, 19, blockItem2, 10);

    }

    private void registerMetal(String name, String materialName, Item nugget, int nMeta, Item ingot, int iMeta, Block block, int bMeta) throws IntegrationException {
        SignRegistry.register(name, null, materialName, PATH, true, new ItemStack(nugget, 1, nMeta), new ItemStack(ingot, 1, iMeta), new ItemStack(block, 1, bMeta), Reference.MODID, TECHREBORN_TAG).setMetal();
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

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

import javax.annotation.Nonnull;

public class TechRebornIntegration implements ISignRegistration {

    private static final String TECHREBORN_TAG = "techreborn";
    private static final String TECHREBORN_NAME = "TechReborn";
    private static final String PATH = "techreborn/";

    @ObjectHolder("techreborn:ingot")
    public static Item ingot = null;

    @ObjectHolder("techreborn:nuggets")
    public static Item nugget = null;

    @ObjectHolder("techreborn:techreborn.storage")
    public static Block blockItem1 = null;

    @ObjectHolder("techreborn:techreborn.storage2")
    public static Block blockItem2 = null;

    @Override
    public void registerSigns() throws IntegrationException {
        registerMetal("aluminium_sign",     "aluminium",     0, 0,  blockItem1, 1);
        registerMetal("brass_sign",         "brass",         1, 1,  blockItem1, 5);
        registerMetal("bronze_sign",        "bronze",        2, 2,  blockItem1, 14);
        registerMetal("chrome_sign",        "chrome",        3, 3,  blockItem1, 3);
        registerMetal("copper_sign",        "copper",        4, 4,  blockItem2, 1);
        registerMetal("electrum_sign",      "electrum",      5, 5,  blockItem1, 7);
        registerMetal("invar_sign",         "invar",         6, 6,  blockItem1, 12);
        registerMetal("iridium_sign",       "iridium",       7, 7,  blockItem1, 13);
        registerMetal("lead_sign",          "lead",          8, 8,  blockItem1, 6);
        registerMetal("nickel_sign",        "nickel",        9, 9,  blockItem1, 11);
        registerMetal("platinum_sign",      "platinum",      10, 10, blockItem1, 9);
        registerMetal("silver_sign",        "silver",        11, 11, blockItem1, 0);
        registerMetal("steel_sign",         "steel",         12, 12, blockItem1, 4);
        registerMetal("tin_sign",           "tin",           13, 13, blockItem2, 9);
        registerMetal("titanium_sign",      "titanium",      14, 14, blockItem1, 2);
        registerMetal("tungsten_sign",      "tungsten",      15, 15, blockItem1, 10);
        registerMetal("tungstensteel_sign", "tungstensteel", 17, 17, blockItem2, 0);
        registerMetal("zinc_sign",          "zinc",          18, 18, blockItem1, 8);
        registerMetal("refinediron_sign",   "refinediron",   19, 19, blockItem2, 10);

    }

    private void registerMetal(String name, String materialName, int nMeta, int iMeta, Block block, int bMeta) throws IntegrationException {
        SignRegistry.register(name, null, materialName, PATH, true, new ItemStack(nugget, 1, nMeta), new ItemStack(ingot, 1, iMeta), new ItemStack(block, 1, bMeta), Reference.MODID, TECHREBORN_TAG).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return TECHREBORN_TAG;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(TECHREBORN_TAG);
    }

    @Override
    public String getModName() {
        return TECHREBORN_NAME;
    }
}

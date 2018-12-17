package gory_moon.moarsigns.integration.ie;

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
import javax.annotation.Nullable;

public class ImmersiveEngineeringIntegration implements ISignRegistration {

    private static final String IE_ID = "immersiveengineering";
    private static final String IE_NAME = "Immersive Engineering";
    private static final String PATH = "ie/";

    @ObjectHolder("immersiveengineering:metal")
    public static Item ingot = null;

    @ObjectHolder("immersiveengineering:metal")
    public static Item nugget = null;

    @ObjectHolder("immersiveengineering:treated_wood")
    public static Block itemWood = null;

    @ObjectHolder("immersiveengineering:storage")
    public static Block itemBlock = null;

    @Override
    public void registerSigns() throws IntegrationException {
        SignRegistry.register("treatedwood_sign", null, "treatedwood", PATH, false, ItemStack.EMPTY, new ItemStack(itemWood, 1, 0), Reference.MODID, IE_ID);
        registerMetal("copper_sign",        "copper",     20, 0, 0);
        registerMetal("aluminium_sign",     "aluminium",  21, 1, 1);
        registerMetal("lead_sign",          "lead",       22, 2, 2);
        registerMetal("silver_sign",        "silver",     23, 3, 3);
        registerMetal("nickel_sign",        "nickel",     24, 4, 4);
        registerMetal("uranium_sign",       "uranium",    25, 5, 5);
        registerMetal("constantan_sign",    "constantan", 26, 6, 6);
        registerMetal("electrum_sign",      "electrum",   27, 7, 7);
        registerMetal("steel_sign",         "steel",      28, 8, 8);
    }

    private void registerMetal(String name, String materialName, int nMeta, int iMeta, int blockMeta) throws IntegrationException {
        SignRegistry.register(name, null, materialName, PATH, true, new ItemStack(nugget, 1, nMeta), new ItemStack(ingot, 1, iMeta), new ItemStack(itemBlock, 1, blockMeta), Reference.MODID, IE_ID).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return IE_ID;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(IE_ID);
    }

    @Nullable
    @Override
    public String getModName() {
        return IE_NAME;
    }
}

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
import java.util.ArrayList;

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
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        SignRegistry.register("treatedwood_sign", null, "treatedwood", PATH, false, ItemStack.EMPTY, new ItemStack(itemWood, 1, 0), Reference.MODID, IE_ID);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        registerMetal("copper_sign",        "copper",       nugget, 20, ingot, 0, itemBlock, 0);
        registerMetal("aluminium_sign",     "aluminium",    nugget, 21, ingot, 1, itemBlock, 1);
        registerMetal("lead_sign",          "lead",         nugget, 22, ingot, 2, itemBlock, 2);
        registerMetal("silver_sign",        "silver",       nugget, 23, ingot, 3, itemBlock, 3);
        registerMetal("nickel_sign",        "nickel",       nugget, 24, ingot, 4, itemBlock, 4);
        registerMetal("uranium_sign",       "uranium",      nugget, 25, ingot, 5, itemBlock, 5);
        registerMetal("constantan_sign",    "constantan",   nugget, 26, ingot, 6, itemBlock, 6);
        registerMetal("electrum_sign",      "electrum",     nugget, 27, ingot, 7, itemBlock, 7);
        registerMetal("steel_sign",         "steel",        nugget, 28, ingot, 8, itemBlock, 8);
    }

    private void registerMetal(String name, String materialName, Item nugget, int nMeta, Item ingot, int iMeta, Block block, int blockMeta) throws IntegrationException {
        SignRegistry.register(name, null, materialName, PATH, true, new ItemStack(nugget, 1, nMeta), new ItemStack(ingot, 1, iMeta), new ItemStack(block, 1, blockMeta), Reference.MODID, IE_ID).setMetal();
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

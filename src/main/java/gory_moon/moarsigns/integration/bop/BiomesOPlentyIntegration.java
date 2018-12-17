package gory_moon.moarsigns.integration.bop;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import javax.annotation.Nonnull;

public class BiomesOPlentyIntegration implements ISignRegistration {

    private static final String BOP_TAG = "biomesoplenty";
    private static final String BOP_NAME = "Biomes O' Plenty";
    private static final String PATH = "bop/";

    @ObjectHolder("biomesoplenty:planks_0")
    public static Block bopItem = null;

    @Override
    public void registerSigns() throws IntegrationException {
        registerWood("sacred_oak_sign",    null, "sacred_oak",  new ItemStack(bopItem, 1, 0));
        registerWood("cherry_sign",        null, "cherry",      new ItemStack(bopItem, 1, 1));
        registerWood("umbran_sign",        null, "umbran",      new ItemStack(bopItem, 1, 2));
        registerWood("fir_sign",           null, "fir",         new ItemStack(bopItem, 1, 3));
        registerWood("ethereal_sign",      null, "ethereal",    new ItemStack(bopItem, 1, 4));
        registerWood("magic_sign",         null, "magic",       new ItemStack(bopItem, 1, 5));
        registerWood("mangrove_sign",      null, "mangrove",    new ItemStack(bopItem, 1, 6));
        registerWood("palm_sign",          null, "palm",        new ItemStack(bopItem, 1, 7));
        registerWood("redwood_sign",       null, "redwood",     new ItemStack(bopItem, 1, 8));
        registerWood("willow_sign",        null, "willow",      new ItemStack(bopItem, 1, 9));
        registerWood("pine_sign",          null, "pine",        new ItemStack(bopItem, 1, 10));
        registerWood("hellbark_sign",      null, "hellbark",    new ItemStack(bopItem, 1, 11));
        registerWood("jacaranda_sign",     null, "jacaranda",   new ItemStack(bopItem, 1, 12));
        registerWood("mahogany_sign",      null, "mahogany",    new ItemStack(bopItem, 1, 13));
        registerWood("ebony_sign",         null, "ebony",       new ItemStack(bopItem, 1, 14));
        registerWood("eucalyptus_sign",    null, "eucalyptus",  new ItemStack(bopItem, 1, 15));
    }

    private void registerWood(String name, SignSpecialProperty property, String materialName, ItemStack material) throws IntegrationException {
        SignRegistry.register(name, property, materialName, PATH, false, ItemStack.EMPTY, material, Reference.MODID, BOP_TAG);
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return BOP_TAG;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(BOP_TAG);
    }

    @Override
    public String getModName() {
        return BOP_NAME;
    }
}

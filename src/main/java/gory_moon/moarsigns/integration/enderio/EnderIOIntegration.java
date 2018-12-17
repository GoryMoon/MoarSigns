package gory_moon.moarsigns.integration.enderio;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnderIOIntegration implements ISignRegistration {

    private static final String ENDERIO_ID = "enderio";
    private static final String ENDERIO_NAME = "Ender IO";

    @GameRegistry.ObjectHolder("enderio:item_alloy_ingot")
    public static Item ingot = null;

    @GameRegistry.ObjectHolder("enderio:item_alloy_nugget")
    public static Item nugget = null;

    @GameRegistry.ObjectHolder("enderio:block_alloy")
    public static Block block = null;

    @Override
    public void registerSigns() throws IntegrationException {
        registerMetal("electricalsteel_sign",   null, "electricalsteel", 0);
        registerMetal("energeticalloy_sign",    null, "energeticalloy",  1);
        registerMetal("vibrantalloy_sign",      null, "vibrantalloy",    2);
        registerMetal("redstonealloy_sign",     null, "redstonealloy",   3);
        registerMetal("conductiveiron_sign",    null, "conductiveiron",  4);
        registerMetal("pulsatingiron_sign",     null, "pulsatingiron",   5);
        registerMetal("darksteel_sign",         null, "darksteel",       6);
        registerMetal("soularium_sign",         null, "soularium",       7);
        registerMetal("endsteel_sign",          null, "endsteel",        8);
        registerMetal("ironalloy_sign",         null, "ironalloy",       9);
    }

    private void registerMetal(String name, SignSpecialProperty property, String materialName,int meta) throws IntegrationException {
        SignRegistry.register(name, property, materialName, "enderio/", true, new ItemStack(nugget, 1, meta), new ItemStack(ingot, 1, meta), new ItemStack(block, 1, meta), Reference.MODID, ENDERIO_ID).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return ENDERIO_ID;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(ENDERIO_ID);
    }

    @Nullable
    @Override
    public String getModName() {
        return ENDERIO_NAME;
    }
}

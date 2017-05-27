package gory_moon.moarsigns.integration.enderio;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class EnderIOIntegration implements ISignRegistration {

    private static final String ENDERIO_ID = "EnderIO";
    private static final String ENDERIO_NAME = "Ender IO";
    private Item item;
    private Item itemBlock;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack: metals) {
            if (stack.getUnlocalizedName().equals("enderio.electricalSteel") && item == null) {
                item = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.enderio.electricalSteel") && itemBlock == null) {
                itemBlock = stack.getItem();
            }

            if (item != null && itemBlock != null)
                break;
        }

        registerMetal("electricalsteel_sign",   null, "electricalsteel",    false, 0, 0);
        registerMetal("energeticalloy_sign",    null, "energeticalloy",     false, 1, 1);
        registerMetal("vibrantalloy_sign",      null, "vibrantalloy",       true,  2, 2);
        registerMetal("redstonealloy_sign",     null, "redstonealloy",      false, 3, 3);
        registerMetal("conductiveiron_sign",    null, "conductiveiron",     false, 4, 4);
        registerMetal("pulsatingiron_sign",     null, "pulsatingiron",      true,  5, 5);
        registerMetal("darksteel_sign",         null, "darksteel",          false, 6, 6);
        registerMetal("soularium_sign",         null, "soularium",          false, 7, 7);
    }

    private void registerMetal(String name, SignSpecialProperty property, String materialName, boolean nugget,int meta, int blockMeta) throws IntegrationException {
        SignRegistry.register(name, property, materialName, "enderio/", nugget, new ItemStack(item, 1, meta), new ItemStack(itemBlock, 1, blockMeta), Reference.MODID, ENDERIO_ID).setMetal();
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

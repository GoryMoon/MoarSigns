package gory_moon.moarsigns.integration.randomthings;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class RandomThingsIntegration implements ISignRegistration {

    private static final String RT_ID = "randomthings";
    private static final String RT_NAME = "Random Things";
    private Item item;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        for (ItemStack stack: planks) {
            if (stack.getUnlocalizedName().equals("tile.spectrePlank") && item == null) {
                item = stack.getItem();
                break;
            }
        }

        SignRegistry.register("spectre_sign", null, "spectre", "randomthings/", false, new ItemStack(item, 1, 0), Reference.MODID, RT_ID);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        // No metal to register
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return RT_ID;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(RT_ID);
    }

    @Nullable
    @Override
    public String getModName() {
        return RT_NAME;
    }
}

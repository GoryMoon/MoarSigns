package gory_moon.moarsigns.integration.randomthings;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RandomThingsIntegration implements ISignRegistration {

    private static final String RT_ID = "randomthings";
    private static final String RT_NAME = "Random Things";

    @ObjectHolder("randomthings:spectreplank")
    public static Item item = null;

    @Override
    public void registerSigns() throws IntegrationException {
        SignRegistry.register("spectre_sign", null, "spectre", "randomthings/", false, ItemStack.EMPTY, new ItemStack(item, 1, 0), Reference.MODID, RT_ID);
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

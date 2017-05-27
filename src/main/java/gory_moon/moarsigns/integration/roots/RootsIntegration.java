package gory_moon.moarsigns.integration.roots;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class RootsIntegration implements ISignRegistration {

    private static final String ROOTS_TAG = "roots";
    private Item item = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        for (ItemStack plank : planks) {
            if (plank.getUnlocalizedName().equals("tile.plankWildwood") && item == null) {
                item = plank.getItem();
                break;
            }
        }

        SignRegistry.register("wildwood_sign", null, "wildwood", "roots/", false, new ItemStack(item, 1, 0), Reference.MODID, ROOTS_TAG);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        // No metal to register
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return ROOTS_TAG;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(ROOTS_TAG);
    }

    @Nullable
    @Override
    public String getModName() {
        return StringUtils.capitalize(ROOTS_TAG);
    }
}

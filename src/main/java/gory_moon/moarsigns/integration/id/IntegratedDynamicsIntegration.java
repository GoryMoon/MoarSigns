package gory_moon.moarsigns.integration.id;

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

public class IntegratedDynamicsIntegration implements ISignRegistration {

    private static final String ID_ID = "integrateddynamics";
    private static final String ID_NAME = "Integrated Dynamics";
    private Item item;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {

        for (ItemStack stack: planks) {
            if (stack.getUnlocalizedName().equals("tile.blocks.integrateddynamics.menrilPlanks") && item == null) {
                item = stack.getItem();
                break;
            }
        }

        SignRegistry.register("menril_sign", null, "menril", "id/", false, new ItemStack(item, 1, 0), Reference.MODID, ID_ID);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        // No metal to register
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return ID_ID;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(ID_ID);
    }

    @Nullable
    @Override
    public String getModName() {
        return ID_NAME;
    }
}

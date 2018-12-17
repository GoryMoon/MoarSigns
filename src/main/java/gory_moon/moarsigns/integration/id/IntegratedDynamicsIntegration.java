package gory_moon.moarsigns.integration.id;

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

public class IntegratedDynamicsIntegration implements ISignRegistration {

    private static final String ID_ID = "integrateddynamics";
    private static final String ID_NAME = "Integrated Dynamics";

    @ObjectHolder("integrateddynamics:menril_planks")
    public static Item item = null;

    @Override
    public void registerSigns() throws IntegrationException {
        SignRegistry.register("menril_sign", null, "menril", "id/", false, ItemStack.EMPTY, new ItemStack(item, 1, 0), Reference.MODID, ID_ID);
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

package gory_moon.moarsigns.integration.ic2;

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

public class IndustrialCraft2Integration implements ISignRegistration {

    private static final String IC2_TAG = "ic2";
    private static final String IC2_NAME = "IndustrialCraft 2";

    @ObjectHolder("ic2:ingot")
    public static Item item = null;

    @ObjectHolder("ic2:resource")
    public static Block blockItem = null;

    @Override
    public void registerSigns() throws IntegrationException {
        SignRegistry.register("bronze_sign",    null, "bronze", "ic2/",  false, ItemStack.EMPTY, new ItemStack(item, 1, 1), new ItemStack(blockItem, 1, 5),  Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("copper_sign",    null, "copper", "ic2/",  false, ItemStack.EMPTY, new ItemStack(item, 1, 2), new ItemStack(blockItem, 1, 6),  Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("lead_sign",      null, "lead",   "ic2/",  false, ItemStack.EMPTY, new ItemStack(item, 1, 3), new ItemStack(blockItem, 1, 7),  Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("silver_sign",    null, "silver", "ic2/",  false, ItemStack.EMPTY, new ItemStack(item, 1, 4), new ItemStack(blockItem, 1, 15), Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("steel_sign",     null, "steel",  "ic2/",  false, ItemStack.EMPTY, new ItemStack(item, 1, 5), new ItemStack(blockItem, 1, 8),  Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("tin_sign",       null, "tin",    "ic2/",  false, ItemStack.EMPTY, new ItemStack(item, 1, 6), new ItemStack(blockItem, 1, 9),  Reference.MODID, IC2_TAG).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return IC2_TAG;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(IC2_TAG);
    }

    @Override
    public String getModName() {
        return IC2_NAME;
    }


}

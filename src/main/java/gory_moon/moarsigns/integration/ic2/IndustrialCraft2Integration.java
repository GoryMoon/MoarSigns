package gory_moon.moarsigns.integration.ic2;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class IndustrialCraft2Integration implements ISignRegistration {

    private static final String IC2_TAG = "IC2";
    private static final String IC2_NAME = "IndustrialCraft 2";
    private Item item = null;
    private Item blockItem = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("ic2.ingot.bronze") && item == null) {
                item = stack.getItem();
            } else if (stack.getUnlocalizedName().equals("ic2.resource.bronze_block") && blockItem == null) {
                blockItem = stack.getItem();
            }

            if (item != null && blockItem != null)
                break;
        }

        SignRegistry.register("bronze_sign",    null, "bronze", "ic2/", false, new ItemStack(item, 1, 1), new ItemStack(blockItem, 1, 5), Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("copper_sign",    null, "copper", "ic2/", false, new ItemStack(item, 1, 2), new ItemStack(blockItem, 1, 6), Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("lead_sign",      null, "lead",   "ic2/", false, new ItemStack(item, 1, 3), new ItemStack(blockItem, 1, 7), Reference.MODID, IC2_TAG).setMetal();

        SignRegistry.register("steel_sign",     null, "steel",  "ic2/",  false, new ItemStack(item, 1, 5), new ItemStack(blockItem, 1, 8), Reference.MODID, IC2_TAG).setMetal();
        SignRegistry.register("tin_sign",       null, "tin",    "ic2/",  false, new ItemStack(item, 1, 6), new ItemStack(blockItem, 1, 9), Reference.MODID, IC2_TAG).setMetal();

    }

    @Override
    public String getActivateTag() {
        return IC2_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(IC2_TAG);
    }

    @Override
    public String getModName() {
        return IC2_NAME;
    }


}

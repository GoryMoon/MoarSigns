package gory_moon.moarsigns.integration.factorization;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class FactorizationIntegration implements ISignRegistration {

    private static final String FACTORIZATION_TAG = "factorization";
    private Item factorizationSilverItem = null;
    private Item factorizationLeadItem = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {

    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {
        for (ItemStack stacks : metals) {
            if (stacks.getUnlocalizedName().equals("item.factorization:silver_ingot")) {
                factorizationSilverItem = stacks.copy().getItem();
            }
            if (stacks.getUnlocalizedName().equals("item.factorization:lead_ingot")) {
                factorizationLeadItem = stacks.copy().getItem();
            }
            if (factorizationSilverItem != null && factorizationLeadItem != null) break;
        }

        //TODO Add block material
        SignRegistry.register("silver_sign", null, "silver", "factorization/", true, new ItemStack(factorizationSilverItem, 1, 0), ModInfo.ID, FACTORIZATION_TAG).setMetal();
        SignRegistry.register("lead_sign", null, "lead", "factorization/", true, new ItemStack(factorizationLeadItem, 1, 0), ModInfo.ID, FACTORIZATION_TAG).setMetal();
    }

    @Override
    public String getActivateTag() {
        return FACTORIZATION_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(FACTORIZATION_TAG);
    }
}

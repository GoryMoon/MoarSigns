package gory_moon.moarsigns.integration.natura;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class NaturaIntegration implements ISignRegistration {

    private static final String NATURA_TAG = "Natura";
    private Item naturaItem = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {
        for (ItemStack plank : planks) {
            if (plank.getUnlocalizedName().equals("block.eucalyptus.NPlanks")) {
                naturaItem = plank.copy().getItem();
                break;
            }
        }

        SignRegistry.register("eucalyptus_sign", null, "eucalyptus", "natura/", false, new ItemStack(naturaItem, 1, 0), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("sakura_sign", null, "sakura", "natura/", false, new ItemStack(naturaItem, 1, 1), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("ghostwood_sign", null, "ghostwood", "natura/", false, new ItemStack(naturaItem, 1, 2), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("redwood_sign", null, "redwood", "natura/", false, new ItemStack(naturaItem, 1, 3), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("bloodwood_sign", null, "bloodwood", "natura/", false, new ItemStack(naturaItem, 1, 4), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("hopseed_sign", null, "hopseed", "natura/", false, new ItemStack(naturaItem, 1, 5), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("maple_sign", null, "maple", "natura/", false, new ItemStack(naturaItem, 1, 6), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("silverbell_sign", null, "silverbell", "natura/", false, new ItemStack(naturaItem, 1, 7), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("purpleheart_sign", null, "purpleheart", "natura/", false, new ItemStack(naturaItem, 1, 8), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("tiger_sign", null, "tiger", "natura/", false, new ItemStack(naturaItem, 1, 9), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("willow_sign", null, "willow", "natura/", false, new ItemStack(naturaItem, 1, 10), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("darkwood_sign", null, "darkwood", "natura/", false, new ItemStack(naturaItem, 1, 11), ModInfo.ID, NATURA_TAG);
        SignRegistry.register("fusewood_sign", null, "fusewood", "natura/", false, new ItemStack(naturaItem, 1, 12), ModInfo.ID, NATURA_TAG);

    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {

    }

    @Override
    public String getActivateTag() {
        return NATURA_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(NATURA_TAG);
    }
}

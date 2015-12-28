package gory_moon.moarsigns.integration.ic2;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class IndustrialCraft2Integration implements ISignRegistration {

    private static final String IC2_TAG = "IC2";
    private Item ic2Item = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> ingots) {
        for (ItemStack stacks : ingots) {
            if (stacks.getUnlocalizedName().equals("ic2.itemIngotCopper")) {
                ic2Item = stacks.copy().getItem();
                break;
            }
        }

        SignRegistry.register("copper_sign", null, "copper", "ic2/", false, new ItemStack(ic2Item, 1, 0), ModInfo.ID, IC2_TAG).setMetal(true);
        SignRegistry.register("tin_sign", null, "tin", "ic2/", false, new ItemStack(ic2Item, 1, 1), ModInfo.ID, IC2_TAG).setMetal(true);
        SignRegistry.register("bronze_sign", null, "bronze", "ic2/", false, new ItemStack(ic2Item, 1, 2), ModInfo.ID, IC2_TAG).setMetal(true);
        //TODO add lead sign textures
        //SignRegistry.register("lead_sign", null, "lead", "ic2/", false, new ItemStack(ic2Item, 1, 5)).setMetal(true);
    }

    @Override
    public String getActivateTag() {
        return IC2_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(IC2_TAG);
    }


}

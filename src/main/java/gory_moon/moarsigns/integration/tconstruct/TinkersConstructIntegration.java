package gory_moon.moarsigns.integration.tconstruct;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TinkersConstructIntegration implements ISignRegistration {

    private static final String TCONSTRUCT_TAG = "TConstruct";
    private Item tconstructItem = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {

    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {
        for (ItemStack stacks : metals) {
            if (stacks.getUnlocalizedName().equals("item.tconstruct.Materials.CopperIngot")) {
                tconstructItem = stacks.copy().getItem();
                break;
            }
        }

        SignRegistry.register("copper_sign", null, "copper", "tconstruct/", true, new ItemStack(tconstructItem, 1, 9), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("tin_sign", null, "tin", "tconstruct/", true, new ItemStack(tconstructItem, 1, 10), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("bronze_sign", null, "bronze", "tconstruct/", true, new ItemStack(tconstructItem, 1, 13), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("steel_sign", null, "steel", "tconstruct/", true, new ItemStack(tconstructItem, 1, 16), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
    }

    @Override
    public String getActivateTag() {
        return TCONSTRUCT_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(TCONSTRUCT_TAG);
    }
}

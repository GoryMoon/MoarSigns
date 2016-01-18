package gory_moon.moarsigns.integration.railcraft;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class RailcraftIntegration implements ISignRegistration {

    private static final String RAILCRAFT_TAG = "Railcraft";
    private Item item;
    private Item itemBlock;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {

    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("item.railcraft.ingot.tin")) {
                item = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.railcraft.cube.tin")) {
                itemBlock = stack.getItem();
            }

            if (item != null && itemBlock != null) break;
        }

        SignRegistry.register("steel_sign", null, "steel", "railcraft/", true, new ItemStack(item, 1, 0), new ItemStack(itemBlock, 1, 0), ModInfo.ID, RAILCRAFT_TAG).setMetal();
        SignRegistry.register("copper_sign", null, "copper", "railcraft/", true, new ItemStack(item, 1, 1), new ItemStack(itemBlock, 1, 1), ModInfo.ID, RAILCRAFT_TAG).setMetal();
        SignRegistry.register("tin_sign", null, "tin", "railcraft/", true, new ItemStack(item, 1, 2), new ItemStack(itemBlock, 1, 2), ModInfo.ID, RAILCRAFT_TAG).setMetal();
        SignRegistry.register("lead_sign", null, "lead", "railcraft/", true, new ItemStack(item, 1, 3), new ItemStack(itemBlock, 1, 3), ModInfo.ID, RAILCRAFT_TAG).setMetal();
    }

    @Override
    public String getActivateTag() {
        return RAILCRAFT_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(RAILCRAFT_TAG);
    }

    @Override
    public String getModName() {
        return RAILCRAFT_TAG;
    }
}

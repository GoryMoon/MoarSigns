package gory_moon.moarsigns.integration.railcraft;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class RailcraftIntegration implements ISignRegistration {

    private static final String RAILCRAFT_TAG = "railcraft";
    private Item item;
    private Item itemBlock;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().startsWith("item.railcraft.ingot.") && item == null) {
                item = stack.getItem();
            }

            if (stack.getUnlocalizedName().startsWith("tile.railcraft.generic") && itemBlock == null) {
                itemBlock = stack.getItem();
            }

            if (item != null && itemBlock != null)
                break;
        }

        SignRegistry.register("steel_sign",     null, "steel",  "railcraft/", true, new ItemStack(item, 1, 0), new ItemStack(itemBlock, 1, 3), Reference.MODID, RAILCRAFT_TAG).setMetal();
        SignRegistry.register("copper_sign",    null, "copper", "railcraft/", true, new ItemStack(item, 1, 1), new ItemStack(itemBlock, 1, 0), Reference.MODID, RAILCRAFT_TAG).setMetal();
        SignRegistry.register("tin_sign",       null, "tin",    "railcraft/", true, new ItemStack(item, 1, 2), new ItemStack(itemBlock, 1, 1), Reference.MODID, RAILCRAFT_TAG).setMetal();
        SignRegistry.register("lead_sign",      null, "lead",   "railcraft/", true, new ItemStack(item, 1, 3), new ItemStack(itemBlock, 1, 2), Reference.MODID, RAILCRAFT_TAG).setMetal();
        SignRegistry.register("silver_sign",    null, "silver", "railcraft/", true, new ItemStack(item, 1, 4), new ItemStack(itemBlock, 1, 10), Reference.MODID, RAILCRAFT_TAG).setMetal();
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
        return StringUtils.capitalize(RAILCRAFT_TAG);
    }
}

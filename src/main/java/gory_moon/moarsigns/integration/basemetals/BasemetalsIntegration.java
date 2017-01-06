package gory_moon.moarsigns.integration.basemetals;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BasemetalsIntegration implements ISignRegistration {

    private static final String BASEMETALS_TAG = "basemetals";
    private static final String BASEMETALS_NAME = "Base Metals";

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        Map<String, BaseItemHelper> items = new HashMap<String, BaseItemHelper>();

        for (ItemStack stack : metals) {
            String unloc = stack.getUnlocalizedName();

            if (unloc.contains(".basemetals.")) {
                String key = unloc.substring(unloc.indexOf(".") + 1, unloc.indexOf("_"));

                if (unloc.startsWith("item.basemetals.")) {
                    if (items.containsKey(key)) {
                        items.get(key).item = stack;
                    } else {
                        items.put(key, new BaseItemHelper().setItem(stack));
                    }
                }

                if (unloc.startsWith("tile.basemetals.")) {
                    if (items.containsKey(key)) {
                        items.get(key).itemBlock = stack;
                    } else {
                        items.put(key, new BaseItemHelper().setItemBlock(stack));
                    }
                }
            }
        }

        for (Map.Entry<String, BaseItemHelper> entry : items.entrySet()) {
            String metal = entry.getKey().substring(entry.getKey().indexOf(".") + 1);
            SignRegistry.register(metal + "_sign", null, metal, "basemetals/", true, entry.getValue().item, entry.getValue().itemBlock, Reference.MODID, BASEMETALS_TAG).setMetal();
        }
    }

    @Override
    public String getActivateTag() {
        return BASEMETALS_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(BASEMETALS_TAG);
    }

    @Override
    public String getModName() {
        return BASEMETALS_NAME;
    }

    private class BaseItemHelper {
        ItemStack item;
        ItemStack itemBlock;

        BaseItemHelper setItem(ItemStack item) {
            this.item = item;
            return this;
        }

        BaseItemHelper setItemBlock(ItemStack itemBlock) {
            this.itemBlock = itemBlock;
            return this;
        }
    }
}

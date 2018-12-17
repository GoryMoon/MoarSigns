package gory_moon.moarsigns.integration.basemetals;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BasemetalsIntegration implements ISignRegistration {

    private static final String BASEMETALS_TAG = "basemetals";
    private static final String BASEMETALS_NAME = "Base Metals";

    @Override
    public void registerSigns() throws IntegrationException {
        Map<String, BaseItemHelper> items = new HashMap<String, BaseItemHelper>();
        items.put("adamantine", new BaseItemHelper());
        items.put("antimony", new BaseItemHelper());
        items.put("bismuth", new BaseItemHelper());
        items.put("coldiron", new BaseItemHelper());
        items.put("copper", new BaseItemHelper());
        items.put("lead", new BaseItemHelper());
        items.put("nickel", new BaseItemHelper());
        items.put("platinum", new BaseItemHelper());
        items.put("silver", new BaseItemHelper());
        items.put("starsteel", new BaseItemHelper());
        items.put("tin", new BaseItemHelper());
        items.put("zinc", new BaseItemHelper());
        items.put("aquarium", new BaseItemHelper());
        items.put("brass", new BaseItemHelper());
        items.put("bronze", new BaseItemHelper());
        items.put("cupronickel", new BaseItemHelper());
        items.put("electrum", new BaseItemHelper());
        items.put("invar", new BaseItemHelper());
        items.put("mithril", new BaseItemHelper());
        items.put("pewter", new BaseItemHelper());
        items.put("steel", new BaseItemHelper());

        fillItems(items, ForgeRegistries.ITEMS.getValuesCollection());
        fillItems(items, ForgeRegistries.BLOCKS.getValuesCollection());

        for (Map.Entry<String, BaseItemHelper> entry : items.entrySet()) {
            String metal = entry.getKey().substring(entry.getKey().indexOf(".") + 1);
            SignRegistry.register(metal + "_sign", null, metal, "basemetals/", true, ItemStack.EMPTY, entry.getValue().item, entry.getValue().itemBlock, Reference.MODID, BASEMETALS_TAG).setMetal();
        }
    }

    private void fillItems(Map<String, BaseItemHelper> items, Collection fromItems) {
        for (Object obj : fromItems) {
            String tKey = null;
            if (obj instanceof Item)
                tKey = ((Item) obj).getTranslationKey();
            else if (obj instanceof Block)
                tKey = ((Block) obj).getTranslationKey();

            if (tKey != null && tKey.contains(".basemetals.") && (tKey.contains("_ingot") || tKey.contains("_block"))) {
                ItemStack stack;
                if (obj instanceof Item)
                    stack = new ItemStack((Item) obj);
                else
                    stack = new ItemStack((Block) obj);
                tKey = stack.getTranslationKey();

                String key = tKey.substring(tKey.lastIndexOf(".") + 1, tKey.indexOf("_"));

                if (tKey.startsWith("item.basemetals.")) {
                    if (items.containsKey(key)) {
                        items.get(key).item = stack;
                    }
                }

                if (tKey.startsWith("tile.basemetals.")) {
                    if (items.containsKey(key)) {
                        items.get(key).itemBlock = stack;
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return BASEMETALS_TAG;
    }

    @Nonnull
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

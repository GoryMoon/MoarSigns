package gory_moon.moarsigns.items;

import gory_moon.moarsigns.lib.Constants;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Map;

public class ItemNugget extends Item {

    public ItemNugget() {
        hasSubtypes = true;
        setRegistryName(Constants.NUGGET_ITEM_KEY);
        setTranslationKey("moarsigns");
        setCreativeTab(CreativeTabs.MATERIALS);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + NuggetRegistry.getUnlocName(stack.getItemDamage());
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == getCreativeTab()) {
            for (Map.Entry<String, NuggetRegistry.NuggetInfo> entry : Utils.entriesSortedByValues(NuggetRegistry.getNuggets())) {
                if (entry.getValue().needed) {
                    items.add(new ItemStack(this, 1, entry.getValue().id));
                }
            }
        }
    }
}

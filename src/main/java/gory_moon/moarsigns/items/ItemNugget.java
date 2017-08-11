package gory_moon.moarsigns.items;

import gory_moon.moarsigns.lib.Constants;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.Map;

public class ItemNugget extends Item {

    public ItemNugget() {
        hasSubtypes = true;
        setRegistryName(Constants.NUGGET_ITEM_KEY);
        setUnlocalizedName("moarsigns");
        setCreativeTab(CreativeTabs.MATERIALS);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + NuggetRegistry.getUnlocName(stack.getItemDamage());
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (Map.Entry<String, NuggetRegistry.NuggetInfo> entry : Utils.entriesSortedByValues(NuggetRegistry.getNuggets())) {
            if (entry.getValue().needed) {
                subItems.add(new ItemStack(this, 1, entry.getValue().id));
            }
        }
    }
}

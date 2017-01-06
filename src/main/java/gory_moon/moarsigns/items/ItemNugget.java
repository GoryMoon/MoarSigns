package gory_moon.moarsigns.items;

import gory_moon.moarsigns.lib.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class ItemNugget extends Item {

    //private IIcon[] icons = new IIcon[NuggetRegistry.size()];

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

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (Map.Entry<String, NuggetRegistry.NuggetInfo> entry : NuggetRegistry.getNuggets().entrySet()) {
            if (entry.getValue().needed) {
                list.add(new ItemStack(this, 1, entry.getValue().id));
            }
        }
    }
}

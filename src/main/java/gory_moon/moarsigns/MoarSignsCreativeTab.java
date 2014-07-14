package gory_moon.moarsigns;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MoarSignsCreativeTab extends CreativeTabs {

    public MoarSignsCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack() {
        SignInfo info = SignRegistry.getSignRegistry().iterator().next();
        return ModItems.sign.createMoarItemStack(info.material.path + info.itemName, false);
    }

    @Override
    public Item getTabIconItem() {
        return null;
    }
}

package gory_moon.moarsigns;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MoarSignsCreativeTab extends CreativeTabs {

    public static MoarSignsCreativeTab tabMS = new MoarSignsCreativeTab("moarSigns");

    public MoarSignsCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        SignInfo info = SignRegistry.getActivatedSignRegistry().iterator().next();
        return ModItems.SIGN.createMoarItemStack(info.material.path + info.itemName, false);
    }
}

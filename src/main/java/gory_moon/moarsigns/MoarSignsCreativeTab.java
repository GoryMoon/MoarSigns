package gory_moon.moarsigns;

import gory_moon.moarsigns.items.Items;
import gory_moon.moarsigns.util.Signs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MoarSignsCreativeTab extends CreativeTabs {

    public MoarSignsCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack() {
        Signs signs = MoarSigns.instance.getSignsWood().get(0);
        return Items.sign.createMoarItemStack(signs.material[signs.activeMaterialIndex].path + signs.signName, false);
    }
}

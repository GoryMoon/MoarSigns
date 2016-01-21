package gory_moon.moarsigns.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemNugget extends Item {

    //private IIcon[] icons = new IIcon[NuggetRegistry.size()];

    public ItemNugget() {
        hasSubtypes = true;
        setUnlocalizedName("moarsigns");
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + NuggetRegistry.getUnlocName(stack.getItemDamage());
    }

    //TODO Icons
    /*
    @Override
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < NuggetRegistry.size(); i++) {
            icons[i] = register.registerIcon(Info.TEXTURE_LOCATION + ":" + "nuggets/" + NuggetRegistry.getUnlocName(i));
        }
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return dmg >= 0 && dmg < NuggetRegistry.size() ? icons[dmg] : null;
    }
    */

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < NuggetRegistry.size(); i++) {
            if (NuggetRegistry.getNeeded(i)) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }
}

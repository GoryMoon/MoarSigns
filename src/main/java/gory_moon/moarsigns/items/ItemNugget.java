package gory_moon.moarsigns.items;

import gory_moon.moarsigns.lib.Info;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemNugget extends Item {

    public String[] nuggets = {"diamond_nugget", "iron_nugget", "emerald_nugget", "bronze_nugget_ic2", "copper_nugget_ic2", "tin_nugget_ic2", "silver_nugget_factorization"};
    public Boolean[] needed = {true, true, true, false, false, false, false};
    private IIcon[] icons = new IIcon[nuggets.length];

    public ItemNugget() {
        hasSubtypes = true;
        setUnlocalizedName("moarsign");
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + nuggets[stack.getItemDamage()];
    }

    @Override
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < nuggets.length; i++) {
            icons[i] = register.registerIcon(Info.TEXTURE_LOCATION + ":" + nuggets[i]);
        }
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return dmg >= 0 && dmg < nuggets.length ? icons[dmg] : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < nuggets.length; i++) {
            if (needed[i]) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }
}

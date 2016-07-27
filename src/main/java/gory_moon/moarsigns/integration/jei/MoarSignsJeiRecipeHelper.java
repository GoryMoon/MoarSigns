package gory_moon.moarsigns.integration.jei;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe.MatchType;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gory_moon.moarsigns.integration.jei.MoarSignsPlugin.moarSigns;

public class MoarSignsJeiRecipeHelper {

    public static ArrayList<ItemStack> getSigns(Object o) {
        ArrayList<ItemStack> signs = new ArrayList<ItemStack>();
        for (ItemStack stack : moarSigns) {
            SignInfo info = ItemMoarSign.getInfo(stack.getTagCompound());

            if (o instanceof MatchType) {
                if (o == MatchType.ALL) {
                    signs.add(stack);
                } else if (o == MatchType.METAL && info.isMetal) {
                    signs.add(stack);
                } else if (o == MatchType.WOOD && !info.isMetal) {
                    signs.add(stack);
                }
            } else if (((MaterialInfo) o).materialName.equals(info.material.materialName)) {
                signs.add(stack);
            }
        }
        return signs;
    }

    public static List<ItemStack> getVariationStacks(ArrayList<SignInfo> infos) {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for (SignInfo info : infos) {
            stacks.add(ModItems.SIGN.createMoarItemStack(info.material.path + info.itemName, info.isMetal));
        }
        return stacks;
    }

}

package gory_moon.moarsigns.integration.jei;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class MoarSignsJeiRecipeHelper {

    public static List<ItemStack> getVariationStacks(ArrayList<SignInfo> infos) {
        List<ItemStack> stacks = new ArrayList<>();
        for (SignInfo info : infos) {
            stacks.add(ModItems.SIGN.createMoarItemStack(info.material.path + info.itemName, info.isMetal));
        }
        return stacks;
    }

}

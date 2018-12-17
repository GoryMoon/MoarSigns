package gory_moon.moarsigns.integration.tweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ZenClass("mods.moarsigns.Signs")
public class Signs {

    private static List<ItemStack> signs = new ArrayList<>();

    @ZenMethod
    public static IItemStack[] getSigns(IIngredient ingredient) {
        IItemStack[] base = new IItemStack[0];
        if (ingredient instanceof MatchTypeEntry || ingredient instanceof MaterialEntry) {
            if (signs.isEmpty()) {
                ModItems.SIGN.getSubItemStacks(Signs.signs);
            }
            ArrayList<IItemStack> signs = new ArrayList<>();
            if (ingredient instanceof MatchTypeEntry) {
                for (ItemStack stack : Signs.signs) {
                    SignInfo info = ItemMoarSign.getInfo(stack.getTagCompound());
                    if (ingredient.getInternal() == MatchType.ALL)
                        signs.add(new MCItemStack(stack));
                    else if (ingredient.getInternal() == MatchType.METAL && info.isMetal)
                        signs.add(new MCItemStack(stack));
                    else if (ingredient.getInternal() == MatchType.WOOD && !info.isMetal)
                        signs.add(new MCItemStack(stack));
                }
            } else {
                for (ItemStack stack : Signs.signs) {
                    SignInfo info = ItemMoarSign.getInfo(stack.getTagCompound());
                    if (((MaterialInfo) ingredient.getInternal()).materialName.equals(info.material.materialName) && (((MaterialEntry) ingredient).getModID() == null || ((MaterialEntry) ingredient).getModID().equals(info.activateTag)))
                        signs.add(new MCItemStack(stack));
                }
            }
            return signs.toArray(base);
        }
        return (IItemStack[]) Collections.emptyList().toArray();
    }

    @ZenMethod
    public static IItemStack getFirstSign(IIngredient ingredient) {
        IItemStack[] signs = getSigns(ingredient);
        if (signs.length > 0)
            return signs[0];
        return null;
    }

    protected enum MatchType {
        ALL,
        METAL,
        WOOD;

        public static MatchType getEnum(String match) {
            if ("ALL".equals(match))
                return ALL;
            else if ("METAL".equals(match))
                return METAL;
            else if ("WOOD".equals(match))
                return WOOD;
            return ALL;
        }
    }
}

package gory_moon.moarsigns.util;

import net.minecraft.item.crafting.IRecipe;

public interface IMoarSignsRecipe extends IRecipe {

    Object[] getInput();

    IMoarSignsRecipe setNEINBTDifferent(boolean nbtDiff);
    boolean isNeiNBTDifferent();

}

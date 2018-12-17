package gory_moon.moarsigns.api.ingredients;

import gory_moon.moarsigns.items.ModItems;
import net.minecraft.item.ItemStack;

public class MatchMetalIngredient extends MatchAllIngredient {
    protected MatchMetalIngredient() {
        super(new ItemStack(ModItems.SIGN, 1,1));
    }
}

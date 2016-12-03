package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.ShapelessMoarSignRecipe;

import javax.annotation.Nonnull;

public class ShapelessMoarSignsRecipeHandler extends MoarSignsRecipeHandlerBase<ShapelessMoarSignRecipe> {

    @Nonnull
    @Override
    public Class<ShapelessMoarSignRecipe> getRecipeClass() {
        return ShapelessMoarSignRecipe.class;
    }

}

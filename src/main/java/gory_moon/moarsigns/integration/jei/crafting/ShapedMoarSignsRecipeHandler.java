package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.ShapedMoarSignRecipe;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class ShapedMoarSignsRecipeHandler extends MoarSignsRecipeHandlerBase<ShapedMoarSignRecipe> {

    public ShapedMoarSignsRecipeHandler() {
        super(null);
    }

    @Nonnull
    @Override
    public Class<ShapedMoarSignRecipe> getRecipeClass() {
        return ShapedMoarSignRecipe.class;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull ShapedMoarSignRecipe recipe) {
        return new ShapedMoarSignsRecipeWrapper(recipe);
    }

}

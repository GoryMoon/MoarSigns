package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.ShapedMoarSignRecipe;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;

import javax.annotation.Nonnull;

public class ShapedMoarSignsRecipeWrapper extends MoarSignsRecipeWrapper implements IShapedCraftingRecipeWrapper {


    public ShapedMoarSignsRecipeWrapper(@Nonnull ShapedMoarSignRecipe recipe) {
        super(recipe);
    }

    @Override
    public int getWidth() {
        return ((ShapedMoarSignRecipe) this.recipe).width;
    }

    @Override
    public int getHeight() {
        return ((ShapedMoarSignRecipe) this.recipe).height;
    }
}

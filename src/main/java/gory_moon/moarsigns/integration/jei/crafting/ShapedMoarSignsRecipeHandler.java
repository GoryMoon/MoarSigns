package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe;
import gory_moon.moarsigns.integration.jei.MoarSignsPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;
import java.util.List;

public class ShapedMoarSignsRecipeHandler implements IRecipeHandler<ShapedMoarSignRecipe> {

    @Nonnull
    @Override
    public Class<ShapedMoarSignRecipe> getRecipeClass() {
        return ShapedMoarSignRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return MoarSignsPlugin.CRAFTING;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull ShapedMoarSignRecipe recipe) {
        return MoarSignsPlugin.CRAFTING;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull ShapedMoarSignRecipe recipe) {
        return new ShapedMoatSignsRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(@Nonnull ShapedMoarSignRecipe recipe) {
        if (recipe.getRecipeOutput() == null) {
            return false;
        }
        int inputCount = 0;
        for (Object input : recipe.getInput()) {
            if (input instanceof List) {
                if (((List) input).size() == 0) {
                    return false;
                }
            }
            if (input instanceof MaterialInfo) {
                if (((MaterialInfo) input).materialName == null || ((MaterialInfo) input).materialName.isEmpty()) {
                    return false;
                }
            }
            if (input != null) {
                inputCount++;
            }
        }
        return inputCount > 0;
    }
}

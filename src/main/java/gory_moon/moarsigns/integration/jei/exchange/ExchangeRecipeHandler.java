package gory_moon.moarsigns.integration.jei.exchange;

import gory_moon.moarsigns.integration.jei.MoarSignsPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class ExchangeRecipeHandler implements IRecipeHandler<ExchangeRecipe> {
    @Nonnull
    @Override
    public Class<ExchangeRecipe> getRecipeClass() {
        return ExchangeRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return MoarSignsPlugin.EXCHANGE;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull ExchangeRecipe recipe) {
        return MoarSignsPlugin.EXCHANGE;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull ExchangeRecipe recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull ExchangeRecipe recipe) {
        return !(recipe.getInputs().isEmpty() || recipe.getOutputs().size() == 1);
    }
}

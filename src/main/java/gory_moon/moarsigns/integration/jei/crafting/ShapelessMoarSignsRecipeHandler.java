package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.ShapelessMoarSignRecipe;
import mezz.jei.api.IGuiHelper;

import javax.annotation.Nonnull;

public class ShapelessMoarSignsRecipeHandler extends MoarSignsRecipeHandlerBase<ShapelessMoarSignRecipe> {

    public ShapelessMoarSignsRecipeHandler(IGuiHelper guiHelper) {
        super(guiHelper);
    }

    @Nonnull
    @Override
    public Class<ShapelessMoarSignRecipe> getRecipeClass() {
        return ShapelessMoarSignRecipe.class;
    }

}

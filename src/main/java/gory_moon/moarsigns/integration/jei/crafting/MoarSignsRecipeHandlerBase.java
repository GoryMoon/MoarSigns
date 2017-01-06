package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.util.IMoarSignsRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class MoarSignsRecipeHandlerBase<T extends IMoarSignsRecipe> implements IRecipeHandler<T> {

    protected final IGuiHelper guiHelper;

    public MoarSignsRecipeHandlerBase(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull T recipe) {
        return VanillaRecipeCategoryUid.CRAFTING;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull T recipe) {
        return new ShapelessMoarSignsRecipeWrapper(recipe, guiHelper);
    }

    @Override
    public boolean isRecipeValid(@Nonnull T recipe) {
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
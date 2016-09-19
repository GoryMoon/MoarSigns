package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe;
import gory_moon.moarsigns.api.ShapelessMoarSignRecipe;
import gory_moon.moarsigns.integration.jei.MoarSignsJeiRecipeHelper;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapelessMoatSignsRecipeWrapper extends BlankRecipeWrapper implements ICraftingRecipeWrapper {

    @Nonnull
    private final ShapelessMoarSignRecipe recipe;

    public ShapelessMoatSignsRecipeWrapper(ShapelessMoarSignRecipe recipe) {
        this.recipe = recipe;
        for (Object input : this.recipe.getInput()) {
            if (input instanceof ItemStack) {
                ItemStack itemStack = (ItemStack) input;
                if (itemStack.stackSize != 1) {
                    itemStack.stackSize = 1;
                }
            }
        }
    }

    @Override
    public List getInputs() {
        ArrayList<Object> inputs = new ArrayList<Object>();
        for (Object o: recipe.getInput()) {
            if (o instanceof ShapedMoarSignRecipe.MatchType || o instanceof MaterialInfo) {
                inputs.add(MoarSignsJeiRecipeHelper.getSigns(o));
            } else {
                inputs.add(o);
            }
        }
        return inputs;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return Collections.singletonList(recipe.getRecipeOutput());
    }
}

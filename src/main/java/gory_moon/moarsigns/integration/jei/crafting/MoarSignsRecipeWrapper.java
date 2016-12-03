package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe.MatchType;
import gory_moon.moarsigns.integration.jei.MoarSignsJeiRecipeHelper;
import gory_moon.moarsigns.integration.jei.MoarSignsPlugin;
import gory_moon.moarsigns.util.IMoarSignsRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoarSignsRecipeWrapper extends BlankRecipeWrapper {

    protected final IMoarSignsRecipe recipe;

    public MoarSignsRecipeWrapper(IMoarSignsRecipe recipe) {
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
    public void getIngredients(IIngredients ingredients) {
        IStackHelper stackHelper = MoarSignsPlugin.jeiHelpers.getStackHelper();
        ArrayList<Object> inputs = new ArrayList<Object>();
        for (Object o: recipe.getInput()) {
            if (o instanceof MatchType || o instanceof MaterialInfo) {
                inputs.add(MoarSignsJeiRecipeHelper.getSigns(o));
            } else {
                inputs.add(o);
            }
        }
        ingredients.setInputLists(ItemStack.class, stackHelper.expandRecipeItemStackInputs(inputs));

        ItemStack recipeOutput = recipe.getRecipeOutput();
        if (recipeOutput != null) {
            ingredients.setOutput(ItemStack.class, recipeOutput);
        }
    }

    @Override
    public List getInputs() {
        ArrayList<Object> inputs = new ArrayList<Object>();
        for (Object o: recipe.getInput()) {
            if (o instanceof MatchType || o instanceof MaterialInfo) {
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

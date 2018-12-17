package gory_moon.moarsigns.integration.jei.exchange;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ExchangeRecipe implements IRecipeWrapper {

    private final ItemStack input;
    private final List<ItemStack> outputs;

    public ExchangeRecipe(ItemStack input, List<ItemStack> outputs) {
        this.input = input;
        this.outputs = outputs;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Collections.singletonList(input));
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

}

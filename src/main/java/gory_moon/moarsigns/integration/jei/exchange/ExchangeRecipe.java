package gory_moon.moarsigns.integration.jei.exchange;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ExchangeRecipe extends BlankRecipeWrapper {

    private final ItemStack input;
    private final List<ItemStack> outputs;

    public ExchangeRecipe(ItemStack input, List<ItemStack> outputs) {
        this.input = input;
        this.outputs = outputs;
    }

    @Override
    public List getInputs() {
        return Collections.singletonList(input);
    }

    @Override
    public List<ItemStack> getOutputs() {
        return outputs;
    }
}

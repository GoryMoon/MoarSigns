package gory_moon.moarsigns.integration.jei.exchange;

import gory_moon.moarsigns.integration.jei.MoarSignsPlugin;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

public class ExchangeGridHelper implements ICraftingGridHelper {

    private final int craftInputSlot;
    private final int craftOutputSlot1;

    public ExchangeGridHelper(int craftInputSlot, int craftOutputSlot1) {
        this.craftInputSlot = craftInputSlot;
        this.craftOutputSlot1 = craftOutputSlot1;
    }

    @Override
    public void setInput(@Nonnull IGuiItemStackGroup guiItemStacks, @Nonnull List input) {
        guiItemStacks.set(craftInputSlot, input);
    }

    @Override
    public void setInput(@Nonnull IGuiItemStackGroup guiItemStacks, @Nonnull List input, int width, int height) {

    }

    @Override
    public void setInputStacks(IGuiItemStackGroup guiItemStacks, List<List<ItemStack>> input) {
        guiItemStacks.set(craftInputSlot, input.get(0));
    }

    @Override
    public void setInputStacks(IGuiItemStackGroup guiItemStacks, List<List<ItemStack>> input, int width, int height) {

    }

    @Override
    public void setOutput(@Nonnull IGuiItemStackGroup guiItemStacks, @Nonnull List<ItemStack> output) {
        for (int i = 0; i < output.size(); i++) {
            Object recipeItem = output.get(i);


            List<ItemStack> itemStacks = MoarSignsPlugin.jeiHelpers.getStackHelper().toItemStackList(recipeItem);
            setOutput(guiItemStacks, i, itemStacks);
        }
    }

    private void setOutput(@Nonnull IGuiItemStackGroup guiItemStacks, int inputIndex, @Nonnull Collection<ItemStack> input) {
        guiItemStacks.set(craftOutputSlot1 + inputIndex, input);
    }
}

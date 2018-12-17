package gory_moon.moarsigns.integration.jei.exchange;

import gory_moon.moarsigns.integration.jei.MoarSignsPlugin;
import gory_moon.moarsigns.lib.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;
import java.util.List;

public class MoarSignsExchangeCategory implements IRecipeCategory<ExchangeRecipe> {

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;

    public MoarSignsExchangeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("moarsigns", "textures/gui/sign_exchange_nei.png");
        background = guiHelper.createDrawable(location, 0, 0, 164, 118);
        localizedName = I18n.translateToLocal("crafting.moarsigns.sign.exchange");
    }

    @Nonnull
    @Override
    public String getUid() {
        return MoarSignsPlugin.EXCHANGE;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getModName() {
        return Reference.NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ExchangeRecipe recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(craftOutputSlot, true, 74, 12);

        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 9; ++x) {
                int index = craftInputSlot1 + x + (y * 9);
                guiItemStacks.init(index, false, 2 + x * 18, 42 + y * 18);
            }
        }

        guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM).size(); i++) {
            Object recipeItem = ingredients.getOutputs(VanillaTypes.ITEM).get(i);


            List<ItemStack> itemStacks = MoarSignsPlugin.jeiHelpers.getStackHelper().toItemStackList(recipeItem);
            guiItemStacks.set(1 + i, itemStacks);
        }
    }
}

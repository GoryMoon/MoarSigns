package gory_moon.moarsigns.integration.jei.exchange;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.integration.jei.MoarSignsPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;

public class MoarSignsExchangeCategory implements IRecipeCategory {

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;
    @Nonnull
    private final ICraftingGridHelper craftingGridHelper;

    public MoarSignsExchangeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("moarsigns", "textures/gui/sign_exchange_nei.png");
        background = guiHelper.createDrawable(location, 0, 0, 164, 118);
        localizedName = I18n.translateToLocal("crafting.moarsigns.sign.exchange");
        craftingGridHelper = new ExchangeGridHelper(0, 1);
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

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(craftOutputSlot, true, 74, 12);

        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 9; ++x) {
                int index = craftInputSlot1 + x + (y * 9);
                guiItemStacks.init(index, false, 2 + x * 18, 42 + y * 18);
            }
        }

        if (recipeWrapper instanceof ExchangeRecipe) {
            ExchangeRecipe wrapper = (ExchangeRecipe) recipeWrapper;
            craftingGridHelper.setInput(guiItemStacks, wrapper.getInputs());
            craftingGridHelper.setOutput(guiItemStacks, wrapper.getOutputs());
        } else {
            MoarSigns.logger.error("RecipeWrapper is not a known crafting wrapper type: {}", recipeWrapper);
        }
    }
}

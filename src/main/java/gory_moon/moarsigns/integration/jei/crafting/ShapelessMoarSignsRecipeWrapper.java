package gory_moon.moarsigns.integration.jei.crafting;

import gory_moon.moarsigns.util.IMoarSignsRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.config.HoverChecker;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ShapelessMoarSignsRecipeWrapper extends MoarSignsRecipeWrapper implements ICraftingRecipeWrapper {

    private static final double shapelessIconScale = 0.5;
    private final IDrawable shapelessIcon;
    private final HoverChecker shapelessIconHoverChecker;
    protected final IMoarSignsRecipe recipe;

    public ShapelessMoarSignsRecipeWrapper(IMoarSignsRecipe recipe, IGuiHelper guiHelper) {
        super(recipe);
        ResourceLocation shapelessIconLocation = new ResourceLocation("jei", "textures/gui/recipeBackground2.png");
        shapelessIcon = guiHelper.createDrawable(shapelessIconLocation, 196, 0, 19, 15);

        int iconBottom = (int) (shapelessIcon.getHeight() * shapelessIconScale);
        int iconLeft = 116 - (int) (shapelessIcon.getWidth() * shapelessIconScale);
        int iconRight = iconLeft + (int) (shapelessIcon.getWidth() * shapelessIconScale);
        shapelessIconHoverChecker = new HoverChecker(0, iconBottom, iconLeft, iconRight, 0);

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
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);

        if (hasMultipleIngredients()) {
            int shapelessIconX = recipeWidth - (int) (shapelessIcon.getWidth() * shapelessIconScale);

            GlStateManager.pushMatrix();
            GlStateManager.scale(shapelessIconScale, shapelessIconScale, 1.0);
            GlStateManager.color(1f, 1f, 1f, 1f);
            shapelessIcon.draw(minecraft, (int) (shapelessIconX / shapelessIconScale), 0);
            GlStateManager.popMatrix();
        }
    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (hasMultipleIngredients() && shapelessIconHoverChecker.checkHover(mouseX, mouseY)) {
            return Collections.singletonList(I18n.translateToLocal("jei.tooltip.shapeless.recipe"));
        }

        return super.getTooltipStrings(mouseX, mouseY);
    }

    private boolean hasMultipleIngredients() {
        return getInputs().size() > 1;
    }

}

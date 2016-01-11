package gory_moon.moarsigns.client.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.MaterialRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.client.interfaces.GuiExchange;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NEIExchangeUsageHandler extends TemplateRecipeHandler {

    private static List<ItemStack> getVariationStacks(ArrayList<SignInfo> infos) {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for (SignInfo info : infos) {
            stacks.add(ModItems.sign.createMoarItemStack(info.material.path + info.itemName, info.isMetal));
        }
        return stacks;
    }

    @Override
    public String getGuiTexture() {
        return "moarsigns:textures/gui/sign_exchange_nei.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("crafting.moarsign.sign.exchange");
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiExchange.class;
    }

    @Override
    public void drawBackground(int recipeIndex) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 164, 118);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient != null && ingredient.getItem() != null && ingredient.getItem() instanceof ItemMoarSign) {
            String texture = ItemMoarSign.getTextureFromNBTFull(ingredient.getTagCompound());
            SignInfo signInfo = SignRegistry.get(texture);

            HashSet<MaterialInfo> materials = MaterialRegistry.get(signInfo.material.materialName);
            if (materials == null || materials.size() <= 1) {
                return;
            }

            ArrayList<SignInfo> signs = SignRegistry.getSignInfoFromMaterials(materials);
            if (signs == null || signs.size() <= 1) {
                return;
            }

            List<ItemStack> variations = getVariationStacks(signs);
            addCached(variations, ingredient);
        }
    }

    private void addCached(List<ItemStack> variations, ItemStack base) {
        this.arecipes.add(new CachedExchangeRecipe(variations, base));
    }

    public class CachedExchangeRecipe extends CachedRecipe {

        private List<PositionedStack> input = new ArrayList<PositionedStack>();
        private List<PositionedStack> outputs = new ArrayList<PositionedStack>();

        public CachedExchangeRecipe(List<ItemStack> variations, ItemStack base) {
            PositionedStack pStack = new PositionedStack(base != null ? base : variations, 75, 13);
            pStack.setMaxSize(1);
            this.input.add(pStack);

            int row = 0;
            int col = 0;
            for (ItemStack v : variations) {
                this.outputs.add(new PositionedStack(v, 3 + 18 * col, 43 + 18 * row));

                col++;
                if (col > 9) {
                    col = 0;
                    row++;
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, this.input);
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return outputs;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }

}

package gory_moon.moarsigns.integration.nei;

import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapedRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe.MatchType;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.items.ItemMoarSign;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static gory_moon.moarsigns.integration.nei.NEIMoarSignConfig.moarSigns;

public class NEIShapedMoarSignHandler extends ShapedRecipeHandler {
    @Override
    @SuppressWarnings("unchecked")
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting") && getClass() == NEIShapedMoarSignHandler.class) {
            List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
            for (IRecipe iRecipe : allrecipes) {
                CachedMoarSignRecipe recipe = null;
                if (iRecipe instanceof ShapedMoarSignRecipe)
                    recipe = forgeShapedRecipe((ShapedMoarSignRecipe) iRecipe);

                if (recipe == null)
                    continue;

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadCraftingRecipes(ItemStack result) {
        List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe iRecipe : allrecipes) {
            CachedMoarSignRecipe recipe = null;
            if (iRecipe instanceof ShapedMoarSignRecipe)
                recipe = forgeShapedRecipe((ShapedMoarSignRecipe) iRecipe);

            if (recipe == null || !NEIServerUtils.areStacksSameTypeCrafting(recipe.result.item, result))
                continue;

            if (((ShapedMoarSignRecipe) iRecipe).isNeiNBTDifferent() && !ItemStack.areItemStackTagsEqual(recipe.result.item, result))
                continue;

            recipe.computeVisuals();
            arecipes.add(recipe);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadUsageRecipes(ItemStack ingredient) {
        for (IRecipe iRecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList()) {
            CachedMoarSignRecipe recipe = null;
            if (iRecipe instanceof ShapedMoarSignRecipe)
                recipe = forgeShapedRecipe((ShapedMoarSignRecipe) iRecipe);

            if (recipe == null || !recipe.contains(recipe.ingredients, ingredient))
                continue;

            recipe.computeVisuals();
            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }

        }
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        for (ItemStack stack : ItemList.items) {
            if (stack != null && stack.getItem() instanceof ItemMoarSign) {
                moarSigns.add(stack);
            }
        }

        return super.newInstance();
    }

    private CachedMoarSignRecipe forgeShapedRecipe(ShapedMoarSignRecipe recipe) {
        int width;
        int height;

        try {
            width = recipe.width;
            height = recipe.height;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Object[] items = recipe.getInput();
        for (Object item : items)
            if (item instanceof List && ((List<?>) item).isEmpty())
                return null;

        return new CachedMoarSignRecipe(width, height, items, recipe.getRecipeOutput());
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("crafting.moarsigns.sign.shaped");
    }

    public class CachedMoarSignRecipe extends CachedShapedRecipe {

        public CachedMoarSignRecipe(int width, int height, Object[] items, ItemStack out) {
            super(width, height, items, out);
        }

        @Override
        public void setIngredients(int width, int height, Object[] items) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (items[y * width + x] == null)
                        continue;
                    Object o = items[y * width + x];
                    if (o instanceof ItemStack) {
                        PositionedStack stack = new PositionedStack(items[y * width + x], 25 + x * 18, 6 + y * 18, false);
                        stack.setMaxSize(1);
                        ingredients.add(stack);
                    } else if (o instanceof MatchType || o instanceof MaterialInfo) {
                        ArrayList<ItemStack> signs = new ArrayList<ItemStack>();
                        for (ItemStack stack : moarSigns) {
                            SignInfo info = ItemMoarSign.getInfo(stack.getTagCompound());

                            if (o instanceof MatchType) {
                                if (o == MatchType.ALL) {
                                    signs.add(stack);
                                } else if (o == MatchType.METAL && info.isMetal) {
                                    signs.add(stack);
                                } else if (o == MatchType.WOOD && !info.isMetal) {
                                    signs.add(stack);
                                }
                            } else if (((MaterialInfo) o).materialName.equals(info.material.materialName)) {
                                signs.add(stack);
                            }
                        }

                        PositionedStack stack = new PositionedStack(signs, 25 + x * 18, 6 + y * 18, false);
                        stack.setMaxSize(1);
                        ingredients.add(stack);
                    } else if (o instanceof List) {
                        PositionedStack stack = new PositionedStack(o, 25 + x * 18, 6 + y * 18, false);
                        stack.setMaxSize(1);
                        ingredients.add(stack);
                    }
                }
            }
        }

        @Override
        public boolean contains(Collection<PositionedStack> ingredients, ItemStack ingredient) {
            Iterator i$ = ingredients.iterator();

            PositionedStack stack;
            do {
                if (!i$.hasNext()) {
                    return false;
                }

                stack = (PositionedStack) i$.next();
            } while (!stackContains(stack, ingredient));

            return true;
        }

        private boolean stackContains(PositionedStack stack, ItemStack ingredient) {
            ItemStack[] arr$ = stack.items;

            for (ItemStack item : arr$) {
                if (ItemStack.areItemStackTagsEqual(item, ingredient) && ingredient.isItemEqual(item)) {
                    return true;
                }
            }

            return false;
        }
    }
}

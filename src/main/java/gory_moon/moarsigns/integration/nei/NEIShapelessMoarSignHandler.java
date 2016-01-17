package gory_moon.moarsigns.integration.nei;

import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe.MatchType;
import gory_moon.moarsigns.api.ShapelessMoarSignRecipe;
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

public class NEIShapelessMoarSignHandler extends ShapelessRecipeHandler {
    @Override
    @SuppressWarnings("unchecked")
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting") && getClass() == NEIShapelessMoarSignHandler.class) {
            List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
            for (IRecipe iRecipe : allrecipes) {
                CachedMoarSignRecipe recipe = null;
                if (iRecipe instanceof ShapelessMoarSignRecipe)
                    recipe = forgeShapelessRecipe((ShapelessMoarSignRecipe) iRecipe);

                if (recipe == null)
                    continue;

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
            if (NEIServerUtils.areStacksSameTypeCrafting(iRecipe.getRecipeOutput(), result)) {
                CachedMoarSignRecipe recipe = null;
                if (iRecipe instanceof ShapelessMoarSignRecipe)
                    recipe = forgeShapelessRecipe((ShapelessMoarSignRecipe) iRecipe);

                if (recipe == null)
                    continue;

                if (((ShapelessMoarSignRecipe) iRecipe).isNeiNBTDifferent() && !ItemStack.areItemStackTagsEqual(recipe.result.item, result))
                    continue;

                arecipes.add(recipe);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadUsageRecipes(ItemStack ingredient) {
        List<IRecipe> allIRecipes = (List<IRecipe>) CraftingManager.getInstance().getRecipeList();
        for (IRecipe iRecipe : allIRecipes) {
            CachedMoarSignRecipe recipe = null;
            if (iRecipe instanceof ShapelessMoarSignRecipe)
                recipe = forgeShapelessRecipe((ShapelessMoarSignRecipe) iRecipe);

            if (recipe == null)
                continue;

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

    private CachedMoarSignRecipe forgeShapelessRecipe(ShapelessMoarSignRecipe recipe) {
        ArrayList<Object> items = recipe.getInput();

        for (Object item : items)
            if (item instanceof List && ((List<?>) item).isEmpty())
                return null;

        return new CachedMoarSignRecipe(items, recipe.getRecipeOutput());
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("crafting.moarsigns.sign.shapeless");
    }

    public class CachedMoarSignRecipe extends CachedShapelessRecipe {

        public CachedMoarSignRecipe(ArrayList<Object> items, ItemStack recipeOutput) {
            super(items, recipeOutput);
        }

        @Override
        public void setIngredients(List<?> items) {
            ingredients.clear();
            for (int ingred = 0; ingred < items.size(); ingred++) {
                Object o = items.get(ingred);
                if (o instanceof ItemStack) {
                    PositionedStack stack = new PositionedStack(items.get(ingred), 25 + stackorder[ingred][0] * 18, 6 + stackorder[ingred][1] * 18);
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

                    PositionedStack stack = new PositionedStack(signs, 25 + stackorder[ingred][0] * 18, 6 + stackorder[ingred][1] * 18);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
                } else if (o instanceof List) {
                    PositionedStack stack = new PositionedStack(o, 25 + stackorder[ingred][0] * 18, 6 + stackorder[ingred][1] * 18);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
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

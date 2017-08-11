package gory_moon.moarsigns.api;

import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.util.IMoarSignsRecipe;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShapedMoarSignRecipe implements IMoarSignsRecipe {

    //Added in for future ease of change, but hard coded for now.
    private static final int MAX_CRAFT_GRID_WIDTH = 3;
    private static final int MAX_CRAFT_GRID_HEIGHT = 3;
    public int width = 0;
    public int height = 0;
    private ItemStack output = null;
    private Object[] input = null;
    private boolean mirrored = true;
    private boolean neiNBTDifferent = false;

    public ShapedMoarSignRecipe(Block result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public ShapedMoarSignRecipe(Item result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public ShapedMoarSignRecipe(ItemStack result, Object... recipe) {
        output = result.copy();

        String shape = "";
        int idx = 0;

        if (recipe[idx] instanceof Boolean) {
            mirrored = (Boolean) recipe[idx];
            if (recipe[idx + 1] instanceof Object[]) {
                recipe = (Object[]) recipe[idx + 1];
            } else {
                idx = 1;
            }
        }

        if (recipe[idx] instanceof Boolean) {
            neiNBTDifferent = (Boolean) recipe[idx];
            if (recipe[idx + 1] instanceof Object[]) {
                recipe = (Object[]) recipe[idx + 1];
                idx = 0;
            } else {
                idx = 2;
            }
        }

        if (recipe[idx] instanceof String[]) {
            String[] parts = ((String[]) recipe[idx++]);

            for (String s : parts) {
                width = s.length();
                shape += s;
            }

            height = parts.length;
        } else {
            while (recipe[idx] instanceof String) {
                String s = (String) recipe[idx++];
                shape += s;
                width = s.length();
                height++;
            }
        }

        if (width * height != shape.length()) {
            String ret = "Invalid shaped ore recipe: ";
            for (Object tmp : recipe) {
                ret += tmp + ", ";
            }
            ret += output;
            throw new RuntimeException(ret);
        }

        HashMap<Character, Object> itemMap = new HashMap<Character, Object>();

        for (; idx < recipe.length; idx += 2) {
            Character chr = (Character) recipe[idx];
            Object in = recipe[idx + 1];

            if (in instanceof MatchType) {
                itemMap.put(chr, (MatchType) in);
            } else if (in instanceof MaterialInfo) {
                itemMap.put(chr, (MaterialInfo) in);
            } else if (in instanceof ItemStack) {
                itemMap.put(chr, ((ItemStack) in).copy());
            } else if (in instanceof Item) {
                itemMap.put(chr, new ItemStack((Item) in));
            } else if (in instanceof Block) {
                itemMap.put(chr, new ItemStack((Block) in, 1, OreDictionary.WILDCARD_VALUE));
            } else if (in instanceof String) {
                itemMap.put(chr, OreDictionary.getOres((String) in));
            } else {
                String ret = "Invalid shaped ore recipe: ";
                for (Object tmp : recipe) {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new InvalidRecipeException(ret);
            }
        }

        input = new Object[width * height];
        int x = 0;
        for (char chr : shape.toCharArray()) {
            input[x++] = itemMap.get(chr);
        }
    }

    public ShapedMoarSignRecipe(IRecipe recipe, Map<ItemStack, Object> replacements) {
        output = recipe.getRecipeOutput();

        Object[] items = null;
        if (recipe instanceof ShapedRecipes) {
            ShapedRecipes r = (ShapedRecipes) recipe;
            width = r.recipeWidth;
            height = r.recipeHeight;
            input = new Object[r.recipeItems.length];
            items = r.recipeItems;
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe r = (ShapedOreRecipe) recipe;
            width = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, (ShapedOreRecipe) recipe, "width");
            height = ObfuscationReflectionHelper.getPrivateValue(ShapedOreRecipe.class, (ShapedOreRecipe) recipe, "height");
            input = new Object[r.getRecipeSize()];
            items = r.getInput();
        }

        if (items != null) {
            for (int i = 0; i < input.length; i++) {
                Object ingred = items[i];
                input[i] = ingred;

                if (ingred == null || !(ingred instanceof ItemStack))
                    continue;

                for (Map.Entry<ItemStack, Object> replace : replacements.entrySet()) {
                    if (OreDictionary.itemMatches(replace.getKey(), (ItemStack) ingred, true)) {
                        if (replace.getValue() instanceof String) {
                            input[i] = OreDictionary.getOres(String.valueOf(replace.getValue()));
                        } else if (replace.getValue() instanceof MatchType || replace.getValue() instanceof MaterialInfo) {
                            input[i] = replace.getValue();
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        return output.copy();
    }

    @Override
    public int getRecipeSize() {
        return input.length;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {

        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++) {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y) {
                if (checkMatch(inv, x, y, false)) {
                    return true;
                }

                if (mirrored && checkMatch(inv, x, y, true)) {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {

        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {

                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
                    if (mirror) {
                        target = input[width - subX - 1 + subY * width];
                    } else {
                        target = input[subX + subY * width];
                    }
                }

                ItemStack slot = inv.getStackInRowAndColumn(x, y);
                //If target is MatchType or MaterialInfo, check what's required by the recipe
                if (target instanceof MatchType || target instanceof MaterialInfo) {
                    if (slot != null && slot.getItem() instanceof ItemMoarSign) {
                        SignInfo info = ItemMoarSign.getInfo(slot.getTagCompound());
                        if (target instanceof MatchType) {
                            switch ((MatchType) target) {
                                case ALL:
                                    continue;
                                case METAL:
                                    if (info.isMetal) {
                                        continue;
                                    }
                                    return false;
                                case WOOD:
                                    if (!info.isMetal) {
                                        continue;
                                    }
                                    return false;
                            }
                        } else {
                            if (!info.material.materialName.equals(((MaterialInfo) target).materialName)) {
                                return false;
                            }
                        }
                    } else
                        return false;
                } else if (target instanceof ItemStack) {
                    if (slot != null && slot.getItem() instanceof ItemMoarSign && ((ItemStack) target).getItem() instanceof ItemMoarSign && (!ItemStack.areItemStackTagsEqual(slot, (ItemStack) target))) {
                        return false;
                    } else if (!OreDictionary.itemMatches((ItemStack) target, slot, false)) {
                        return false;
                    }
                } else if (target instanceof List) {
                    boolean matched = false;

                    Iterator<ItemStack> itr = ((List<ItemStack>) target).iterator();
                    while (itr.hasNext() && !matched) {
                        matched = OreDictionary.itemMatches(itr.next(), slot, false);
                    }

                    if (!matched) {
                        return false;
                    }
                } else if (target == null && slot != null || target != null && slot == null) {
                    return false;
                }
            }
        }

        return true;
    }

    public ShapedMoarSignRecipe setMirrored(boolean mirror) {
        mirrored = mirror;
        return this;
    }

    public IMoarSignsRecipe setNEINBTDifferent(boolean nbtDiff) {
        neiNBTDifferent = nbtDiff;
        return this;
    }

    public boolean isNeiNBTDifferent() {
        return neiNBTDifferent;
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     *
     * @return The recipes input vales.
     */
    public Object[] getInput() {
        return this.input;
    }

    public enum MatchType {
        ALL, METAL, WOOD;

        public static MatchType getEnum(String value) {
            if ("ALL".equals(value)) {
                return ALL;
            } else if ("METAL".equals(value)) {
                return METAL;
            } else if ("WOOD".equals(value)) {
                return WOOD;
            }
            return null;
        }
    }
}

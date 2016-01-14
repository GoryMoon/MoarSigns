package gory_moon.moarsigns.api;

import gory_moon.moarsigns.api.ShapedMoarSignRecipe.MatchType;
import gory_moon.moarsigns.items.ItemMoarSign;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ShapelessMoarSignRecipe implements IRecipe {

    private ItemStack output = null;
    private ArrayList<Object> input = new ArrayList<Object>();
    private boolean neiNBTDifferent = false;

    public ShapelessMoarSignRecipe(Block result, boolean neiNBTDifferent, Object... recipe) {
        this(new ItemStack(result), neiNBTDifferent, recipe);
    }

    public ShapelessMoarSignRecipe(Item result, boolean neiNBTDifferent, Object... recipe) {
        this(new ItemStack(result), neiNBTDifferent, recipe);
    }

    public ShapelessMoarSignRecipe(ItemStack result, boolean neiNBTDifferent, Object[] recipe) {

        output = result.copy();
        this.neiNBTDifferent = neiNBTDifferent;
        for (Object in : recipe) {
            if (in instanceof MatchType) { //If the item is an instanceof IBloodOrb then save the level of the orb
                input.add((MatchType) in);
            } else if (in instanceof MaterialInfo) {
                input.add((MaterialInfo) in);
            } else if (in instanceof ItemStack) {
                input.add(((ItemStack) in).copy());
            } else if (in instanceof Item) {
                input.add(new ItemStack((Item) in));
            } else if (in instanceof Block) {
                input.add(new ItemStack((Block) in));
            } else if (in instanceof String) {
                input.add(OreDictionary.getOres((String) in));
            } else {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp : recipe) {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    public ShapelessMoarSignRecipe(IRecipe recipe, Map<ItemStack, Object> replacements) {
        output = recipe.getRecipeOutput();

        for (Object ingred : (recipe instanceof ShapelessRecipes ? ((List<Object>) ((ShapelessRecipes) recipe).recipeItems) : ((ShapelessOreRecipe) recipe).getInput())) {
            Object finalObj = ingred;
            for (Entry<ItemStack, Object> replace : replacements.entrySet()) {
                if (ingred instanceof ItemStack && OreDictionary.itemMatches(replace.getKey(), (ItemStack) ingred, false)) {
                    if (replace.getValue() instanceof String) {
                        finalObj = OreDictionary.getOres(String.valueOf(replace.getValue()));
                    } else if (replace.getValue() instanceof MatchType || replace.getValue() instanceof MaterialInfo) {
                        finalObj = replace.getValue();
                    }
                    break;
                }
            }
            input.add(finalObj);
        }
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
        return output.copy();
    }

    @Override
    public int getRecipeSize() {
        return input.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(InventoryCrafting var1, World world) {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for (int x = 0; x < var1.getSizeInventory(); x++) {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != null) {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while (req.hasNext()) {
                    boolean match = false;

                    Object next = req.next();

                    //If target is MatchType or MaterialInfo, check what's required by the recipe
                    if (next instanceof MatchType || next instanceof MaterialInfo) {
                        if (slot.getItem() instanceof ItemMoarSign) {
                            SignInfo info = ItemMoarSign.getInfo(slot.getTagCompound());
                            if (next instanceof MatchType) {
                                switch ((MatchType) next) {
                                    case ALL:
                                        match = true;
                                        break;
                                    case METAL:
                                        match = info.isMetal;
                                        break;
                                    case WOOD:
                                        match = !info.isMetal;
                                }
                            } else {
                                match = info.material.materialName.equals(((MaterialInfo) next).materialName);
                            }
                        }
                    } else if (next instanceof ItemStack) {
                        match = OreDictionary.itemMatches((ItemStack) next, slot, false);
                    } else if (next instanceof ArrayList) {
                        Iterator<ItemStack> itr = ((ArrayList<ItemStack>) next).iterator();
                        while (itr.hasNext() && !match) {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if (match) {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe) {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    public ArrayList<Object> getInput() {
        return this.input;
    }

    public ShapelessMoarSignRecipe setNEINBTDifferent(boolean nbtDiff) {
        neiNBTDifferent = nbtDiff;
        return this;
    }

    public boolean isNeiNBTDifferent() {
        return neiNBTDifferent;
    }

}

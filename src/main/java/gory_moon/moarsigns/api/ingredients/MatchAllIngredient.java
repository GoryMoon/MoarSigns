package gory_moon.moarsigns.api.ingredients;

import gory_moon.moarsigns.items.ModItems;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class MatchAllIngredient extends Ingredient {

    public ItemStack[] matchingStacksExploded;
    public IntList matchingStacksPacked;

    protected MatchAllIngredient() {
        super(new ItemStack(ModItems.SIGN, 1, OreDictionary.WILDCARD_VALUE));
    }

    protected MatchAllIngredient(ItemStack stack) {
        super(stack);
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        if (matchingStacksExploded == null || matchingStacksExploded.length <= 0) {
            NonNullList<ItemStack> lst = NonNullList.create();
            for (ItemStack s : this.matchingStacks) {
                if (s.getMetadata() == net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE)
                    s.getItem().getSubItems(net.minecraft.creativetab.CreativeTabs.SEARCH, lst);
                else
                    lst.add(s);
            }
            this.matchingStacksExploded = lst.toArray(new ItemStack[lst.size()]);
        }
        return this.matchingStacksExploded;
    }

    @Override
    public IntList getValidItemStacksPacked() {
        if (this.matchingStacksPacked == null)
        {
            getMatchingStacks();
            this.matchingStacksPacked = new IntArrayList(this.matchingStacksExploded.length);

            for (ItemStack itemstack : this.matchingStacksExploded)
            {
                this.matchingStacksPacked.add(RecipeItemHelper.pack(itemstack));
            }

            this.matchingStacksPacked.sort(IntComparators.NATURAL_COMPARATOR);
        }
        return this.matchingStacksPacked;
    }

    @Override
    protected void invalidate() {
        this.matchingStacksPacked = null;
    }

    @Override
    public boolean isSimple() {
        return true;
    }
}

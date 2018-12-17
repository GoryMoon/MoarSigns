package gory_moon.moarsigns.integration.jei;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.integration.jei.exchange.ExchangeRecipeMaker;
import gory_moon.moarsigns.integration.jei.exchange.MoarSignsExchangeCategory;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;

@JEIPlugin
public class MoarSignsPlugin implements IModPlugin {

    public static ArrayList<ItemStack> moarSigns = new ArrayList<>();
    public static final String EXCHANGE = "moarsigns.exchange";

    public static IJeiHelpers jeiHelpers;

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        for (ItemStack stack : registry.getIngredientRegistry().getAllIngredients(VanillaTypes.ITEM)) {
            if (stack != null && stack.getItem() instanceof ItemMoarSign) {
                moarSigns.add(stack);
            }
        }

        IIngredientBlacklist blacklist = jeiHelpers.getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(ModItems.DEBUG));

        registry.addRecipes(ExchangeRecipeMaker.getExchangeRecipes(), EXCHANGE);
        registry.addRecipeCatalyst(new ItemStack(ModItems.SIGN_TOOLBOX, 1, 4), EXCHANGE);

        MoarSigns.logger.info("Loaded JEI Integration");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new MoarSignsExchangeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(ModItems.SIGN, itemStack -> {
            if(itemStack.getMetadata() != OreDictionary.WILDCARD_VALUE) {
                NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
                if (nbtTagCompound != null && !nbtTagCompound.isEmpty()) {
                    return nbtTagCompound.toString();
                }
            }
            return null;
        });
        subtypeRegistry.registerSubtypeInterpreter(ModItems.SIGN_TOOLBOX, itemStack -> "");
    }
}

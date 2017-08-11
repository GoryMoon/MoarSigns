package gory_moon.moarsigns.integration.jei;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe;
import gory_moon.moarsigns.api.ShapelessMoarSignRecipe;
import gory_moon.moarsigns.integration.jei.crafting.MoarSignsRecipeWrapper;
import gory_moon.moarsigns.integration.jei.crafting.ShapedMoarSignsRecipeWrapper;
import gory_moon.moarsigns.integration.jei.exchange.ExchangeRecipeMaker;
import gory_moon.moarsigns.integration.jei.exchange.MoarSignsExchangeCategory;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

@JEIPlugin
public class MoarSignsPlugin extends BlankModPlugin {

    public static ArrayList<ItemStack> moarSigns = new ArrayList<ItemStack>();
    public static final String EXCHANGE = "moarsigns.exchange";

    public static IJeiHelpers jeiHelpers;

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        registry.handleRecipes(ShapedMoarSignRecipe.class, ShapedMoarSignsRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);
        registry.handleRecipes(ShapelessMoarSignRecipe.class, MoarSignsRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);

        for (ItemStack stack : registry.getIngredientRegistry().getIngredients(ItemStack.class)) {
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
        subtypeRegistry.registerSubtypeInterpreter(ModItems.SIGN, new ISubtypeRegistry.ISubtypeInterpreter() {
            @Nullable
            @Override
            public String getSubtypeInfo(ItemStack itemStack) {
                if(itemStack.getMetadata() != OreDictionary.WILDCARD_VALUE) {
                    NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
                    if (nbtTagCompound != null && !nbtTagCompound.hasNoTags()) {
                        return nbtTagCompound.toString();
                    }
                }
                return null;
            }
        });
        subtypeRegistry.registerSubtypeInterpreter(ModItems.SIGN_TOOLBOX, new ISubtypeRegistry.ISubtypeInterpreter() {
            @Nullable
            @Override
            public String getSubtypeInfo(ItemStack itemStack) {
                return "";
            }
        });
    }
}

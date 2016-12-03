package gory_moon.moarsigns.integration.jei;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.integration.jei.crafting.ShapedMoarSignsRecipeHandler;
import gory_moon.moarsigns.integration.jei.crafting.ShapelessMoarSignsRecipeHandler;
import gory_moon.moarsigns.integration.jei.exchange.ExchangeRecipeHandler;
import gory_moon.moarsigns.integration.jei.exchange.ExchangeRecipeMaker;
import gory_moon.moarsigns.integration.jei.exchange.MoarSignsExchangeCategory;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;

@JEIPlugin
public class MoarSignsPlugin extends BlankModPlugin {

    public static ArrayList<ItemStack> moarSigns = new ArrayList<ItemStack>();
    public static final String EXCHANGE = "moarsigns.exchange";

    public static IJeiHelpers jeiHelpers;

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(
                new MoarSignsExchangeCategory(guiHelper)
        );

        registry.addRecipeHandlers(
                new ShapedMoarSignsRecipeHandler(),
                new ShapelessMoarSignsRecipeHandler(),
                new ExchangeRecipeHandler()
        );

        for (ItemStack stack : registry.getIngredientRegistry().getIngredients(ItemStack.class)) {
            if (stack != null && stack.getItem() instanceof ItemMoarSign) {
                moarSigns.add(stack);
            }
        }

        IItemBlacklist blacklist = jeiHelpers.getItemBlacklist();
        blacklist.addItemToBlacklist(new ItemStack(ModItems.DEBUG));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signStandingMetal));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signStandingWood));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signWallMetal));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signWallWood));

        registry.addRecipes(ExchangeRecipeMaker.getExchangeRecipes());
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.SIGN_TOOLBOX, 1, 4), new String[]{EXCHANGE});

        MoarSigns.logger.info("Loaded JEI Integration");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(
                ModItems.SIGN
        );
    }
}

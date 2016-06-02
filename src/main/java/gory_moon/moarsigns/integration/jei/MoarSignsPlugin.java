package gory_moon.moarsigns.integration.jei;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.integration.jei.crafting.MoarSignCraftingRecipeCategory;
import gory_moon.moarsigns.integration.jei.crafting.ShapedMoarSignsRecipeHandler;
import gory_moon.moarsigns.integration.jei.crafting.ShapelessMoarSignsRecipeHandler;
import gory_moon.moarsigns.integration.jei.exchange.ExchangeRecipeHandler;
import gory_moon.moarsigns.integration.jei.exchange.ExchangeRecipeMaker;
import gory_moon.moarsigns.integration.jei.exchange.MoarSignsExchangeCategory;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import mezz.jei.api.*;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;

@JEIPlugin
public class MoarSignsPlugin implements IModPlugin {

    public static ArrayList<ItemStack> moarSigns = new ArrayList<ItemStack>();
    public static final String CRAFTING = "moarsigns.crafting";
    public static final String EXCHANGE = "moarsigns.exchange";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(
                new MoarSignCraftingRecipeCategory(guiHelper),
                new MoarSignsExchangeCategory(guiHelper)
        );

        registry.addRecipeHandlers(
                new ShapedMoarSignsRecipeHandler(),
                new ShapelessMoarSignsRecipeHandler(),
                new ExchangeRecipeHandler()
        );

        for (ItemStack stack : registry.getItemRegistry().getItemList()) {
            if (stack != null && stack.getItem() instanceof ItemMoarSign) {
                moarSigns.add(stack);
            }
        }
        IItemBlacklist blacklist = jeiHelpers.getItemBlacklist();
        blacklist.addItemToBlacklist(new ItemStack(ModItems.debug));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signStandingMetal));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signStandingWood));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signWallMetal));
        blacklist.addItemToBlacklist(new ItemStack(Blocks.signWallWood));

        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(ContainerWorkbench.class, MoarSignsPlugin.CRAFTING, 1, 9, 10, 36);

        registry.addRecipes(ExchangeRecipeMaker.getExchangeRecipes());
        registry.addRecipeCategoryCraftingItem(new ItemStack(net.minecraft.init.Blocks.CRAFTING_TABLE), new String[]{CRAFTING});
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.signToolbox, 1, 4), new String[]{EXCHANGE});

        MoarSigns.logger.info("Loaded JEI Integration");
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

    }
}

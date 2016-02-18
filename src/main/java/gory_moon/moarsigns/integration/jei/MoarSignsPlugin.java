package gory_moon.moarsigns.integration.jei;

import gory_moon.moarsigns.MoarSigns;
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

import java.util.ArrayList;

@JEIPlugin
public class MoarSignsPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelpers;

    public static ArrayList<ItemStack> moarSigns = new ArrayList<ItemStack>();
    public static final String CRAFTING = "moarsigns.crafting";
    public static final String EXCHANGE = "moarsigns.exchange";

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
        MoarSignsPlugin.jeiHelpers = jeiHelpers;
        IItemBlacklist blacklist = jeiHelpers.getItemBlacklist();
        blacklist.addItemToBlacklist(new ItemStack(ModItems.debug));
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
        for (ItemStack stack : itemRegistry.getItemList()) {
            if (stack != null && stack.getItem() instanceof ItemMoarSign) {
                moarSigns.add(stack);
            }
        }
    }

    @Override
    public void register(IModRegistry registry) {
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

        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(ContainerWorkbench.class, MoarSignsPlugin.CRAFTING, 1, 9, 10, 36);

        registry.addRecipes(ExchangeRecipeMaker.getExchangeRecipes());

        MoarSigns.logger.info("Loaded JEI Integration");
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}

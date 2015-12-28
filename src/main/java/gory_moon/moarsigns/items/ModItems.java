package gory_moon.moarsigns.items;

import cpw.mods.fml.common.registry.GameRegistry;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.ShapedMoarSignRecipe;
import gory_moon.moarsigns.api.ShapelessMoarSignRecipe;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Info;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static ItemMoarSign sign;
    public static ItemDebug debug;
    public static ItemNugget nugget;
    public static ItemSignToolbox signToolbox;

    public static void init() {

        sign = new ItemMoarSign();
        debug = new ItemDebug();
        nugget = new ItemNugget();
        signToolbox = new ItemSignToolbox();

        GameRegistry.registerItem(sign, Info.SIGN_ITEM_KEY);
        GameRegistry.registerItem(debug, Info.DEBUG_ITEM_KEY);
        GameRegistry.registerItem(nugget, Info.NUGGET_ITEM_KEY);
        GameRegistry.registerItem(signToolbox, Info.SIGN_TOOLBOX_ITEM_KEY);
    }

    public static void registerRecipes() {

        RecipeSorter.register("moarsigns:shaped", ShapedMoarSignRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
        RecipeSorter.register("moarsigns:shapeless", ShapelessMoarSignRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        removeRecipesWithResult(new ItemStack(net.minecraft.init.Items.sign, 3));

        List<SignInfo> signRegistry = SignRegistry.getActivatedSignRegistry();

        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        sign.getSubItemStacks(list);

        ItemStack generalSign = null;

        Container dummyContainer = new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer entityplayer) {
                return false;
            }

            @Override
            public void onCraftMatrixChanged(IInventory par1IInventory) {
            }
        };
        InventoryCrafting crafting = new InventoryCrafting(dummyContainer, 3, 3);

        for (ItemStack stack : list) {

            ItemMoarSign sign = (ItemMoarSign) stack.getItem();
            String texture = sign.getTextureFromNBTFull(stack.getTagCompound());
            boolean isMetal = stack.getItemDamage() == 1;

            stack.stackSize = 3;

            if (texture.equals("oak_sign")) {
                generalSign = stack;
                continue;
            }

            for (SignInfo s : signRegistry) {
                if ((s.material.path + s.itemName).equals(texture)) {

                    ItemStack mat = s.material.material;
                    if (mat != null && mat.getItem() != null) {
                        if (isMetal) {
                            mat.stackSize = 1;
                            if (mat.getItem() instanceof ItemBlock) {
                                crafting.setInventorySlotContents(0, mat);
                                mat = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
                            }
                            ItemStack recNugget = null;
                            mat.stackSize = 1;

                            if (!s.material.gotNugget) {
                                String unlocName = mat.getUnlocalizedName();
                                for (int i = 0; i < Info.NUGGET_INGOT_UNLOCS.length; i++) {

                                    if (Info.NUGGET_INGOT_UNLOCS[i].equals(unlocName)) {
                                        nugget.needed[i] = true;
                                        recNugget = new ItemStack(nugget, 9, i);
                                        OreDictionary.registerOre(Info.NUGGET_ORE_DICTIONARY[i], recNugget.copy());

                                        GameRegistry.addShapelessRecipe(recNugget.copy(), mat);
                                        GameRegistry.addShapedRecipe(mat, "xxx", "xxx", "xxx", 'x', recNugget.copy());
                                        break;
                                    }
                                }
                            } else {
                                crafting.setInventorySlotContents(0, mat);
                                recNugget = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
                            }

                            if (recNugget != null && recNugget.getItem() != null) {
                                ItemStack stack1 = stack.copy();
                                stack1.stackSize = 1;
                                if (recNugget.getUnlocalizedName().equals("item.moarsign." + nugget.nuggets[0]))
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', "diamondNugget", '/', Items.stick));
                                else if (recNugget.getUnlocalizedName().equals("item.moarsign." + nugget.nuggets[1]))
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', "nuggetIron", '/', Items.stick));
                                else
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', recNugget, '/', Items.stick));
                            }

                            stack.stackSize = 9;
                        }
                        GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack, true, true, "XXX", "XXX", " / ", 'X', mat, '/', Items.stick));
                    }
                    break;
                }
            }
        }

        GameRegistry.addRecipe(new ShapedMoarSignRecipe(generalSign, true, true, "###", "###", " X ", '#', "plankWood", 'X', Items.stick));
        GameRegistry.addRecipe(new ShapedMoarSignRecipe(signToolbox, "rxr", "xsx", "rxr", 'x', "ingotIron", 's', ShapedMoarSignRecipe.MatchType.ALL, 'r', "dyeRed"));

        //TODO replace shaped, shapeless and forge versions of recipes with signs, example: BiblioCraft
        /*
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
        for (int scan = 0; scan < recipes.size(); scan++) {
            IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                MoarSigns.logger.info("Replacing Recipe: " + recipes.get(scan) + " -> " + recipeResult);
                recipes.remove(scan);
            }
        }*/
    }


    private static void removeRecipesWithResult(ItemStack resultItem) {
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();

        for (int scan = 0; scan < recipes.size(); scan++) {
            IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                MoarSigns.logger.debug("Removing Recipe: " + recipes.get(scan) + " -> " + recipeResult);
                recipes.remove(scan);
            }
        }
    }

}

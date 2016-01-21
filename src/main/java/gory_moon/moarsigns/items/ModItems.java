package gory_moon.moarsigns.items;

import com.google.common.collect.Maps;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModItems {

    public static ItemMoarSign sign;
    public static ItemDebug debug;
    public static ItemNugget nugget;
    public static ItemSignToolbox signToolbox;

    public static boolean replaceRecipes = true;

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
                return true;
            }

            @Override
            public void onCraftMatrixChanged(IInventory par1IInventory) {
            }
        };
        InventoryCrafting crafting = new InventoryCrafting(dummyContainer, 2, 2);

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
                                for (int i = 0; i < NuggetRegistry.size(); i++) {

                                    if (NuggetRegistry.getIngotName(i).equals(unlocName)) {
                                        NuggetRegistry.setNeeded(i, true);
                                        recNugget = new ItemStack(nugget, 9, i);
                                        OreDictionary.registerOre(NuggetRegistry.getOreName(i), recNugget.copy());

                                        GameRegistry.addShapelessRecipe(recNugget.copy(), mat);
                                        GameRegistry.addRecipe(new ShapedOreRecipe(mat, "xxx", "xxx", "xxx", 'x', NuggetRegistry.getOreName(i)));
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
                                if (recNugget.getUnlocalizedName().equals("item.moarsign." + NuggetRegistry.getUnlocName(0))) {
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', "diamondNugget", '/', "stickWood"));
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', "nuggetDiamond", '/', "stickWood"));
                                } else if (recNugget.getUnlocalizedName().equals("item.moarsign." + NuggetRegistry.getUnlocName(1)))
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', "nuggetIron", '/', "stickWood"));
                                else if (recNugget.getUnlocalizedName().equals("item.moarsign." + NuggetRegistry.getUnlocName(2)))
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', "nuggetEmerald", '/', "stickWood"));
                                else
                                    GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack1, true, true, "XXX", "XXX", " / ", 'X', recNugget, '/', "stickWood"));
                            }

                            stack.stackSize = 9;
                        }
                        if (mat.getUnlocalizedName().equals(Items.diamond.getUnlocalizedName()))
                            GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack, true, true, "XXX", "XXX", " / ", 'X', "gemDiamond", '/', "stickWood"));
                        else if (mat.getUnlocalizedName().equals(Items.emerald.getUnlocalizedName()))
                            GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack, true, true, "XXX", "XXX", " / ", 'X', "gemEmerald", '/', "stickWood"));
                        else if (mat.getUnlocalizedName().equals(Items.iron_ingot.getUnlocalizedName()))
                            GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack, true, true, "XXX", "XXX", " / ", 'X', "ingotIron", '/', "stickWood"));
                        else
                            GameRegistry.addRecipe(new ShapedMoarSignRecipe(stack, true, true, "XXX", "XXX", " / ", 'X', mat, '/', "stickWood"));
                    }
                    break;
                }
            }
        }

        GameRegistry.addRecipe(new ShapedMoarSignRecipe(generalSign, true, true, "###", "###", " X ", '#', "plankWood", 'X', "stickWood"));
        GameRegistry.addRecipe(new ShapedMoarSignRecipe(signToolbox, "rxr", "xsx", "rxr", 'x', "ingotIron", 's', ShapedMoarSignRecipe.MatchType.ALL, 'r', "dyeRed"));

        if (replaceRecipes) {
            ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
            ItemStack signStack = new ItemStack(Items.sign);
            for (int scan = 0; scan < recipes.size(); scan++) {
                IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
                List input = null;

                if (tmpRecipe instanceof ShapedRecipes) {
                    input = Arrays.asList(((ShapedRecipes) tmpRecipe).recipeItems);
                } else if (tmpRecipe instanceof ShapelessRecipes) {
                    input = ((ShapelessRecipes) tmpRecipe).recipeItems;
                } else if (tmpRecipe instanceof ShapedOreRecipe) {
                    input = Arrays.asList(((ShapedOreRecipe) tmpRecipe).getInput());
                } else if (tmpRecipe instanceof ShapelessOreRecipe) {
                    input = ((ShapelessOreRecipe) tmpRecipe).getInput();
                }

                if (input != null) {
                    for (Object stack : input) {
                        if (stack instanceof ItemStack && OreDictionary.itemMatches((ItemStack) stack, signStack, false)) {
                            HashMap<ItemStack, Object> map = Maps.newHashMap();
                            map.put(signStack, ShapedMoarSignRecipe.MatchType.ALL);

                            IRecipe replacement = null;
                            if (tmpRecipe instanceof ShapedRecipes || tmpRecipe instanceof ShapedOreRecipe)
                                GameRegistry.addRecipe(replacement = new ShapedMoarSignRecipe(tmpRecipe, map));
                            if (tmpRecipe instanceof ShapelessRecipes || tmpRecipe instanceof ShapelessOreRecipe)
                                GameRegistry.addRecipe(replacement = new ShapelessMoarSignRecipe(tmpRecipe, map));

                            MoarSigns.logger.info("Replacing Recipe: " + tmpRecipe + " (containing " + stack + ") -> " + replacement);
                            recipes.remove(scan);
                        }
                    }
                }
            }
        }
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

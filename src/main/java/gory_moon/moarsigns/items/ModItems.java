package gory_moon.moarsigns.items;

import cpw.mods.fml.common.registry.GameRegistry;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Info;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static ItemMoarSign sign;
    public static ItemDebug debug;
    public static ItemNugget nugget;
    //public static ItemSignToolbox signToolbox;

    public static void init() {

        sign = new ItemMoarSign();
        debug = new ItemDebug();
        nugget = new ItemNugget();
        //signToolbox = new ItemSignToolbox();

        GameRegistry.registerItem(sign, Info.SIGN_ITEM_KEY);
        GameRegistry.registerItem(debug, Info.DEBUG_ITEM_KEY);
        GameRegistry.registerItem(nugget, Info.NUGGET_ITEM_KEY);
        //GameRegistry.registerItem(signToolbox, Info.SIGN_TOOLBOX_ITEM_KEY);
    }

    public static void registerRecipes() {

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
                            ItemStack recNugget = null;
                            if (!s.material.gotNugget) {
                                String unlocName = mat.getUnlocalizedName();
                                for (int i = 0; i < Info.NUGGET_INGOT_UNLOCS.length; i++) {

                                    if (Info.NUGGET_INGOT_UNLOCS[i].equals(unlocName)) {
                                        nugget.needed[i] = true;
                                        recNugget = new ItemStack(nugget, 9, i);
                                        OreDictionary.registerOre(Info.NUGGET_ORE_DICTIONARY[i], recNugget.copy());

                                        GameRegistry.addShapelessRecipe(recNugget.copy(), new Object[]{mat});
                                        GameRegistry.addShapedRecipe(mat, new Object[]{"xxx", "xxx", "xxx", 'x', recNugget.copy()});
                                        break;
                                    }
                                }
                            } else {
                                mat.stackSize = 1;
                                crafting.setInventorySlotContents(0, mat);
                                recNugget = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
                            }

                            if (recNugget != null && recNugget.getItem() != null) {
                                ItemStack stack1 = stack.copy();
                                stack1.stackSize = 1;
                                if (recNugget.getUnlocalizedName().equals("item.moarsign." + nugget.nuggets[0]))
                                    GameRegistry.addRecipe(new ShapedOreRecipe(stack1, new Object[]{"XXX", "XXX", " / ", 'X', "diamondNugget", '/', Items.stick}));
                                else if (recNugget.getUnlocalizedName().equals("item.moarsign." + nugget.nuggets[1]))
                                    GameRegistry.addRecipe(new ShapedOreRecipe(stack1, new Object[]{"XXX", "XXX", " / ", 'X', "nuggetIron", '/', Items.stick}));
                                else
                                    GameRegistry.addRecipe(stack1, new Object[]{"XXX", "XXX", " / ", 'X', recNugget, '/', Items.stick});
                            }

                            stack.stackSize = 10;
                        }
                        GameRegistry.addRecipe(stack, new Object[]{"XXX", "XXX", " / ", 'X', mat, '/', net.minecraft.init.Items.stick});
                    }
                    break;
                }
            }
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(generalSign, new Object[]{"###", "###", " X ", '#', "plankWood", 'X', Items.stick}));
    }


    private static void removeRecipesWithResult(ItemStack resultItem) {
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();

        for (int scan = 0; scan < recipes.size(); scan++) {
            IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                MoarSigns.logger.info("Removing Recipe: " + recipes.get(scan) + " -> " + recipeResult);
                recipes.remove(scan);
            }
        }
    }

}

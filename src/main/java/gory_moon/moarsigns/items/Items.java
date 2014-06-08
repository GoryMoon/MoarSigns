package gory_moon.moarsigns.items;

import cpw.mods.fml.common.registry.GameRegistry;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.lib.Info;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class Items {

    public static ItemMoarSign sign;
    public static ItemDebug debug;
    public static ItemNugget nugget;

    public static void init() {

        sign = new ItemMoarSign();

        debug = new ItemDebug();

        nugget = new ItemNugget();

        GameRegistry.registerItem(sign, Info.SIGN_ITEM_KEY);
        GameRegistry.registerItem(debug, Info.DEBUG_ITEM_KEY);
        GameRegistry.registerItem(nugget, Info.NUGGET_ITEM_KEY);
    }

    public static void registerRecipes() {

        removeRecipesWithResult(new ItemStack(net.minecraft.init.Items.sign, 3));

        //TODO remake this with the registers
        /*
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        sign.getSubItemStacks(list);
        ArrayList<Signs> signs = (ArrayList<Signs>) MoarSigns.instance.getSignsWood().clone();
        signs.addAll((ArrayList<Signs>) MoarSigns.instance.getSignsMetal().clone());

        ItemStack generalSign = null;

        Container dummyContainer = new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer entityplayer) {
                return false;
            }

            @Override
            public void onCraftMatrixChanged(IInventory par1IInventory) {}
        };
        InventoryCrafting crafting = new InventoryCrafting(dummyContainer, 3, 3);

        for (ItemStack stack: list) {

            ItemMoarSign sign = (ItemMoarSign) stack.getItem();
            String texture = sign.getTextureFromNBTFull(stack.getTagCompound());
            boolean isMetal = stack.getItemDamage() == 1;

            stack.stackSize = 3;

            if (texture.equals("oak_sign")) {
                generalSign = stack;
                continue;
            }

            for (Signs s: signs) {
                if ((s.material[s.activeMaterialIndex].path.replace("\\", "/") + s.signName).equals(texture)) {

                    String unlocName = s.material[s.activeMaterialIndex].unlocalizedName;
                    ItemStack mat = MoarSigns.craftingMats.get(unlocName);

                    if (isMetal && mat != null && mat.getItem() != null) {
                        ItemStack recNugget = null;
                        if (!s.material[s.activeMaterialIndex].gotNugget) {
                            for (int i = 0; i < Info.NUGGET_INGET_UNLOCS.length; i++){

                                if (Info.NUGGET_INGET_UNLOCS[i].equals(unlocName)) {
                                    nugget.needed[i] = true;
                                    recNugget = new ItemStack(nugget, 9, i);
                                    if (i == 0)
                                        OreDictionary.registerOre("diamondNugget", recNugget.copy());
                                    if (i == 1)
                                        OreDictionary.registerOre("nuggetIron", recNugget.copy());
                                    if (i == 1)
                                        OreDictionary.registerOre("nuggetEmerald", recNugget.copy());
                                    if (i == 3)
                                        OreDictionary.registerOre("nuggetBronze", recNugget.copy());
                                    if (i == 4)
                                        OreDictionary.registerOre("nuggetCopper", recNugget.copy());
                                    if (i == 5)
                                        OreDictionary.registerOre("nuggetTin", recNugget.copy());
                                    if (i == 6)
                                        OreDictionary.registerOre("nuggetSilver", recNugget.copy());
                                    GameRegistry.addShapelessRecipe(recNugget.copy(), new Object[]{mat});
                                    GameRegistry.addShapedRecipe(mat, new Object[]{"xxx", "xxx", "xxx", 'x', recNugget.copy()});
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
                                GameRegistry.addRecipe(new ShapedOreRecipe(stack1, new Object[]{"XXX", "XXX", " / ", 'X', "diamondNugget", '/', net.minecraft.init.Items.stick}));
                            else if (recNugget.getUnlocalizedName().equals("item.moarsign." + nugget.nuggets[1]))
                                GameRegistry.addRecipe(new ShapedOreRecipe(stack1, new Object[]{"XXX", "XXX", " / ", 'X', "nuggetIron", '/', net.minecraft.init.Items.stick}));
                            else
                                GameRegistry.addRecipe(stack1, new Object[]{"XXX", "XXX", " / ", 'X', recNugget, '/', net.minecraft.init.Items.stick});
                        }

                        stack.stackSize = 10;
                        GameRegistry.addRecipe(stack, new Object[]{"XXX","XXX"," / ", 'X', mat, '/', net.minecraft.init.Items.stick});
                    } else if (mat != null && mat.getItem() != null) {
                        GameRegistry.addRecipe(stack, new Object[]{"XXX","XXX"," / ", 'X', mat, '/', net.minecraft.init.Items.stick});
                    }

                    break;
                }
            }
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(generalSign, new Object[] {"###", "###", " X ", '#', "plankWood", 'X', net.minecraft.init.Items.stick}));
        */
    }



    private static void removeRecipesWithResult(ItemStack resultItem)
    {
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();

        for (int scan = 0; scan < recipes.size(); scan++)
        {
            IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
            {
                MoarSigns.logger.info("Removing Recipe: " + recipes.get(scan) + " -> " + recipeResult);
                recipes.remove(scan);
            }
        }
    }

}

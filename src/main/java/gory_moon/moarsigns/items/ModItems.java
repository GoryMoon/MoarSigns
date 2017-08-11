package gory_moon.moarsigns.items;

import com.google.common.collect.Maps;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.*;
import gory_moon.moarsigns.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.util.*;

@GameRegistry.ObjectHolder(Reference.MODID)
public class ModItems {

    public static final ItemMoarSign SIGN = new ItemMoarSign();
    public static final ItemDebug DEBUG = new ItemDebug();
    public static final ItemNugget NUGGET = new ItemNugget();
    public static final ItemSignToolbox SIGN_TOOLBOX = new ItemSignToolbox();

    public static boolean replaceRecipes = true;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();

        /**
         * Register this mod's {@link Item}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            NuggetRegistry.init();

            final Item[] items = {SIGN, DEBUG, NUGGET, SIGN_TOOLBOX};

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }
        }
    }

    public static void registerRecipes() {
        RecipeSorter.register("moarsigns:shaped", ShapedMoarSignRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
        RecipeSorter.register("moarsigns:shapeless", ShapelessMoarSignRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        removeRecipesWithResult(new ItemStack(Items.SIGN, 3));

        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        SIGN.getSubItemStacks(list);

        ItemStack generalSign = null;
        for (ItemStack stack : list) {

            String texture = ItemMoarSign.getTextureFromNBTFull(stack.getTagCompound());
            boolean isMetal = stack.getItemDamage() == 1;
            stack.setCount(3);

            if (texture.equals("oak_sign")) {
                generalSign = stack;
                continue;
            }

            SignInfo s = SignRegistry.get(texture);
            List<MaterialInfo> materials = SignRegistry.getAlternativeMaterials(s);

            MaterialInfo material = s != null ? s.material : null;
            materials.add(0, material);
            for (MaterialInfo infos : materials) {
                ItemStack mat = infos.material;
                if (mat != null && material != null) {
                    if (isMetal) {
                        handleMetalSign(mat, material, stack);
                        stack.setCount(9);
                    }
                    registerSignRecipe(mat, stack);
                }
            }
        }

        GameRegistry.addRecipe(new ShapedMoarSignRecipe(generalSign, true, true, "###", "###", " X ", '#', "plankWood", 'X', "stickWood"));
        GameRegistry.addRecipe(new ShapedMoarSignRecipe(SIGN_TOOLBOX, "rxr", "xsx", "rxr", 'x', "ingotIron", 's', ShapedMoarSignRecipe.MatchType.ALL, 'r', "dyeRed"));

        if (replaceRecipes) {
            replaceRecipes();
        }
    }

    private static void handleMetalSign(ItemStack mat, MaterialInfo material, ItemStack stack) {
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

        mat.setCount(1);
        if (mat.getItem() instanceof ItemBlock) {
            crafting.setInventorySlotContents(0, mat);
            mat = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
        }
        ItemStack recNugget = null;
        mat.setCount(1);

        if (!material.gotNugget) {
            String unlocName = mat.getUnlocalizedName();
            NuggetRegistry.NuggetInfo nuggetInfo = NuggetRegistry.getNuggetInfo(unlocName);
            if (nuggetInfo != null) {
                nuggetInfo.needed = true;
                recNugget = new ItemStack(NUGGET, 1, nuggetInfo.id);
                OreDictionary.registerOre(nuggetInfo.oreName, recNugget.copy());

                recNugget.setCount(9);
                GameRegistry.addShapelessRecipe(recNugget.copy(), mat);
                GameRegistry.addRecipe(new ShapedOreRecipe(mat, "xxx", "xxx", "xxx", 'x', nuggetInfo.oreName));
            }
        } else {
            if (material.materialNugget.isEmpty()) {
                crafting.setInventorySlotContents(0, mat);
                recNugget = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
            } else
                recNugget = material.materialNugget.copy();
        }

        if (recNugget != null) {
            registerNuggetSign(recNugget, stack);
        }
    }

    private static void registerNuggetSign(ItemStack nuggetMaterial, ItemStack stack) {
        ItemStack result = stack.copy();
        result.setCount(1);
        nuggetMaterial.setCount(1);
        if (isItem(nuggetMaterial, "ingot.moarsigns.diamond_nugget"))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "nuggetDiamond", '/', "stickWood"));
        else if (isItem(nuggetMaterial, Items.field_191525_da.getUnlocalizedName()))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "nuggetIron", '/', "stickWood"));
        else if (isItem(nuggetMaterial, "ingot.moarsigns.emerald_nugget"))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "nuggetEmerald", '/', "stickWood"));
        else if (isItem(nuggetMaterial, "ingot.moarsigns.lapis_nugget"))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "nuggetLapis", '/', "stickWood"));
        else if (isItem(nuggetMaterial, "ingot.moarsigns.quartz_nugget"))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "nuggetQuartz", '/', "stickWood"));
        else
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', nuggetMaterial, '/', "stickWood"));
    }

    private static boolean isItem(ItemStack stack, @Nonnull String name) {
        return name.equals(stack.getUnlocalizedName());
    }

    private static void registerSignRecipe(ItemStack material, ItemStack result) {
        if (isItem(material, Items.DIAMOND.getUnlocalizedName()))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "gemDiamond", '/', "stickWood"));
        else if (isItem(material, Items.EMERALD.getUnlocalizedName()))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "gemEmerald", '/', "stickWood"));
        else if (isItem(material, Items.IRON_INGOT.getUnlocalizedName()))
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', "ingotIron", '/', "stickWood"));
        else
            GameRegistry.addRecipe(new ShapedMoarSignRecipe(result, true, true, "XXX", "XXX", " / ", 'X', material, '/', "stickWood"));
    }

    private static void replaceRecipes() {
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
        ItemStack signStack = new ItemStack(Items.SIGN);
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

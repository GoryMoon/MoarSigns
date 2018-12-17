package gory_moon.moarsigns.items;

import com.google.common.collect.Maps;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.integration.IntegrationHandler;
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
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

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

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void preregisterRecipes(RegistryEvent.Register<IRecipe> event) {
            new IntegrationHandler().setupSigns();
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            IForgeRegistry<IRecipe> registry = event.getRegistry();
            ModItems.registerRecipes(registry);
        }
    }

    private static void registerRecipes(IForgeRegistry<IRecipe> registry) {
        removeRecipesWithResult(registry, new ItemStack(Items.SIGN, 3));

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
            for (MaterialInfo info : materials) {
                ItemStack mat = info.material;
                if (mat != null && material != null) {
                    if (isMetal) {
                        handleMetalSign(registry, mat, info, stack);
                        stack.setCount(9);
                    }
                    registerSignRecipe(registry, mat, info, stack, isMetal);
                }
            }
        }

        ModContainer mc = Loader.instance().activeModContainer();
        Loader.instance().setActiveModContainer(null);
        registry.register(new ShapedOreRecipe(null, generalSign, true, "###", "###", " X ", '#', "plankWood", 'X', "stickWood").setRegistryName(new ResourceLocation( "sign")));
        Loader.instance().setActiveModContainer(mc);

        if (replaceRecipes) {
            replaceRecipes(registry);
        }
    }

    private static void handleMetalSign(IForgeRegistry<IRecipe> registry, ItemStack mat, MaterialInfo material, ItemStack stack) {
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
            mat = CraftingManager.findMatchingResult(crafting, null);
        }
        ItemStack recNugget = null;
        mat.setCount(1);

        if (!material.gotNugget) {
            String key = mat.getTranslationKey();
            NuggetRegistry.NuggetInfo nuggetInfo = NuggetRegistry.getNuggetInfo(key);
            if (nuggetInfo != null) {
                nuggetInfo.needed = true;
                recNugget = new ItemStack(NUGGET, 1, nuggetInfo.id);
                OreDictionary.registerOre(nuggetInfo.oreName, recNugget.copy());

                recNugget.setCount(9);
                ResourceLocation loc1 = new ResourceLocation(Reference.MODID, nuggetInfo.regName);
                ResourceLocation loc2 = new ResourceLocation(Reference.MODID, nuggetInfo.regName + "_reverse");
                registry.register(new ShapelessOreRecipe(null, recNugget.copy(), mat).setRegistryName(loc1));
                registry.register(new ShapedOreRecipe(null, mat, true, "xxx", "xxx", "xxx", 'x', nuggetInfo.oreName).setRegistryName(loc2));
            }
        } else {
            if (material.materialNugget.isEmpty()) {
                crafting.setInventorySlotContents(0, mat);
                recNugget = CraftingManager.findMatchingResult(crafting, null);
            } else
                recNugget = material.materialNugget.copy();
        }

        if (recNugget != null) {
            registerNuggetSign(registry, recNugget, stack, material);
        }
    }

    private static void registerNuggetSign(IForgeRegistry<IRecipe> registry, ItemStack nuggetMaterial, ItemStack stack, MaterialInfo material) {
        ItemStack result = stack.copy();
        result.setCount(1);
        nuggetMaterial.setCount(1);

        Object ingredient = nuggetMaterial;
        if (isItem(nuggetMaterial, "item.moarsigns.diamond_nugget"))
            ingredient = "nuggetDiamond";
        else if (isItem(nuggetMaterial, Items.IRON_NUGGET.getRegistryName().toString()))
            ingredient = "nuggetIron";
        else if (isItem(nuggetMaterial, "item.moarsigns.emerald_nugget"))
            ingredient = "nuggetEmerald";
        else if (isItem(nuggetMaterial, "item.moarsigns.lapis_nugget"))
            ingredient = "nuggetLapis";
        else if (isItem(nuggetMaterial, "item.moarsigns.quartz_nugget"))
            ingredient = "nuggetQuartz";

        ResourceLocation group = new ResourceLocation(Reference.MODID, material.path.replace("/", "_") + material.materialName);
        ResourceLocation location = new ResourceLocation(Reference.MODID, "nugget_sign_" + (ingredient instanceof String ? ingredient: material.path.replaceAll("/", "_") + material.materialName));
        registry.register(new ShapedOreRecipe(group, result, true, "XXX", "XXX", " / ", 'X', ingredient, '/' , "stickWood").setRegistryName(location));
    }

    private static boolean isItem(ItemStack stack, @Nonnull String name) {
        return name.equals(stack.getTranslationKey());
    }

    private static void registerSignRecipe(IForgeRegistry<IRecipe> registry, ItemStack material, MaterialInfo materialInfo, ItemStack result, boolean isMetal) {
        Object ingredient;
        if ("diamond".equals(materialInfo.materialName))
            ingredient = "gemDiamond";
        else if ("emerald".equals(materialInfo.materialName))
            ingredient = "gemEmerald";
        else if ("iron".equals(materialInfo.materialName))
            ingredient = "ingotIron";
        else
            ingredient = material;

        ResourceLocation location = new ResourceLocation(Reference.MODID, "sign_" + materialInfo.path.replaceAll("/", "_") + materialInfo.materialName);
        ResourceLocation group = null;
        if (isMetal)
            group = new ResourceLocation(Reference.MODID, materialInfo.path.replace("/", "_") + materialInfo.materialName);

        registry.register(new ShapedOreRecipe(group, result, true, "XXX", "XXX", " / ", 'X', ingredient, '/', "stickWood").setRegistryName(location));
    }

    private static void replaceRecipes(IForgeRegistry<IRecipe> registry) {
        Collection<IRecipe> recipes = registry.getValuesCollection();
        ItemStack signStack = new ItemStack(Items.SIGN);
        for (IRecipe tmpRecipe : recipes) {
            NonNullList<Ingredient> ingredients = tmpRecipe.getIngredients();

            for (Ingredient stack : ingredients) {
                if (stack.apply(signStack)) {
                    HashMap<ItemStack, Object> map = Maps.newHashMap();
                    MoarSigns.logger.info("Found recipe with sign: " + tmpRecipe.getRegistryName());
                }
            }
        }
    }


    private static void removeRecipesWithResult(IForgeRegistry<IRecipe> registry, ItemStack resultItem) {
        for (IRecipe tmpRecipe : registry) {
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                MoarSigns.logger.debug("Removing Recipe: " + tmpRecipe + " -> " + recipeResult);
                ((ForgeRegistry<IRecipe>) registry).remove(tmpRecipe.getRegistryName());
            }
        }
    }

}

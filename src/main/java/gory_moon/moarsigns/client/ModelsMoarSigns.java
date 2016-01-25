package gory_moon.moarsigns.client;

import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.items.NuggetRegistry;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.ArrayList;

public class ModelsMoarSigns {

    public static void prepareModels() {

        ArrayList<String> signs = SignRegistry.getTextureLocations(false, false);
        addVariantNames(Blocks.signStandingWood, signs.toArray(new String[signs.size()]));
        addVariantNames(Blocks.signWallWood, signs.toArray(new String[signs.size()]));
        signs = SignRegistry.getTextureLocations(true, false);
        addVariantNames(Blocks.signStandingMetal, signs.toArray(new String[signs.size()]));
        addVariantNames(Blocks.signWallMetal, signs.toArray(new String[signs.size()]));

        signs = SignRegistry.getTextureLocations(true);
        addVariantModelNames(ModItems.sign, signs.toArray(new String[signs.size()]));

        ArrayList<String> nuggets = NuggetRegistry.getNames();
        addVariantNames(ModItems.nugget, nuggets.toArray(new String[nuggets.size()]));

        addVariantNames(ModItems.debug, Info.DEBUG_ITEM_KEY);
        addVariantNames(ModItems.signToolbox, Info.SIGN_TOOLBOX_ITEM_KEY + "/edit", Info.SIGN_TOOLBOX_ITEM_KEY + "/rotate", Info.SIGN_TOOLBOX_ITEM_KEY + "/move", Info.SIGN_TOOLBOX_ITEM_KEY + "/copy", Info.SIGN_TOOLBOX_ITEM_KEY + "/exchange", Info.SIGN_TOOLBOX_ITEM_KEY + "/preview");
    }

    public static void registerModels() {

        for (int i = 0; i < NuggetRegistry.getNuggets().size(); i++)
            registerItemModel(ModItems.nugget, i, getResource("nuggets/" + NuggetRegistry.getUnlocName(i)));

        registerItemModel(ModItems.debug);
        registerItemModel(ModItems.signToolbox, 0, getResource(Info.SIGN_TOOLBOX_ITEM_KEY + "/edit"));
        registerItemModel(ModItems.signToolbox, 1, getResource(Info.SIGN_TOOLBOX_ITEM_KEY + "/rotate"));
        registerItemModel(ModItems.signToolbox, 2, getResource(Info.SIGN_TOOLBOX_ITEM_KEY + "/move"));
        registerItemModel(ModItems.signToolbox, 7, getResource(Info.SIGN_TOOLBOX_ITEM_KEY + "/move"));
        registerItemModel(ModItems.signToolbox, 3, getResource(Info.SIGN_TOOLBOX_ITEM_KEY + "/copy"));
        registerItemModel(ModItems.signToolbox, 4, getResource(Info.SIGN_TOOLBOX_ITEM_KEY + "/exchange"));
        registerItemModel(ModItems.signToolbox, 5, getResource(Info.SIGN_TOOLBOX_ITEM_KEY + "/preview"));
    }

    private static void addVariantNames(Block block, String... names) {
        ResourceLocation[] locations = new ResourceLocation[names.length];
        for (int i = 0; i < names.length; i++)
        {
            locations[i] = new ResourceLocation(getResource(names[i]));
        }

        if(block != null)
            ModelBakery.registerItemVariants(Item.getItemFromBlock(block), locations);
    }

    private static void addVariantModelNames(Item item, String... names) {
        ModelResourceLocation[] locations = new ModelResourceLocation[names.length];
        for (int i = 0; i < names.length; i++)
        {
            locations[i] = new ModelResourceLocation(names[i], "inventory");
        }

        ModelBakery.registerItemVariants(item, locations);
    }

    private static void addVariantNames(Item item, String... names) {
        ResourceLocation[] locations = new ResourceLocation[names.length];
        for (int i = 0; i < names.length; i++)
        {
            locations[i] = new ResourceLocation(getResource(names[i]));
        }

        ModelBakery.registerItemVariants(item, locations);
    }

    private static void registerBlockModel(Block block) {
        ResourceLocation resourceLocation = (ResourceLocation) Block.blockRegistry.getNameForObject(block);

        registerBlockModel(block, 0, resourceLocation.toString());
    }

    private static void registerItemModel(Item item) {
        ResourceLocation resourceLocation = (ResourceLocation) Item.itemRegistry.getNameForObject(item);

        registerItemModel(item, 0, resourceLocation.toString());
    }

    private static void registerBlockModel(Block block, int meta, String modelName) {
        registerItemModel(Item.getItemFromBlock(block), meta, modelName);
    }

    private static void registerItemModel(Item item, int meta, String resourcePath) {
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(resourcePath, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
    }

    public static String getResource(String resource) {
        return (ModInfo.ID + ":") + resource;
    }


}

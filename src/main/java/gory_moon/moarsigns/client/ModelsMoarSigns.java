package gory_moon.moarsigns.client;

import gory_moon.moarsigns.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ModelsMoarSigns {

    public static void prepareModels() {

    }

    public static void registerModels() {

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

    public static void registerItemModels(Item item) {
        /*for (int i = 0; i < ((IItemWithVariants) item).getVariantNames().length; i++) {
            String NAME = item.getUnlocalizedName().substring(5) + "_" + ((IItemWithVariants) item).getVariantNames()[i];
            registerItemModel(item, i, getResource(NAME));
        }*/
    }

    public static String getResource(String resource) {
        return (ModInfo.ID + ":") + resource;
    }


}

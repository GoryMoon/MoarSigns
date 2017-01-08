package gory_moon.moarsigns.client;

import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.BlockMoarSignStanding;
import gory_moon.moarsigns.blocks.BlockMoarSignWall;
import gory_moon.moarsigns.blocks.ModBlocks;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.items.NuggetRegistry;
import gory_moon.moarsigns.items.VariantItem;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntFunction;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ModModelManager {

    public static final ModModelManager INSTANCE = new ModModelManager();
    private final Set<Item> itemsRegistered = new HashSet<>();

    public ModModelManager() {
    }

    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event) {
        new IntegrationHandler().preSetupSigns();
        INSTANCE.registerBlockModels();
        INSTANCE.registerItemModels();
        SignRegistry.clear();
    }

    private void registerBlockModels() {
        ModelLoader.setCustomStateMapper(ModBlocks.SIGN_STANDING_WOOD, (new StateMap.Builder()).ignore(new IProperty[]{BlockMoarSignStanding.ROTATION}).build());
        ModelLoader.setCustomStateMapper(ModBlocks.SIGN_STANDING_METAL, (new StateMap.Builder()).ignore(new IProperty[]{BlockMoarSignStanding.ROTATION}).build());
        ModelLoader.setCustomStateMapper(ModBlocks.SIGN_WALL_WOOD, (new StateMap.Builder()).ignore(new IProperty[]{BlockMoarSignWall.ROTATION, BlockMoarSignWall.FACING}).build());
        ModelLoader.setCustomStateMapper(ModBlocks.SIGN_WALL_METAL, (new StateMap.Builder()).ignore(new IProperty[]{BlockMoarSignWall.ROTATION, BlockMoarSignWall.FACING}).build());

        ModBlocks.RegistrationHandler.ITEM_BLOCKS.stream().filter(item -> !itemsRegistered.contains(item)).forEach(this::registerItemModel);
    }

    /**
     * Register this mod's {@link Item} models.
     */
    private void registerItemModels() {
        // Register items with custom model names first
        ArrayList<String> signs = SignRegistry.getTextureLocations(false);
        for (String s : signs)
            registerItemModel(ModItems.SIGN, getModel(s));

        //Overrides the ItemMeshDefenition wit a custom one
        registerItemModel(ModItems.SIGN, new MoarSignsItemMeshDefenition());

        for (Map.Entry<String, NuggetRegistry.NuggetInfo> entry : Utils.entriesSortedByValues(NuggetRegistry.getNuggets()))
            registerItemModelForMeta(ModItems.NUGGET, entry.getValue().id, getModel("nuggets/" + entry.getValue().modId + entry.getValue().unlocName));

        registerVariantItems(ModItems.SIGN_TOOLBOX, "mode");

        // Then register items with default model names
        ModItems.RegistrationHandler.ITEMS.stream().filter(item -> !itemsRegistered.contains(item)).forEach(this::registerItemModel);
    }


    /**
     * A {@link StateMapperBase} used to create property strings.
     */
    private final StateMapperBase propertyStringMapper = new StateMapperBase() {
        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return new ModelResourceLocation("minecraft:air");
        }
    };

    /**
     * Register a single model for the {@link Block}'s {@link Item}.
     * <p>
     * Uses the registry name as the domain/path and the {@link IBlockState} as the variant.
     *
     * @param state The state to use as the variant
     */
    private void registerBlockItemModel(IBlockState state) {
        final Block block = state.getBlock();
        final Item item = Item.getItemFromBlock(block);

        if (item != null) {
            registerItemModel(item, new ModelResourceLocation(block.getRegistryName(), propertyStringMapper.getPropertyString(state.getProperties())));
        }
    }

    /**
     * Register a model for a metadata value of the {@link Block}'s {@link Item}.
     * <p>
     * Uses the registry name as the domain/path and the {@link IBlockState} as the variant.
     *
     * @param state    The state to use as the variant
     * @param metadata The item metadata to register the model for
     */
    private void registerBlockItemModelForMeta(IBlockState state, int metadata) {
        final Item item = Item.getItemFromBlock(state.getBlock());

        if (item != null) {
            registerItemModelForMeta(item, metadata, propertyStringMapper.getPropertyString(state.getProperties()));
        }
    }

    /**
     * Register a model for each metadata value of the {@link Block}'s {@link Item} corresponding to the values of an {@link IProperty}.
     * <p>
     * For each value:
     * <li>The domain/path is the registry name</li>
     * <li>The variant is {@code baseState} with the {@link IProperty} set to the value</li>
     * <p>
     * The {@code getMeta} function is used to get the metadata of each value.
     *
     * @param baseState The base state to use for the variant
     * @param property  The property whose values should be used
     * @param getMeta   A function to get the metadata of each value
     * @param <T>       The value type
     */
    private <T extends Comparable<T>> void registerVariantBlockItemModels(IBlockState baseState, IProperty<T> property, ToIntFunction<T> getMeta) {
        property.getAllowedValues().forEach(value -> registerBlockItemModelForMeta(baseState.withProperty(property, value), getMeta.applyAsInt(value)));
    }

    private <T extends VariantItem> void registerVariantItems(T variant, String variantName) {
        variant.getMetas().forEach(value -> registerItemModelForMeta(variant, value, variantName + "=" + variant.getVariant(value)));
    }

    /**
     * Register a single model for an {@link Item}.
     * <p>
     * Uses the registry name as the domain/path and {@code "inventory"} as the variant.
     *
     * @param item The Item
     */
    private void registerItemModel(Item item) {
        registerItemModel(item, item.getRegistryName().toString());
    }

    /**
     * Register a single model for an {@link Item}.
     * <p>
     * Uses {@code modelLocation} as the domain/path and {@link "inventory"} as the variant.
     *
     * @param item          The Item
     * @param modelLocation The model location
     */
    private void registerItemModel(Item item, String modelLocation) {
        final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
        registerItemModel(item, fullModelLocation);
    }

    /**
     * Register a single model for an {@link Item}.
     * <p>
     * Uses {@code fullModelLocation} as the domain, path and variant.
     *
     * @param item              The Item
     * @param fullModelLocation The full model location
     */
    private void registerItemModel(Item item, ModelResourceLocation fullModelLocation) {
        ModelBakery.registerItemVariants(item, fullModelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
        registerItemModel(item, MeshDefinitionFix.create(stack -> fullModelLocation));
    }

    /**
     * Register an {@link ItemMeshDefinition} for an {@link Item}.
     *
     * @param item           The Item
     * @param meshDefinition The ItemMeshDefinition
     */
    private void registerItemModel(Item item, ItemMeshDefinition meshDefinition) {
        itemsRegistered.add(item);
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    /**
     * Register a model for a metadata value an {@link Item}.
     * <p>
     * Uses the registry name as the domain/path and {@code variant} as the variant.
     *
     * @param item     The Item
     * @param metadata The metadata
     * @param variant  The variant
     */
    private void registerItemModelForMeta(Item item, int metadata, String variant) {
        registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    /**
     * Register a model for a metadata value of an {@link Item}.
     * <p>
     * Uses {@code modelResourceLocation} as the domain, path and variant.
     *
     * @param item                  The Item
     * @param metadata              The metadata
     * @param modelResourceLocation The full model location
     */
    private void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation modelResourceLocation) {
        itemsRegistered.add(item);
        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
    }

    public static ModelResourceLocation getModel(String resource) {
        return new ModelResourceLocation(Reference.MODID + ":" + resource, "inventory");
    }

}

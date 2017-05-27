package gory_moon.moarsigns.blocks;

import gory_moon.moarsigns.lib.Constants;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {

    public static final BlockMoarSignStanding SIGN_STANDING_WOOD = (BlockMoarSignStanding) new BlockMoarSignStanding(Material.WOOD, SoundType.WOOD, 1.0F, 5.0F, Constants.SIGN_STAND_WOOD_KEY, "axe", 0);
    public static final BlockMoarSignWall SIGN_WALL_WOOD = (BlockMoarSignWall) new BlockMoarSignWall(Material.WOOD, SoundType.WOOD, 1.0F, 5.0F, Constants.SIGN_WALL_WOOD_KEY, "axe", 0);

    public static final BlockMoarSignStanding SIGN_STANDING_METAL = (BlockMoarSignStanding) new BlockMoarSignStanding(Material.IRON, SoundType.METAL, 2.0F, 10.0F, Constants.SIGN_STAND_METAL_KEY, "pickaxe", 1);
    public static final BlockMoarSignWall SIGN_WALL_METAL = (BlockMoarSignWall) new BlockMoarSignWall(Material.IRON, SoundType.METAL, 2.0F, 10.0F, Constants.SIGN_WALL_METAL_KEY, "pickaxe", 1);

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        /**
         * Register this mod's {@link Block}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {SIGN_STANDING_WOOD, SIGN_WALL_WOOD, SIGN_STANDING_METAL, SIGN_WALL_METAL};

            registry.registerAll(blocks);
        }

        /**
         * Register this mod's {@link ItemBlock}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
            final ItemBlock[] items = {

            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final ItemBlock item : items) {
                registry.register(item.setRegistryName(item.getBlock().getRegistryName()));
                ITEM_BLOCKS.add(item);
            }
        }
    }


    public static void registerTileEntities() {
        registerTileEntityNoPrefix(TileEntityMoarSign.class, TileEntityMoarSign.class.getSimpleName().replaceFirst("TileEntity", ""), Constants.SIGN_TE_ID);
    }

   /* private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {
        GameRegistry.registerTileEntity(tileEntityClass, Reference.RESOURCE_PREFIX + tileEntityClass.getSimpleName().replaceFirst("TileEntity", ""));
    }

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String name, String legacyName) {
        GameRegistry.registerTileEntityWithAlternatives(tileEntityClass, Reference.RESOURCE_PREFIX + name, Reference.RESOURCE_PREFIX + legacyName);
    }*/

    private static void registerTileEntityNoPrefix(Class<? extends TileEntity> tileEntityClass, String name, String legacyName) {
        GameRegistry.registerTileEntityWithAlternatives(tileEntityClass, Reference.RESOURCE_PREFIX + name, legacyName);
    }

}

package gory_moon.moarsigns.blocks;

import gory_moon.moarsigns.lib.Constants;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {

    public static Block signStandingWood;
    public static Block signWallWood;

    public static Block signStandingMetal;
    public static Block signWallMetal;

    public static void init() {

        signStandingWood = new BlockMoarSignStanding(Material.WOOD, SoundType.WOOD).setHardness(1.0F).setResistance(5.0F).setRegistryName(Constants.SIGN_STAND_WOOD_KEY);
        signWallWood = new BlockMoarSignWall(Material.WOOD, SoundType.WOOD).setHardness(1.0F).setResistance(5.0F).setRegistryName(Constants.SIGN_WALL_WOOD_KEY);

        signStandingMetal = new BlockMoarSignStanding(Material.IRON, SoundType.METAL).setHardness(2.0F).setResistance(10.0F).setRegistryName(Constants.SIGN_STAND_METAL_KEY);
        signWallMetal = new BlockMoarSignWall(Material.IRON, SoundType.METAL).setHardness(2.0F).setResistance(10.0F).setRegistryName(Constants.SIGN_WALL_METAL_KEY);

        signStandingWood.setHarvestLevel("axe", 0);
        signWallWood.setHarvestLevel("axe", 0);
        signStandingMetal.setHarvestLevel("pickaxe", 1);
        signWallMetal.setHarvestLevel("pickaxe", 1);

        GameRegistry.register(signStandingWood);
        GameRegistry.register(signWallWood);
        GameRegistry.register(signStandingMetal);
        GameRegistry.register(signWallMetal);

        GameRegistry.register(new ItemBlock(signStandingWood).setRegistryName(Constants.SIGN_STAND_WOOD_KEY));
        GameRegistry.register(new ItemBlock(signWallWood).setRegistryName(Constants.SIGN_WALL_WOOD_KEY));
        GameRegistry.register(new ItemBlock(signStandingMetal).setRegistryName(Constants.SIGN_STAND_METAL_KEY));
        GameRegistry.register(new ItemBlock(signWallMetal).setRegistryName(Constants.SIGN_WALL_METAL_KEY));

        GameRegistry.registerTileEntity(TileEntityMoarSign.class, Constants.SIGN_TE_ID);

    }

}

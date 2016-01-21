package gory_moon.moarsigns.blocks;

import net.minecraftforge.fml.common.registry.GameRegistry;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Blocks {

    public static Block signStandingWood;
    public static Block signWallWood;

    public static Block signStandingMetal;
    public static Block signWallMetal;

    public static void init() {

        signStandingWood = new BlockMoarSignStanding(Material.wood, true).setStepSound(Block.soundTypeWood).setHardness(1.0F).setResistance(5.0F);
        signWallWood = new BlockMoarSignWall(Material.wood, false).setStepSound(Block.soundTypeWood).setHardness(1.0F).setResistance(5.0F);

        signStandingMetal = new BlockMoarSignStanding(Material.iron, true).setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(10.0F);
        signWallMetal = new BlockMoarSignWall(Material.iron, false).setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(10.0F);

        signStandingWood.setHarvestLevel("axe", 0);
        signWallWood.setHarvestLevel("axe", 0);
        signStandingMetal.setHarvestLevel("pickaxe", 1);
        signWallMetal.setHarvestLevel("pickaxe", 1);

        GameRegistry.registerBlock(signStandingWood, Info.SIGN_STAND_WOOD_KEY);
        GameRegistry.registerBlock(signWallWood, Info.SIGN_WALL_WOOD_KEY);
        GameRegistry.registerBlock(signStandingMetal, Info.SIGN_STAND_METAL_KEY);
        GameRegistry.registerBlock(signWallMetal, Info.SIGN_WALL_METAL_KEY);

        GameRegistry.registerTileEntity(TileEntityMoarSign.class, Info.SIGN_TE_ID);

    }

}

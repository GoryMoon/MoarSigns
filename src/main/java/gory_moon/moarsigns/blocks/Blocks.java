package gory_moon.moarsigns.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;

public class Blocks {

    public static Block signStandingWood;
    public static Block signWallWood;

    public static Block signStandingMetal;
    public static Block signWallMetal;

    public static void init() {

        signStandingWood = new BlockMoarSign(Info.SIGN_STAND_WOOD_ID, Material.wood, true).setStepSound(Block.soundWoodFootstep).setHardness(1.0F).setResistance(5.0F);
        signWallWood = new BlockMoarSign(Info.SIGN_WALL_WOOD_ID, Material.wood, false).setStepSound(Block.soundWoodFootstep).setHardness(1.0F).setResistance(5.0F);


        signStandingMetal = new BlockMoarSign(Info.SIGN_STAND_METAL_ID, Material.iron, true).setStepSound(Block.soundMetalFootstep).setHardness(2.0F).setResistance(10.0F);
        signWallMetal = new BlockMoarSign(Info.SIGN_WALL_METAL_ID, Material.iron, false).setStepSound(Block.soundMetalFootstep).setHardness(2.0F).setResistance(10.0F);

        GameRegistry.registerBlock(signStandingWood, Info.SIGN_STAND_WOOD_KEY);
        GameRegistry.registerBlock(signWallWood, Info.SIGN_WALL_WOOD_KEY);
        GameRegistry.registerBlock(signStandingMetal, Info.SIGN_STAND_METAL_KEY);
        GameRegistry.registerBlock(signWallMetal, Info.SIGN_WALL_METAL_KEY);

        MinecraftForge.setBlockHarvestLevel(signStandingWood, "axe", 0);
        MinecraftForge.setBlockHarvestLevel(signWallWood, "axe", 0);

        MinecraftForge.setBlockHarvestLevel(signStandingMetal, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(signWallMetal, "pickaxe", 1);

        GameRegistry.registerTileEntity(TileEntityMoarSign.class, Info.SIGN_TE_ID);

    }

}

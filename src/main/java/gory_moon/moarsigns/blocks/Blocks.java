package gory_moon.moarsigns.blocks;

import gory_moon.moarsigns.lib.Info;
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

        signStandingWood = new BlockMoarSignStanding(Material.wood, SoundType.WOOD).setHardness(1.0F).setResistance(5.0F).setRegistryName(Info.SIGN_STAND_WOOD_KEY);
        signWallWood = new BlockMoarSignWall(Material.wood, SoundType.WOOD).setHardness(1.0F).setResistance(5.0F).setRegistryName(Info.SIGN_WALL_WOOD_KEY);

        signStandingMetal = new BlockMoarSignStanding(Material.iron, SoundType.METAL).setHardness(2.0F).setResistance(10.0F).setRegistryName(Info.SIGN_STAND_METAL_KEY);
        signWallMetal = new BlockMoarSignWall(Material.iron, SoundType.METAL).setHardness(2.0F).setResistance(10.0F).setRegistryName(Info.SIGN_WALL_METAL_KEY);

        signStandingWood.setHarvestLevel("axe", 0);
        signWallWood.setHarvestLevel("axe", 0);
        signStandingMetal.setHarvestLevel("pickaxe", 1);
        signWallMetal.setHarvestLevel("pickaxe", 1);

        GameRegistry.register(signStandingWood);
        GameRegistry.register(signWallWood);
        GameRegistry.register(signStandingMetal);
        GameRegistry.register(signWallMetal);

        GameRegistry.register(new ItemBlock(signStandingWood).setRegistryName(Info.SIGN_STAND_WOOD_KEY));
        GameRegistry.register(new ItemBlock(signWallWood).setRegistryName(Info.SIGN_WALL_WOOD_KEY));
        GameRegistry.register(new ItemBlock(signStandingMetal).setRegistryName(Info.SIGN_STAND_METAL_KEY));
        GameRegistry.register(new ItemBlock(signWallMetal).setRegistryName(Info.SIGN_WALL_METAL_KEY));

        GameRegistry.registerTileEntity(TileEntityMoarSign.class, Info.SIGN_TE_ID);

    }

}

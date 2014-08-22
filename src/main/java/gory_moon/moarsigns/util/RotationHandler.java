package gory_moon.moarsigns.util;

import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.world.World;

public class RotationHandler {

    public static void rotate(TileEntityMoarSign tileEntity) {
        if (tileEntity.getWorldObj().isRemote) return;
        World world = tileEntity.getWorldObj();
        int meta = world.getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);

        int side = meta;
        boolean flatSign;
        boolean isFreestanding = ((BlockMoarSign)world.getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord)).isFreestanding;

        if (!isFreestanding) {
            boolean testing = true;
            while (testing) {
                meta = side;
                side = meta & 7;
                flatSign = ((meta & 8) >> 3) == 1;
                if (flatSign) {
                    meta = side;
                    side = meta & 1;
                    int rotation = (meta & 6) >> 1;

                    if (side == 1) {
                        if (rotation == 3) side = 2;
                        else {
                            side = (++rotation << 1);
                            side += 9;
                        }
                    } else {
                            if (rotation == 3) {
                                side = 9;
                            } else {
                                side = (++rotation << 1);
                                side += 8;
                            }
                    }
                } else {
                    side = side & 7;
                    if (side == 5) side = 8;
                    else if (side == 2) side = 4;
                    else if (side == 4) side = 3;
                    else if (side == 3) side = 5;
                }
                if (((BlockMoarSign)Blocks.signWallMetal).canPlaceBlockAt(world, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, side)) {
                    testing = false;
                }
            }
            rotate(tileEntity, side);
        } else {
            if (meta == 15) {
                meta = 0;
            } else {
                meta++;
            }
            rotate(tileEntity, meta);
        }
    }

    public static void rotate(TileEntityMoarSign tileEntity, int rotation) {
        if (rotation > 15 || tileEntity.getWorldObj().isRemote) return;
        World world = tileEntity.getWorldObj();
        world.setBlockMetadataWithNotify(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, rotation, 3);
    }

}

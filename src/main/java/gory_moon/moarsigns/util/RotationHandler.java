package gory_moon.moarsigns.util;

import gory_moon.moarsigns.blocks.BlockMoarSignStanding;
import gory_moon.moarsigns.blocks.ModBlocks;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class RotationHandler {

    public static void rotate(TileEntityMoarSign tileEntity, boolean reverse) {
        if (tileEntity.getWorld().isRemote)
            return;
        World world = tileEntity.getWorld();
        IBlockState state = world.getBlockState(tileEntity.getPos());
        int meta = state.getBlock().getMetaFromState(state);

        int side = meta;
        boolean flatSign;
        Block block = world.getBlockState(tileEntity.getPos()).getBlock();
        boolean isFreestanding = block instanceof BlockMoarSignStanding;

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
                        if (rotation == 3)
                            side = 2;
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
                    if (side == 5)
                        side = 8;
                    else if (side == 2)
                        side = 4;
                    else if (side == 4)
                        side = 3;
                    else if (side == 3)
                        side = 5;
                }
                flatSign = ((side & 8) >> 3) == 1;
                int enumSide = flatSign ? side & 1 : side & 7;

                if (ModBlocks.SIGN_STANDING_METAL.canPlaceBlockAt(world, tileEntity.getPos().offset(EnumFacing.byIndex(enumSide).getOpposite()))) {
                    testing = false;
                }
            }
            setRotation(tileEntity, side);
        } else {
            if (reverse) {
                if (meta == 0) {
                    meta = 15;
                } else {
                    meta--;
                }
            } else {
                if (meta == 15) {
                    meta = 0;
                } else {
                    meta++;
                }
            }
            setRotation(tileEntity, meta);
        }
    }

    public static void rotate(TileEntityMoarSign tileEntity, int rotation) {
        if (tileEntity.getWorld().isRemote)
            return;
        for (int i = 0; i < rotation; i++)
            rotate(tileEntity, false);
    }

    public static void setRotation(TileEntityMoarSign tileEntity, int rotation) {
        if (rotation < 0 || rotation > 15 || tileEntity.getWorld().isRemote)
            return;
        World world = tileEntity.getWorld();
        IBlockState state = world.getBlockState(tileEntity.getPos());
        world.setBlockState(tileEntity.getPos(), state.getBlock().getStateFromMeta(rotation), 3);
        tileEntity.markDirty();
    }

}

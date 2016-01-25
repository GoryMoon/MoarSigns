package gory_moon.moarsigns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMoarSignWall extends BlockMoarSign {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockMoarSignWall(Material material, boolean freeStand) {
        super(material, freeStand);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ROTATION, meta);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, ROTATION, FACING);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int meta = getMetaFromState(state);
        boolean flatSign = ((meta & 8) >> 3) == 1;
        int facing = flatSign ? (meta & 1): (meta & 7);
        return state.withProperty(FACING, EnumFacing.getFront(facing));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ROTATION);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        int l = state.getBlock().getMetaFromState(state);
        float f = 0.28125F;
        float f1 = 0.78125F;
        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.125F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        EnumFacing side = EnumFacing.getFront(l & 7);
        boolean flatSign = ((l & 8) >> 3) == 1;
        boolean groundSign;

        if (flatSign) {
            groundSign = (l & 1) == 1;
            int rotation = (l & 6) >> 1;

            if (groundSign) {
                setBlockBounds(f2, f2, f - 0.01F, f3, f4, f1 - 0.01F);
                switch (rotation) {
                    case 1:
                        setBlockBounds(f - 0.05F, f2, f2, f1 - 0.05F, f4, f3);
                        break;
                    case 2:
                        setBlockBounds(f2, f2, f - 0.05F, f3, f4, f1 - 0.05F);
                        break;
                    case 3:
                        setBlockBounds(f - 0.01F, f2, f2, f1 - 0.01F, f4, f3);
                }
            } else {
                setBlockBounds(f2, f3 - f4, f - 0.05F, f3, f3, f1 - 0.05F);
                switch (rotation) {
                    case 1:
                        setBlockBounds(f - 0.01F, f3 - f4, f2, f1 - 0.01F, f3, f3);
                        break;
                    case 2:
                        setBlockBounds(f2, f3 - f4, f - 0.01F, f3, f3, f1 - 0.01F);
                        break;
                    case 3:
                        setBlockBounds(f - 0.05F, f3 - f4, f2, f1 - 0.05F, f3, f3);
                }
            }
        } else {
            switch (side) {
                case NORTH:
                    setBlockBounds(f2, f - 0.01F, f3 - f4, f3, f1 - 0.01F, f3);
                    break;
                case SOUTH:
                    setBlockBounds(f2, f - 0.01F, f2, f3, f1 - 0.01F, f4);
                    break;
                case WEST:
                    setBlockBounds(f3 - f4, f - 0.01F, f2, f3, f1 - 0.01F, f3);
                    break;
                case EAST:
                    setBlockBounds(f2, f - 0.01F, f2, f4, f1 - 0.01F, f3);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) {
        boolean flag;
        int rotation = world.getBlockState(pos).getValue(ROTATION);
        EnumFacing facing = EnumFacing.getFront(rotation & 7);


        boolean flatSign = ((rotation & 8) >> 3) == 1;
        boolean groundSign;

        if (flatSign) {
            groundSign = (rotation & 1) == 1;

            if (groundSign) {
                flag = !(world.getBlockState(pos.down()).getBlock().getMaterial().isSolid());
            } else {
                flag = !(world.getBlockState(pos.up()).getBlock().getMaterial().isSolid());
            }
        } else {

            flag = !(facing == EnumFacing.NORTH && world.getBlockState(pos.south()).getBlock().getMaterial().isSolid());

            if (world.getBlockState(pos.offset(facing.getOpposite())).getBlock().getMaterial().isSolid())
                flag = false;
        }

        if (flag) {
            world.setBlockToAir(pos);
        }
    }
}

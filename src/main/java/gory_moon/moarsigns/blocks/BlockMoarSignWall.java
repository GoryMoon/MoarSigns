package gory_moon.moarsigns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMoarSignWall extends BlockMoarSign {

    public static final IProperty FACING = PropertyDirection.create("facing");
    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 3);

    public BlockMoarSignWall(Material material, boolean freeStand) {
        super(material, freeStand);
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ROTATION, 0));
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING, ROTATION);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean flatSign = ((meta & 8) >> 3) == 1;
        int rotation = flatSign ? 0: (meta & 6) >> 1;
        int facing = flatSign ? ((meta & 1) == 0 ? 1: 0): (meta & 7);
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(facing)).withProperty(ROTATION, rotation);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = ((EnumFacing) state.getValue(FACING));
        int meta = facing.getIndex();
        meta += (state.getValue(ROTATION) << 1);

        if (facing == EnumFacing.DOWN || facing == EnumFacing.UP)
            meta += 8;

        return meta;
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
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        if (!world.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().getMaterial().isSolid())
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }
}

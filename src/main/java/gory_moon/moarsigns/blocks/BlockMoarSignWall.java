package gory_moon.moarsigns.blocks;

import com.girafi.passthroughsigns.api.IPassable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(modid = "passthroughsigns", iface = "com.girafi.passthroughsigns.api.IPassable")
public class BlockMoarSignWall extends BlockMoarSign implements IPassable {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    protected static final AxisAlignedBB SIGN_EAST_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 0.125D, 0.78125D, 1.0D);
    protected static final AxisAlignedBB SIGN_WEST_AABB = new AxisAlignedBB(0.875D, 0.28125D, 0.0D, 1.0D, 0.78125D, 1.0D);
    protected static final AxisAlignedBB SIGN_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.0D, 1.0D, 0.78125D, 0.125D);
    protected static final AxisAlignedBB SIGN_NORTH_AABB = new AxisAlignedBB(0.0D, 0.28125D, 0.875D, 1.0D, 0.78125D, 1.0D);

    protected static final AxisAlignedBB SIGN_GROUND0_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.27125D, 1.0D, 0.125D, 0.77125D);
    protected static final AxisAlignedBB SIGN_GROUND1_AABB = new AxisAlignedBB(0.23125D, 0.0D, 0.0D, 0.73125D, 0.125D, 1.0D);
    protected static final AxisAlignedBB SIGN_GROUND2_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.23125D, 1.0D, 0.125D, 0.73125D);
    protected static final AxisAlignedBB SIGN_GROUND3_AABB = new AxisAlignedBB(0.27125D, 0.0D, 0.0D, 0.77125D, 0.125D, 1.0D);

    protected static final AxisAlignedBB SIGN_ROOF0_AABB = new AxisAlignedBB(0.0D, 0.875D, 0.23125D, 1.0D, 1.0D, 0.73125D);
    protected static final AxisAlignedBB SIGN_ROOF1_AABB = new AxisAlignedBB(0.27125D, 0.875D, 0.0D, 0.77125D, 1.0D, 1.0D);
    protected static final AxisAlignedBB SIGN_ROOF2_AABB = new AxisAlignedBB(0.0D, 0.875D, 0.27125D, 1.0D, 1.0D, 0.77125D);
    protected static final AxisAlignedBB SIGN_ROOF3_AABB = new AxisAlignedBB(0.23125D, 0.875D, 0.0D, 0.23125D, 1.0D, 1.0D);

    public BlockMoarSignWall(Material material, SoundType stepSound, float hardness, float resistance, String registryname, String harvestLevel, int level) {
        super(material, stepSound, hardness, resistance, registryname, harvestLevel, level);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ROTATION, meta);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ROTATION, FACING);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int meta = getMetaFromState(state);
        boolean flatSign = ((meta & 8) >> 3) == 1;
        int facing = flatSign ? (meta & 1) : (meta & 7);
        return state.withProperty(FACING, EnumFacing.getFront(facing));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ROTATION);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int l = state.getBlock().getMetaFromState(state);

        EnumFacing side = EnumFacing.getFront(l & 7);
        boolean flatSign = ((l & 8) >> 3) == 1;
        boolean groundSign;

        if (flatSign) {
            groundSign = (l & 1) == 1;
            int rotation = (l & 6) >> 1;

            if (groundSign) {
                switch (rotation) {
                    case 1:
                        return SIGN_GROUND1_AABB;
                    case 2:
                        return SIGN_GROUND2_AABB;
                    case 3:
                        return SIGN_GROUND3_AABB;
                    default:
                        return SIGN_GROUND0_AABB;
                }
            } else {
                switch (rotation) {
                    case 1:
                        return SIGN_ROOF1_AABB;
                    case 2:
                        return SIGN_ROOF2_AABB;
                    case 3:
                        return SIGN_ROOF3_AABB;
                    default:
                        return SIGN_ROOF0_AABB;
                }
            }
        } else {
            switch (side) {
                case NORTH:
                default:
                    return SIGN_NORTH_AABB;
                case SOUTH:
                    return SIGN_SOUTH_AABB;
                case WEST:
                    return SIGN_WEST_AABB;
                case EAST:
                    return SIGN_EAST_AABB;
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        boolean flag;
        int rotation = world.getBlockState(pos).getValue(ROTATION);
        EnumFacing facing = EnumFacing.getFront(rotation & 7);


        boolean flatSign = ((rotation & 8) >> 3) == 1;
        boolean groundSign;

        if (flatSign) {
            groundSign = (rotation & 1) == 1;

            if (groundSign) {
                flag = !(world.getBlockState(pos.down()).getMaterial().isSolid());
            } else {
                flag = !(world.getBlockState(pos.up()).getMaterial().isSolid());
            }
        } else {

            flag = !(facing == EnumFacing.NORTH && world.getBlockState(pos.south()).getMaterial().isSolid());

            if (world.getBlockState(pos.offset(facing.getOpposite())).getMaterial().isSolid())
                flag = false;
        }

        if (flag) {
            world.setBlockToAir(pos);
        }
    }

    @Override
    @Optional.Method(modid = "passthroughsigns")
    public boolean canBePassed(World world, BlockPos pos, EnumPassableType type) {
        return type == EnumPassableType.WALL_BLOCK;
    }
}

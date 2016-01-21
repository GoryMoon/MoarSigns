package gory_moon.moarsigns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockMoarSignStanding extends BlockMoarSign {

    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

    public BlockMoarSignStanding(Material material, boolean freeStand) {
        super(material, freeStand);
        float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, 0));
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ROTATION, meta);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, ROTATION);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ROTATION);
    }

    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!world.getBlockState(pos.down()).getBlock().getMaterial().isSolid())
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }
}

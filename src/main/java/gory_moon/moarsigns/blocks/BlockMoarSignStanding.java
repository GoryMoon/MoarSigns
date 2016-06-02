package gory_moon.moarsigns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMoarSignStanding extends BlockMoarSign {

    public BlockMoarSignStanding(Material material, SoundType stepSound) {
        super(material, stepSound);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ROTATION, meta);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ROTATION);
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ROTATION);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
        if (!world.getBlockState(pos.down()).getMaterial().isSolid()) {
            world.setBlockToAir(pos);
        }
    }

}

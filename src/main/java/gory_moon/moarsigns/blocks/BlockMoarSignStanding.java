package gory_moon.moarsigns.blocks;

import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockMoarSignStanding extends BlockMoarSign {

    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
    public static final PropertyString TEXTURE = PropertyString.create("texture");

    public BlockMoarSignStanding(Material material, boolean freeStand) {
        super(material, freeStand);
        float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ROTATION, meta);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(pos);

        if (state instanceof IExtendedBlockState && tileEntity != null) {
            String texture = tileEntity.texture_name;
            return state.withProperty(TEXTURE, texture);
        }
        return state;
    }

    @Override
    protected BlockState createBlockState() {
        IProperty[] listedProperties = new IProperty[]{TEXTURE, ROTATION};
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[0];
        return new ExtendedBlockState(this,  listedProperties, unlistedProperties);
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

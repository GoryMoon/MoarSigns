package gory_moon.moarsigns.blocks;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.client.particle.EntityDiggingFXMoarSigns;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class BlockMoarSign extends BlockContainer {

    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

    public BlockMoarSign(Material material, boolean freeStand) {
        super(material);
        setUnlocalizedName("moarsign.sign");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
        setBlockBoundsBasedOnState(world, pos);
        return super.getSelectedBoundingBox(world, pos);
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random) {
        SignInfo signInfo = getSignInfo(world, pos);
        if (signInfo != null && signInfo.property != null) {
            signInfo.property.randomDisplayTick(world, pos, state, random);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        SignInfo signInfo = getSignInfo(world, pos);
        if (signInfo != null && signInfo.property != null) {
            signInfo.property.onEntityCollidedWithBlock(world, pos, state, entity);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        SignInfo signInfo = getSignInfo(world, pos);
        if (signInfo != null && signInfo.property != null) {
            return signInfo.property.onRightClick(world, pos, state, player, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer) {
        IBlockState state = world.getBlockState(pos);
        int i = 4;
        for (int j = 0; j < i; ++j)
        {
            for (int k = 0; k < i; ++k)
            {
                for (int l = 0; l < i; ++l)
                {
                    double d0 = (double)pos.getX() + ((double)j + 0.5D) / (double)i;
                    double d1 = (double)pos.getY() + ((double)k + 0.5D) / (double)i;
                    double d2 = (double)pos.getZ() + ((double)l + 0.5D) / (double)i;
                    effectRenderer.addEffect((new EntityDiggingFXMoarSigns(world, d0, d1, d2, d0 - (double)pos.getX() - 0.5D, d1 - (double)pos.getY() - 0.5D, d2 - (double)pos.getZ() - 0.5D, pos, state)).func_174846_a(pos));
                }
            }
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer) {
        BlockPos pos = target.getBlockPos();
        EnumFacing side = target.sideHit;
        IBlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();

        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        float f = 0.1F;
        double d0 = (double)i + world.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinX();
        double d1 = (double)j + world.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinY();
        double d2 = (double)k + world.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinZ();

        if (side == EnumFacing.DOWN)
        {
            d1 = (double)j + block.getBlockBoundsMinY() - (double)f;
        }

        if (side == EnumFacing.UP)
        {
            d1 = (double)j + block.getBlockBoundsMaxY() + (double)f;
        }

        if (side == EnumFacing.NORTH)
        {
            d2 = (double)k + block.getBlockBoundsMinZ() - (double)f;
        }

        if (side == EnumFacing.SOUTH)
        {
            d2 = (double)k + block.getBlockBoundsMaxZ() + (double)f;
        }

        if (side == EnumFacing.WEST)
        {
            d0 = (double)i + block.getBlockBoundsMinX() - (double)f;
        }

        if (side == EnumFacing.EAST)
        {
            d0 = (double)i + block.getBlockBoundsMaxX() + (double)f;
        }

        effectRenderer.addEffect((new EntityDiggingFXMoarSigns(world, d0, d1, d2, 0.0D, 0.0D, 0.0D, pos, iblockstate)).func_174846_a(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
        return true;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityMoarSign();
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
        TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(pos);
        String s = tileEntity.texture_name;
        s = s != null ? s : "null";
        return ModItems.sign.createMoarItemStack(s, tileEntity.isMetal);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        dropBlockAsItem(world, pos, state, 0);
        super.breakBlock(world, pos, state);
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileEntityMoarSign) {
            TileEntityMoarSign tileEntity = (TileEntityMoarSign) entity;
            if (tileEntity.removeNoDrop || tileEntity.texture_name == null) return ret;

            ret.add(ModItems.sign.createMoarItemStack(tileEntity.texture_name, tileEntity.isMetal));
        }
        return ret;
    }

    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileEntityMoarSign)
            ((TileEntityMoarSign) entity).removeNoDrop = player.capabilities.isCreativeMode;
        return super.removedByPlayer(world, pos, player, false);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return !this.func_181087_e(world, pos) && world.getBlockState(pos).getBlock().getMaterial().isSolid();
    }

    public SignInfo getSignInfo(World world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMoarSign) {
            TileEntityMoarSign sign = (TileEntityMoarSign) tileEntity;
            return SignRegistry.get(sign.texture_name);
        }

        return null;
    }
}

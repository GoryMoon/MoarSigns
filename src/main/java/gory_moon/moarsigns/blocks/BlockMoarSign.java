package gory_moon.moarsigns.blocks;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
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

    public BlockMoarSign(Material material, boolean freeStand) {
        super(material);
        setUnlocalizedName("moarsign.sign");
    }

    //TODO Icons
    /*
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        Boolean s = !((TileEntityMoarSign) world.getTileEntity(x, y, z)).isMetal;
        return s ? Blocks.planks.getIcon(world, x, y, z, side) : Blocks.iron_block.getIcon(world, x, y, z, side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2) {
        return Blocks.planks.getIcon(par1, par2);
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
    }
    */

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
        /*
        if (!state.getBlock().isAir(worldObj, pos) && !state.getBlock().addDestroyEffects(worldObj, pos, this))
        {
            state = state.getBlock().getActualState(state, this.worldObj, pos);
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
                        effectRenderer.addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, d0 - (double)pos.getX() - 0.5D, d1 - (double)pos.getY() - 0.5D, d2 - (double)pos.getZ() - 0.5D, state)).func_174846_a(pos));
                    }
                }
            }
        }

        byte b0 = 4;
        for (int j1 = 0; j1 < b0; ++j1) {
            for (int k1 = 0; k1 < b0; ++k1) {
                for (int l1 = 0; l1 < b0; ++l1) {
                    double d0 = (double) x + ((double) j1 + 0.5D) / (double) b0;
                    double d1 = (double) y + ((double) k1 + 0.5D) / (double) b0;
                    double d2 = (double) z + ((double) l1 + 0.5D) / (double) b0;
                    effectRenderer.addEffect((new EntityDiggingFXMoarSigns(world, d0, d1, d2, d0 - (double) x - 0.5D, d1 - (double) y - 0.5D, d2 - (double) z - 0.5D, x, y, z, this, meta, 3)).applyColourMultiplier(x, y, z));
                }
            }
        }

        return true;*/
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        /*IBlockState iblockstate = this.worldObj.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (block.getRenderType() != -1)
        {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            float f = 0.1F;
            double d0 = (double)i + this.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinX();
            double d1 = (double)j + this.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinY();
            double d2 = (double)k + this.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinZ();

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

            this.addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate)).func_174846_a(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
        }

        int x = target.blockX;
        int y = target.blockY;
        int z = target.blockZ;
        int side = target.sideHit;

        Block block = worldObj.getBlock(x, y, z);

        if (block != null) {

            float f = 0.1F;
            double d0 = (double) x + worldObj.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinX();
            double d1 = (double) y + worldObj.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinY();
            double d2 = (double) z + worldObj.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinZ();

            if (side == 0) {
                d1 = (double) y + block.getBlockBoundsMinY() - (double) f;
            }

            if (side == 1) {
                d1 = (double) y + block.getBlockBoundsMaxY() + (double) f;
            }

            if (side == 2) {
                d2 = (double) z + block.getBlockBoundsMinZ() - (double) f;
            }

            if (side == 3) {
                d2 = (double) z + block.getBlockBoundsMaxZ() + (double) f;
            }

            if (side == 4) {
                d0 = (double) x + block.getBlockBoundsMinX() - (double) f;
            }

            if (side == 5) {
                d0 = (double) x + block.getBlockBoundsMaxX() + (double) f;
            }

            effectRenderer.addEffect((new EntityDiggingFXMoarSigns(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, x, y, z, block, worldObj.getBlockMetadata(x, y, z), side)).applyColourMultiplier(x, y, z).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
        }
        return true;*/
        return false;
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
    protected boolean func_181087_e(World world, BlockPos pos) {
        return func_181086_a(world, pos, EnumFacing.NORTH) ||
                func_181086_a(world, pos, EnumFacing.SOUTH) ||
                func_181086_a(world, pos, EnumFacing.WEST) ||
                func_181086_a(world, pos, EnumFacing.EAST) ||
                func_181086_a(world, pos, EnumFacing.DOWN) ||
                func_181086_a(world, pos, EnumFacing.UP);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return !this.func_181087_e(world, pos) && super.canPlaceBlockAt(world, pos);
/*
        boolean flatSign = ((meta & 8) >> 3) == 1;
        int side = flatSign ? meta & 1 : meta & 7;
        switch (side) {
            case 0:
                y++;
                break;
            case 1:
                y--;
                break;
            case 2:
                z++;
                break;
            case 3:
                z--;
                break;
            case 4:
                x++;
                break;
            case 5:
                x--;
                break;
        }

        return !super.canPlaceBlockAt(world, pos) && world.getBlockState(pos).getBlock().getMaterial().isSolid();*/
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

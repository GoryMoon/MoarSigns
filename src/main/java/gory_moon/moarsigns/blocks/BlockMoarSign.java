package gory_moon.moarsigns.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.client.particle.EntityDiggingFXMoarSigns;
import gory_moon.moarsigns.items.Items;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockMoarSign extends BlockContainer {

    private boolean isFreestanding;

    protected BlockMoarSign(int id, Material material, boolean freeStand) {
        super(id, material);
        setUnlocalizedName("moarsign.sign");

        isFreestanding = freeStand;
        float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        System.out.println("X: " + x + ", Y: " + y + ", Z: " + z);
        Boolean s = !((TileEntityMoarSign) world.getBlockTileEntity(x, y, z)).isMetal;
        return s ? Block.planks.getBlockTexture(world, x, y, z, side): Block.blockIron.getBlockTexture(world, x, y, z, side);
    }

    @Override
    public float getBlockHardness(World par1World, int par2, int par3, int par4) {
        return super.getBlockHardness(par1World, par2, par3, par4);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2) {
        return Block.planks.getIcon(par1, par2);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {}

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        if (!this.isFreestanding) {
            int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
            float f = 0.28125F;
            float f1 = 0.78125F;
            float f2 = 0.0F;
            float f3 = 1.0F;
            float f4 = 0.125F;
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            if (l == 2) {
                setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
            }

            if (l == 3) {
                setBlockBounds(f2, f, 0.0F, f3, f1, f4);
            }

            if (l == 4) {
                setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
            }

            if (l == 5) {
                setBlockBounds(0.0F, f, f2, f4, f1, f3);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        byte b0 = 4;

        for (int j1 = 0; j1 < b0; ++j1)
        {
            for (int k1 = 0; k1 < b0; ++k1)
            {
                for (int l1 = 0; l1 < b0; ++l1)
                {
                    double d0 = (double)x + ((double)j1 + 0.5D) / (double)b0;
                    double d1 = (double)y + ((double)k1 + 0.5D) / (double)b0;
                    double d2 = (double)z + ((double)l1 + 0.5D) / (double)b0;
                    effectRenderer.addEffect((new EntityDiggingFXMoarSigns(world, d0, d1, d2, d0 - (double)x - 0.5D, d1 - (double)y - 0.5D, d2 - (double)z - 0.5D, x, y, z, this, meta, 3)).applyColourMultiplier(x, y, z));
                }
            }
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        int x = target.blockX;
        int y = target.blockY;
        int z = target.blockZ;
        int side = target.sideHit;

        int i1 = worldObj.getBlockId(x, y, z);

        if (i1 != 0) {
            Block block = Block.blocksList[i1];
            float f = 0.1F;
            double d0 = (double)x + worldObj.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinX();
            double d1 = (double)y + worldObj.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinY();
            double d2 = (double)z + worldObj.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (double)(f * 2.0F)) + (double)f + block.getBlockBoundsMinZ();

            if (side == 0) {
                d1 = (double)y + block.getBlockBoundsMinY() - (double)f;
            }

            if (side == 1) {
                d1 = (double)y + block.getBlockBoundsMaxY() + (double)f;
            }

            if (side == 2) {
                d2 = (double)z + block.getBlockBoundsMinZ() - (double)f;
            }

            if (side == 3)  {
                d2 = (double)z + block.getBlockBoundsMaxZ() + (double)f;
            }

            if (side == 4) {
                d0 = (double)x + block.getBlockBoundsMinX() - (double)f;
            }

            if (side == 5) {
                d0 = (double)x + block.getBlockBoundsMaxX() + (double)f;
            }

            effectRenderer.addEffect((new EntityDiggingFXMoarSigns(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, x, y, z, block, worldObj.getBlockMetadata(x, y, z), side)).applyColourMultiplier(x, y, z).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
        }
        return true;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World par1World) {
        return new TileEntityMoarSign();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
        boolean flag = false;

        if (isFreestanding) {
            if (!world.getBlockMaterial(x, y - 1, z).isSolid()) {
                flag = true;
            }
        } else {
            int i1 = world.getBlockMetadata(x, y, z);

            flag = !(i1 == 2 && world.getBlockMaterial(x, y, z + 1).isSolid());

            if (i1 == 3 && world.getBlockMaterial(x, y, z - 1).isSolid()) {
                flag = false;
            }

            if (i1 == 4 && world.getBlockMaterial(x + 1, y, z).isSolid()) {
                flag = false;
            }

            if (i1 == 5 && world.getBlockMaterial(x - 1, y, z).isSolid()) {
                flag = false;
            }
        }

        if (flag) {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        TileEntityMoarSign tileEntity = (TileEntityMoarSign)world.getBlockTileEntity(x, y, z);
        String s = tileEntity.getTexture_name();
        s = s != null ? s: "null";
        return Items.sign.createMoarItemStack(s, tileEntity.isMetal);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta) {
        dropBlockAsItem(world, x, y, z, oldMeta, 0);
        super.breakBlock(world, x, y, z, oldId, oldMeta);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {

        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        TileEntityMoarSign tileEntity = (TileEntityMoarSign)world.getBlockTileEntity(x, y, z);
        if (tileEntity != null) ret.add(Items.sign.createMoarItemStack(tileEntity.getTexture_name(), tileEntity.isMetal));
        return ret;
    }

}

package gory_moon.moarsigns.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.client.particle.EntityDiggingFXMoarSigns;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockMoarSign extends BlockContainer {

    public boolean isFreestanding;

    protected BlockMoarSign(Material material, boolean freeStand) {
        super(material);

        setBlockName("moarsign.sign");
        isFreestanding = freeStand;
        float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

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

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityMoarSign) {
            TileEntityMoarSign sign = (TileEntityMoarSign) entity;
            SignInfo info = SignRegistry.get(sign.texture_name);
            if (info != null && info.property != null) info.property.randomDisplayTick(world, x, y, z, random);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityMoarSign) {
            TileEntityMoarSign sign = (TileEntityMoarSign) tileEntity;
            SignInfo info = SignRegistry.get(sign.texture_name);
            if (info != null && info.property != null) info.property.onEntityCollidedWithBlock(world, x, y, z, entity);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityMoarSign) {
            TileEntityMoarSign sign = (TileEntityMoarSign) tileEntity;
            SignInfo info = SignRegistry.get(sign.texture_name);
            if (info != null && info.property != null) return info.property.onRightClick(world, x, y, z, player, side, hitX, hitY, hitZ);
        }
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        if (!isFreestanding) {
            int l = world.getBlockMetadata(x, y, z);
            float f = 0.28125F;
            float f1 = 0.78125F;
            float f2 = 0.0F;
            float f3 = 1.0F;
            float f4 = 0.125F;
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            int side = l & 7;
            boolean flatSign = ((l & 8) >> 3) == 1;
            boolean groundSign;

            if (flatSign) {
                groundSign = (l & 1) == 1;

                if (groundSign) {
                    int rotation = (l & 6) >> 1;
                    setBlockBounds(f2, f2, f - 0.01F, f3, f4, f1 - 0.01F);

                    if (rotation == 1) setBlockBounds(f - 0.05F, f2, f2, f1 - 0.05F, f4, f3);
                    else if (rotation == 2) setBlockBounds(f2, f2, f - 0.05F, f3, f4, f1 - 0.05F);
                    else if (rotation == 3) setBlockBounds(f - 0.01F, f2, f2, f1 - 0.01F, f4, f3);
                } else {
                    int rotation = (l & 6) >> 1;
                    setBlockBounds(f2, f3 - f4, f - 0.05F, f3, f3, f1 - 0.05F);

                    if (rotation == 1) setBlockBounds(f - 0.01F, f3 - f4, f2, f1 - 0.01F, f3, f3);
                    else if (rotation == 2) setBlockBounds(f2, f3 - f4, f - 0.01F, f3, f3, f1 - 0.01F);
                    else if (rotation == 3) setBlockBounds(f - 0.05F, f3 - f4, f2, f1 - 0.05F, f3, f3);
                }
            } else {
                if (side == 2) {
                    setBlockBounds(f2, f- 0.01F, f3 - f4, f3, f1- 0.01F, f3);
                } else if (side == 3) {
                    setBlockBounds(f2, f- 0.01F, f2, f3, f1- 0.01F, f4);
                } else if (side == 4) {
                    setBlockBounds(f3 - f4, f- 0.01F, f2, f3, f1 - 0.01F, f3);
                } else if (side == 5) {
                    setBlockBounds(f2, f- 0.01F, f2, f4, f1- 0.01F, f3);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
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

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
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
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityMoarSign();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        boolean flag = false;

        if (this.isFreestanding) {
            if (!world.getBlock(x, y - 1, z).getMaterial().isSolid()) {
                flag = true;
            }
        } else {
            int i = world.getBlockMetadata(x, y, z);
            int i1 = i & 7;


            boolean flatSign = ((i & 8) >> 3) == 1;
            boolean groundSign;

            if (flatSign) {
                groundSign = (i & 1) == 1;

                if (groundSign) {
                    flag = !(world.getBlock(x, y - 1, z).getMaterial().isSolid());
                } else {
                    flag = !(world.getBlock(x, y + 1, z).getMaterial().isSolid());
                }
            } else {

                flag = !(i1 == 2 && world.getBlock(x, y, z + 1).getMaterial().isSolid());

                if (i1 == 2 && world.getBlock(x, y, z + 1).getMaterial().isSolid()) {
                    flag = false;
                }

                if (i1 == 3 && world.getBlock(x, y, z - 1).getMaterial().isSolid()) {
                    flag = false;
                }

                if (i1 == 4 && world.getBlock(x + 1, y, z).getMaterial().isSolid()) {
                    flag = false;
                }

                if (i1 == 5 && world.getBlock(x - 1, y, z).getMaterial().isSolid()) {
                    flag = false;
                }
            }
        }

        if (flag) {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(x, y, z);
        String s = tileEntity.texture_name;
        s = s != null ? s : "null";
        return ModItems.sign.createMoarItemStack(s, tileEntity.isMetal);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMeta) {
        dropBlockAsItem(world, x, y, z, oldMeta, 0);
        super.breakBlock(world, x, y, z, oldBlock, oldMeta);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {

        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityMoarSign) {
            TileEntityMoarSign tileEntity = (TileEntityMoarSign) entity;
            if (tileEntity.isRemovedByPlayerAndCreative) return ret;

            ret.add(ModItems.sign.createMoarItemStack(tileEntity.texture_name, tileEntity.isMetal));
        }
        return ret;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityMoarSign)
            ((TileEntityMoarSign) entity).isRemovedByPlayerAndCreative = player.capabilities.isCreativeMode;
        return super.removedByPlayer(world, player, x, y, z, false);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z, int meta) {
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

        return !super.canPlaceBlockAt(world, x, y, z) && world.getBlock(x, y, z).getMaterial().isSolid();
    }
}

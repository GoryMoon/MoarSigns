package gory_moon.moarsigns.blocks;

import cofh.api.item.IToolHammer;
import crazypants.enderio.api.tool.ITool;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.client.particle.EntityDiggingFXMoarSigns;
import gory_moon.moarsigns.items.ItemSignToolbox;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.Utils;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

@Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockMoarSign extends BlockContainer implements IProbeInfoAccessor {

    public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
    protected static final AxisAlignedBB SIGN_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    public BlockMoarSign(Material material, SoundType stepSound) {
        super(material);
        setUnlocalizedName("moarsign.sign");
        setSoundType(stepSound);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SIGN_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canSpawnInBlock() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityMoarSign();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(ROTATION, rot.rotate(state.getValue(ROTATION), 16));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withProperty(ROTATION, mirrorIn.mirrorRotation(state.getValue(ROTATION), 16));
    }

    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        SignInfo signInfo = getSignInfo(world, pos);
        if (signInfo != null && signInfo.property != null) {
            signInfo.property.randomDisplayTick(state, world, pos, rand);
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (heldItem != null && heldItem.getItem() instanceof IToolHammer && ((IToolHammer) heldItem.getItem()).isUsable(heldItem, player, pos.getX(), pos.getY(), pos.getZ())) {
            ItemSignToolbox.doRotate(world, pos, player);
            ((IToolHammer) heldItem.getItem()).toolUsed(heldItem, player, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        if (heldItem != null && heldItem.getItem() instanceof ITool && ((ITool) heldItem.getItem()).canUse(heldItem, player, pos)) {
            ItemSignToolbox.doRotate(world, pos, player);
            ((ITool) heldItem.getItem()).used(heldItem, player, pos);
            return true;
        }

        SignInfo signInfo = getSignInfo(world, pos);
        boolean returnVal = true;
        if (signInfo != null && signInfo.property != null) {
            returnVal = signInfo.property.onRightClick(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
        }

        if (world.isRemote) {
            return returnVal;
        } else {
            TileEntity tileentity = world.getTileEntity(pos);
            return returnVal && (tileentity instanceof TileEntityMoarSign && ((TileEntityMoarSign) tileentity).executeCommand(player));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager effectRenderer) {
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
                    effectRenderer.addEffect((new EntityDiggingFXMoarSigns(world, d0, d1, d2, d0 - (double)pos.getX() - 0.5D, d1 - (double)pos.getY() - 0.5D, d2 - (double)pos.getZ() - 0.5D, pos, state)).setBlockPos(pos));
                }
            }
        }

        return true;
    }

    @Override
    public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager effectRenderer) {
        BlockPos pos = target.getBlockPos();
        EnumFacing side = target.sideHit;
        IBlockState iblockstate = world.getBlockState(pos);

        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        float f = 0.1F;
        AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(world, pos);
        double d0 = (double)i + world.rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - (double)(f * 2.0F)) + (double)f + axisalignedbb.minX;
        double d1 = (double)j + world.rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - (double)(f * 2.0F)) + (double)f + axisalignedbb.minY;
        double d2 = (double)k + world.rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - (double)(f * 2.0F)) + (double)f + axisalignedbb.minZ;

        if (side == EnumFacing.DOWN)
        {
            d1 = (double)j + axisalignedbb.minY - (double)f;
        }

        if (side == EnumFacing.UP)
        {
            d1 = (double)j + axisalignedbb.maxY + (double)f;
        }

        if (side == EnumFacing.NORTH)
        {
            d2 = (double)k + axisalignedbb.minZ - (double)f;
        }

        if (side == EnumFacing.SOUTH)
        {
            d2 = (double)k + axisalignedbb.maxZ + (double)f;
        }

        if (side == EnumFacing.WEST)
        {
            d0 = (double)i + axisalignedbb.minX - (double)f;
        }

        if (side == EnumFacing.EAST)
        {
            d0 = (double)i + axisalignedbb.maxX + (double)f;
        }

        effectRenderer.addEffect((new EntityDiggingFXMoarSigns(world, d0, d1, d2, 0.0D, 0.0D, 0.0D, pos, iblockstate)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
        return true;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(pos);
        String s = tileEntity.texture_name;
        s = s != null ? s : "null";
        return ModItems.SIGN.createMoarItemStack(s, tileEntity.isMetal);
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

            ret.add(ModItems.SIGN.createMoarItemStack(tileEntity.texture_name, tileEntity.isMetal));
        }
        return ret;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileEntityMoarSign)
            ((TileEntityMoarSign) entity).removeNoDrop = player.capabilities.isCreativeMode;
        return super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return !this.hasInvalidNeighbor(world, pos) && super.canPlaceBlockAt(world, pos);
    }

    public SignInfo getSignInfo(World world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMoarSign) {
            TileEntityMoarSign sign = (TileEntityMoarSign) tileEntity;
            return SignRegistry.get(sign.texture_name);
        }

        return null;
    }

    // The One Probe Integration
    @Method(modid = "theoneprobe")
    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        SignInfo info = getSignInfo(world, data.getPos());
        String modName = info.activateTag.equals(SignRegistry.ALWAYS_ACTIVE_TAG) ? "Minecraft" : info.activateTag;

        probeInfo.text(Colors.LIGHTGRAY + Localization.ITEM.SIGN.MATERIAL_ORIGIN.translate(Colors.WHITE + Utils.getModName(modName)));

        if (mode.equals(ProbeMode.EXTENDED) || mode.equals(ProbeMode.DEBUG)) {
            probeInfo.text(Colors.LIGHTGRAY + Localization.ITEM.SIGN.MATERIAL.translate(Colors.WHITE + info.material.materialName));
        }
    }
}

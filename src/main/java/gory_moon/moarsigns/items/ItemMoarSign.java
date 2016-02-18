package gory_moon.moarsigns.items;

import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemMoarSign extends Item {

    public ItemMoarSign() {
        maxStackSize = 16;
        setCreativeTab(MoarSignsCreativeTab.tabMS);
        setUnlocalizedName("moarsigns");
        hasSubtypes = true;
    }

    public static String getTextureFromNBTFull(NBTTagCompound compound) {
        return compound != null && compound.hasKey("SignTexture") ? compound.getString("SignTexture") : "";
    }

    public static String getTextureFromNBT(NBTTagCompound compound) {
        String texture = getTextureFromNBTFull(compound);
        if (texture.contains("\\")) texture = texture.split("\\\\")[1];
        if (texture.contains("/")) texture = texture.split("/")[1];
        return texture;
    }

    public static SignInfo getInfo(NBTTagCompound compound) {
        String texture = getTextureFromNBTFull(compound);
        return SignRegistry.get(texture);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        SignInfo info = SignRegistry.get(getTextureFromNBTFull(stack.getTagCompound()));
        if (info == null) return super.getUnlocalizedName() + ".sign.error";
        return super.getUnlocalizedName() + ".sign." + (info.material.path.equals("") ? "" : info.material.path.replace("/", "") + ".") + getTextureFromNBT(stack.getTagCompound());
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        getSubItemStacks(list);
    }

    @SuppressWarnings("unchecked")
    public void getSubItemStacks(List list) {
        List<SignInfo> signRegistry = SignRegistry.getActivatedSignRegistry();

        for (SignInfo info : signRegistry) {
            list.add(createMoarItemStack(info.material.path + info.itemName, info.isMetal));
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        SignInfo info = SignRegistry.get(getTextureFromNBTFull(stack.getTagCompound()));
        if (info != null) {
            return info.rarity;
        }
        return EnumRarity.COMMON;
    }

    public ItemStack createMoarItemStack(String signName, boolean isMetal) {
        ItemStack itemStack = new ItemStack(this, 1, (isMetal ? 1 : 0));
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("SignTexture", signName.replace("\\", "/"));
        itemStack.setTagCompound(compound);
        return itemStack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.getBlockState(pos).getBlock().getMaterial().isSolid()) {
            return false;
        } else {
            pos = pos.offset(side);

            if (!player.canPlayerEdit(pos, side, stack) || !Blocks.signStandingWood.canPlaceBlockAt(world, pos)) {
                return false;
            } else if (world.isRemote) {
                return true;
            } else {

                SignInfo info = getInfo(stack.getTagCompound());
                if (info == null) return false;
                if (side == EnumFacing.UP && !player.isSneaking()) {
                    int rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    if (!info.isMetal) world.setBlockState(pos, Blocks.signStandingWood.getDefaultState().withProperty(BlockMoarSign.ROTATION, rotation), 3);
                    else world.setBlockState(pos, Blocks.signStandingMetal.getDefaultState().withProperty(BlockMoarSign.ROTATION, rotation), 3);

                } else {
                    int finalRotation = side.getIndex();
                    if (side == EnumFacing.DOWN || side == EnumFacing.UP) {
                        int rotation = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                        finalRotation += (rotation << 1);
                        finalRotation += 8;
                    }
                    if (!info.isMetal) world.setBlockState(pos, Blocks.signWallWood.getDefaultState().withProperty(BlockMoarSign.ROTATION, finalRotation), 3);
                    else world.setBlockState(pos, Blocks.signWallMetal.getDefaultState().withProperty(BlockMoarSign.ROTATION, finalRotation), 3);
                }

                if (!player.capabilities.isCreativeMode) --stack.stackSize;
                TileEntity tileEntity = world.getTileEntity(pos);

                if (tileEntity instanceof TileEntityMoarSign && !ItemBlock.setTileEntityNBT(world, player, pos, stack)) {
                    TileEntityMoarSign te = (TileEntityMoarSign) tileEntity;
                    String texture = getTextureFromNBTFull(stack.getTagCompound());

                    te.isMetal = info.isMetal;
                    te.setPlayer(player);
                    te.setResourceLocation(texture);

                    boolean moving = stack.getTagCompound().hasKey(ItemSignToolbox.SIGN_MOVING_TAG) && stack.getTagCompound().getBoolean(ItemSignToolbox.SIGN_MOVING_TAG);
                    PacketHandler.INSTANCE.sendTo(new MessageSignOpenGui(te, moving), (EntityPlayerMP) player);
                }

                return true;
            }
        }
    }


    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean extraInfo) {
        SignInfo info = getInfo(stack.getTagCompound());
        if (info != null) {
            String modName = info.activateTag.equals(SignRegistry.ALWAYS_ACTIVE_TAG) ? "Minecraft" : info.activateTag;
            list.add(Localization.ITEM.SIGN.MATERIAL_ORIGIN.translate(Colors.WHITE + Utils.getModName(modName)));
            if (extraInfo) {
                list.add(Localization.ITEM.SIGN.MATERIAL.translate(Colors.WHITE + info.material.materialName));
            }
        } else {
            list.add(Colors.RED + Localization.ITEM.SIGN.ERROR.translate(Colors.RED.toString()));
        }
    }
}

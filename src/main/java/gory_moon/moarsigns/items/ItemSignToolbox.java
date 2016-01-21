package gory_moon.moarsigns.items;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.lib.ToolBoxModes;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import java.util.Collections;
import java.util.List;

import static gory_moon.moarsigns.lib.ToolBoxModes.EXCHANGE_MODE;
import static gory_moon.moarsigns.lib.ToolBoxModes.PREVIEW_MODE;

public class ItemSignToolbox extends Item {

    public static final String SIGN_MOVING_TAG = "SignMoving";
    public static final String NBT_UNLOCALIZED_NAME = "SignUnlocalizedName";
    //private IIcon[] icons = new IIcon[ToolBoxModes.values().length];

    public ItemSignToolbox() {
        setUnlocalizedName("moarsigns.signtoolbox");
        setCreativeTab(MoarSignsCreativeTab.tabMS);
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    //TODO Icons
    /*
    @Override
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < ToolBoxModes.values().length; i++) {
            icons[i] = register.registerIcon(Info.TEXTURE_LOCATION + ":" + "toolbox/" + ToolBoxModes.values()[i].toString().toLowerCase());
        }
    }

    @Override
    public IIcon getIconFromDamage(int itemDamage) {
        if (itemDamage > 5 && !isMoving(itemDamage)) return null;
        int mode = isMoving(itemDamage) ? 2 : itemDamage;
        return icons[mode];
    }*/

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            MovingObjectPosition.MovingObjectType hit = FMLClientHandler.instance().getClient().objectMouseOver.typeOfHit;

            if (hit == MovingObjectPosition.MovingObjectType.MISS) {
                int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
                if (player.isSneaking() && !isMoving(stack.getItemDamage())) {
                    return rotateModes(stack);
                } else if (ToolBoxModes.values()[mode] == EXCHANGE_MODE) {
                    doExchange(world, BlockPos.ORIGIN, player);
                } else if (ToolBoxModes.values()[mode] == PREVIEW_MODE) {
                    doPreview(world, BlockPos.ORIGIN, player);
                }
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
            switch (ToolBoxModes.values()[mode]) {
                case EDIT_MODE:
                    doEdit(world, pos, player);
                    break;
                case ROTATE_MODE:
                    doRotate(world, pos);
                    break;
                case MOVE_MODE:
                    return doMove(world, pos, stack, player, side, hitX, hitY, hitZ);
                case COPY_MODE:
                    doCopy(world, pos, stack);
                    break;
                case EXCHANGE_MODE:
                    doExchange(world, pos, player);
                    return true;
                case PREVIEW_MODE:
                    doPreview(world, pos, player);
                    return true;
            }
        }
        return false;
    }

    private void doRotate(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() instanceof BlockMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) world.getTileEntity(pos));
        }
    }

    private void doEdit(World world, BlockPos pos, EntityPlayer player) {
        if (world.getBlockState(pos).getBlock() instanceof BlockMoarSign) {
            TileEntity entity = world.getTileEntity(pos);
            if (entity instanceof TileEntityMoarSign) {
                TileEntityMoarSign tileEntity = (TileEntityMoarSign) entity;
                PacketHandler.INSTANCE.sendTo(new MessageSignOpenGui(tileEntity, false), (EntityPlayerMP) player);
            }
        }
    }

    private void doCopy(World world, BlockPos pos, ItemStack stack) {
        NBTTagCompound signInfo = stack.getTagCompound();
        if (GuiScreen.isCtrlKeyDown()) {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof TileEntityMoarSign) {
                signInfo = new NBTTagCompound();
                tileEntity.writeToNBT(signInfo);
                signInfo.removeTag(TileEntityMoarSign.NBT_TEXTURE_TAG);
                signInfo.removeTag(TileEntityMoarSign.NBT_METAL_TAG);
                stack.setTagCompound(signInfo);
            }
        } else if (signInfo != null) {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof TileEntityMoarSign) {
                tileEntity.readFromNBT(signInfo);
                tileEntity.setPos(pos);
                world.markBlockForUpdate(pos);
            }
        }
    }

    private boolean doMove(World world, BlockPos pos, ItemStack stack, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        boolean val = true;
        NBTTagCompound signInfo = stack.getTagCompound();

        if (!isMoving(stack.getItemDamage())) {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof TileEntityMoarSign) {
                TileEntityMoarSign tileEntityMoarSign = (TileEntityMoarSign) tileEntity;
                signInfo = new NBTTagCompound();
                tileEntity.writeToNBT(signInfo);
                ItemStack signStack = ModItems.sign.createMoarItemStack(tileEntityMoarSign.texture_name, tileEntityMoarSign.isMetal);
                String unlocalizedName = signStack.getUnlocalizedName() + ".name";
                signInfo.setString(NBT_UNLOCALIZED_NAME, unlocalizedName);

                stack = toggleMoving(stack);
                tileEntityMoarSign.removeNoDrop = true;
                world.setBlockToAir(pos);
            }
        } else {
            String texture = signInfo.getString(TileEntityMoarSign.NBT_TEXTURE_TAG);
            boolean isMetal = signInfo.getBoolean(TileEntityMoarSign.NBT_METAL_TAG);

            ItemStack moarSignsStack = ModItems.sign.createMoarItemStack(texture, isMetal);
            moarSignsStack.getTagCompound().setBoolean(SIGN_MOVING_TAG, true);
            val = ModItems.sign.onItemUse(moarSignsStack, player, world, pos, side, hitX, hitY, hitZ);

            if (val) {
                pos = pos.offset(side);

                signInfo.removeTag(NBT_UNLOCALIZED_NAME);
                TileEntityMoarSign entityMoarSign = (TileEntityMoarSign) world.getTileEntity(pos);
                entityMoarSign.readFromNBT(signInfo);
                entityMoarSign.setPos(pos);
                world.markBlockForUpdate(pos);

                signInfo = null;
                stack = toggleMoving(stack);
            }
        }

        stack.setTagCompound(signInfo);
        return !val;
    }

    private void doExchange(World world, BlockPos pos, EntityPlayer player) {
        FMLNetworkHandler.openGui(player, MoarSigns.instance, GuiHandler.EXCHANGE, world, pos.getX(), pos.getY(), pos.getZ());
    }

    private void doPreview(World world, BlockPos pos, EntityPlayer player) {
        FMLNetworkHandler.openGui(player, MoarSigns.instance, GuiHandler.PREVIEW, world, pos.getX(), pos.getY(), pos.getZ());
    }

    private ItemStack rotateModes(ItemStack stack) {
        int mode = stack.getItemDamage();
        mode = mode + 1 >= 6 ? 0 : mode + 1;
        stack.setItemDamage(mode);
        return stack;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() > 5 && !isMoving(stack.getItemDamage())) stack.setItemDamage(0);
        int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
        return super.getUnlocalizedName(stack) + "." + ToolBoxModes.values()[mode].toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean extraInfo) {
        GameSettings gameSettings = FMLClientHandler.instance().getClient().gameSettings;
        String str = Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.CHANGE.translate(Colors.LIGHTGRAY + "[" + GameSettings.getKeyDisplayString(gameSettings.keyBindSneak.getKeyCode()) + "]" + Colors.GRAY.toString());

        int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
        switch (ToolBoxModes.values()[mode]) {
            case COPY_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.COPY.translate(Colors.LIGHTGRAY.toString() + "[", (Minecraft.isRunningOnMac ? "0" : "1"), "]" + Colors.GRAY.toString(), "\n" + Colors.LIGHTGRAY.toString() + "[");
                if (stack.getTagCompound() != null)
                    str += "\n" + Colors.LIGHTGRAY + Localization.ITEM.SIGNTOOLBOX.CURRENT_TEXT.translate() + getFormattedData(stack.getTagCompound());
                break;
            case MOVE_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.MOVE.translate(Colors.LIGHTGRAY.toString() + "[", "]" + Colors.GRAY.toString(), "\n" + Colors.GRAY.toString(), "\n" + Colors.RED.toString());
                if (stack.getTagCompound() != null) {
                    String unlocName = stack.getTagCompound().getString(NBT_UNLOCALIZED_NAME);
                    String signName = StatCollector.translateToLocal(unlocName);
                    str += "\n" + Colors.LIGHTGRAY + Localization.ITEM.SIGNTOOLBOX.CURRENT_SIGN.translate() + " " + Colors.WHITE + signName + "\n" + Colors.LIGHTGRAY + Localization.ITEM.SIGNTOOLBOX.CURRENT_TEXT.translate() + getFormattedData(stack.getTagCompound());
                }
                break;
            case EXCHANGE_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.EXCHANGE.translate("\n" + Colors.GRAY.toString());
                break;
            case PREVIEW_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.PREVIEW.translate("\n" + Colors.GRAY.toString());
                break;
            default:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.values()[stack.getItemDamage() + 1].translate(Colors.LIGHTGRAY.toString(), Colors.GRAY.toString());
        }

        String[] strList = str.split("\n");
        for (int i = 0; i < strList.length; i++) strList[i] = strList[i].trim();
        Collections.addAll(list, strList);
    }

    private String getFormattedData(NBTTagCompound compound) {
        String s = "\n";

        for (int i = 0; i < 4; i++) {
            s += Colors.WHITE + "[" + Colors.GRAY + compound.getString("Text" + (i + 1)) + Colors.WHITE + "]\n";
        }

        return s;
    }

    private boolean isMoving(int itemDamage) {
        return (itemDamage & 7) == 7;
    }

    private ItemStack toggleMoving(ItemStack stack) {
        if (stack.getItemDamage() == 2) {
            stack.setItemDamage(7);
        } else if (isMoving(stack.getItemDamage())) {
            stack.setItemDamage(2);
        }
        return stack;
    }

}

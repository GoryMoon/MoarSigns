package gory_moon.moarsigns.items;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.lib.ToolBoxModes;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

import static gory_moon.moarsigns.lib.ToolBoxModes.EXCHANGE_MODE;
import static gory_moon.moarsigns.lib.ToolBoxModes.PREVIEW_MODE;

public class ItemSignToolbox extends Item {

    public static final String SIGN_MOVING_TAG = "SignMoving";
    public static final String NBT_UNLOCALIZED_NAME = "SignUnlocalizedName";
    private IIcon[] icons = new IIcon[ToolBoxModes.values().length];

    public ItemSignToolbox() {
        setUnlocalizedName("moarsign.signtoolbox");
        setCreativeTab(MoarSignsCreativeTab.tabMS);
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

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
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            MovingObjectPosition.MovingObjectType hit = FMLClientHandler.instance().getClient().objectMouseOver.typeOfHit;

            if (hit == MovingObjectPosition.MovingObjectType.MISS) {
                int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
                if (player.isSneaking() && !isMoving(stack.getItemDamage())) {
                    return rotateModes(stack);
                } else if (ToolBoxModes.values()[mode] == EXCHANGE_MODE) {
                    doExchange(world, 0, 0, 0, player);
                } else if (ToolBoxModes.values()[mode] == PREVIEW_MODE) {
                    doPreview(world, 0, 0, 0, player);
                }
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
            switch (ToolBoxModes.values()[mode]) {
                case EDIT_MODE:
                    doEdit(world, x, y, z, player);
                    break;
                case ROTATE_MODE:
                    doRotate(world, x, y, z);
                    break;
                case MOVE_MODE:
                    return doMove(world, x, y, z, stack, player, side, hitX, hitY, hitZ);
                case COPY_MODE:
                    doCopy(world, x, y, z, stack);
                    break;
                case EXCHANGE_MODE:
                    doExchange(world, x, y, z, player);
                    return true;
                case PREVIEW_MODE:
                    doPreview(world, x, y, z, player);
                    return true;
            }
        }
        return false;
    }

    private void doRotate(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z) instanceof BlockMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) world.getTileEntity(x, y, z));
        }
    }

    private void doEdit(World world, int x, int y, int z, EntityPlayer player) {
        if (world.getBlock(x, y, z) instanceof BlockMoarSign) {
            TileEntity entity = world.getTileEntity(x, y, z);
            if (entity instanceof TileEntityMoarSign) {
                TileEntityMoarSign tileEntity = (TileEntityMoarSign) entity;
                PacketHandler.INSTANCE.sendTo(new MessageSignOpenGui(tileEntity, false), (EntityPlayerMP) player);
            }
        }
    }

    private void doCopy(World world, int x, int y, int z, ItemStack stack) {
        NBTTagCompound signInfo = stack.getTagCompound();
        if (GuiScreen.isCtrlKeyDown()) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityMoarSign) {
                signInfo = new NBTTagCompound();
                tileEntity.writeToNBT(signInfo);
                signInfo.removeTag(TileEntityMoarSign.NBT_TEXTURE_TAG);
                signInfo.removeTag(TileEntityMoarSign.NBT_METAL_TAG);
                stack.setTagCompound(signInfo);
            }
        } else if (signInfo != null) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityMoarSign) {
                tileEntity.readFromNBT(signInfo);
                tileEntity.xCoord = x;
                tileEntity.yCoord = y;
                tileEntity.zCoord = z;
                world.markBlockForUpdate(x, y, z);
            }
        }
    }

    private boolean doMove(World world, int x, int y, int z, ItemStack stack, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        boolean val = true;
        NBTTagCompound signInfo = stack.getTagCompound();

        if (!isMoving(stack.getItemDamage())) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityMoarSign) {
                TileEntityMoarSign tileEntityMoarSign = (TileEntityMoarSign) tileEntity;
                signInfo = new NBTTagCompound();
                tileEntity.writeToNBT(signInfo);
                ItemStack signStack = ModItems.sign.createMoarItemStack(tileEntityMoarSign.texture_name, tileEntityMoarSign.isMetal);
                String unlocalizedName = signStack.getUnlocalizedName() + ".name";
                signInfo.setString(NBT_UNLOCALIZED_NAME, unlocalizedName);

                stack = toggleMoving(stack);
                tileEntityMoarSign.removeNoDrop = true;
                world.setBlock(x, y, z, Blocks.air);
            }
        } else {
            String texture = signInfo.getString(TileEntityMoarSign.NBT_TEXTURE_TAG);
            boolean isMetal = signInfo.getBoolean(TileEntityMoarSign.NBT_METAL_TAG);

            ItemStack moarSignsStack = ModItems.sign.createMoarItemStack(texture, isMetal);
            moarSignsStack.getTagCompound().setBoolean(SIGN_MOVING_TAG, true);
            val = ModItems.sign.onItemUse(moarSignsStack, player, world, x, y, z, side, hitX, hitY, hitZ);

            if (val) {
                PlacedCoord coord = new PlacedCoord(x, y, z, side);
                coord = Utils.calculatePlaceSideCoord(coord);
                x = coord.x;
                y = coord.y;
                z = coord.z;

                signInfo.removeTag(NBT_UNLOCALIZED_NAME);
                TileEntityMoarSign entityMoarSign = (TileEntityMoarSign) world.getTileEntity(x, y, z);
                entityMoarSign.readFromNBT(signInfo);
                entityMoarSign.xCoord = x;
                entityMoarSign.yCoord = y;
                entityMoarSign.zCoord = z;
                world.markBlockForUpdate(x, y, z);

                signInfo = null;
                stack = toggleMoving(stack);
            }
        }

        stack.setTagCompound(signInfo);
        return !val;
    }

    private void doExchange(World world, int x, int y, int z, EntityPlayer player) {
        FMLNetworkHandler.openGui(player, MoarSigns.instance, GuiHandler.EXCHANGE, world, x, y, z);
    }

    private void doPreview(World world, int x, int y, int z, EntityPlayer player) {
        FMLNetworkHandler.openGui(player, MoarSigns.instance, GuiHandler.PREVIEW, world, x, y, z);
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

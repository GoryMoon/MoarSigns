package gory_moon.moarsigns.items;

import cofh.api.item.IToolHammer;
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
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import java.util.Collections;
import java.util.List;

import static gory_moon.moarsigns.lib.ToolBoxModes.*;

public class ItemSignToolbox extends Item implements IToolHammer {

    public static final String SIGN_MOVING_TAG = "SignMoving";
    public static final String NBT_UNLOCALIZED_NAME = "SignUnlocalizedName";

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

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            RayTraceResult movingObjectPosition = rayTrace(world, player, false);
            RayTraceResult.Type hit = movingObjectPosition != null ? movingObjectPosition.typeOfHit: RayTraceResult.Type.MISS;

            if (hit == RayTraceResult.Type.MISS) {
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
        return super.onItemRightClick(stack, world, player, hand);
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote) {
            int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
            switch (ToolBoxModes.values()[mode]) {
                case EDIT_MODE:
                    doEdit(world, pos, player);
                    break;
                case ROTATE_MODE:
                    doRotate(world, pos, player);
                    break;
                case MOVE_MODE:
                    return doMove(world, pos, stack, player, hand, side, hitX, hitY, hitZ);
                case COPY_MODE:
                    doCopy(world, pos, stack, player);
                    break;
                case EXCHANGE_MODE:
                    doExchange(world, pos, player);
                    return EnumActionResult.SUCCESS;
                case PREVIEW_MODE:
                    doPreview(world, pos, player);
                    return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    public static void doRotate(World world, BlockPos pos, EntityPlayer player) {
        if (world.getBlockState(pos).getBlock() instanceof BlockMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) world.getTileEntity(pos), player.isSneaking());
        }
    }

    private void doEdit(World world, BlockPos pos, EntityPlayer player) {
        if (world.getBlockState(pos).getBlock() instanceof BlockMoarSign) {
            TileEntity entity = world.getTileEntity(pos);
            if (entity instanceof TileEntityMoarSign) {
                TileEntityMoarSign tileEntity = (TileEntityMoarSign) entity;
                tileEntity.setEditable(true);
                tileEntity.setPlayer(player);
                PacketHandler.INSTANCE.sendTo(new MessageSignOpenGui(tileEntity, false), (EntityPlayerMP) player);
            }
        }
    }

    public void doCopy(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        NBTTagCompound signInfo = stack.getTagCompound();
        if (player.isSneaking()) {
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
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
    }

    private EnumActionResult doMove(World world, BlockPos pos, ItemStack stack, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        EnumActionResult val = EnumActionResult.SUCCESS;
        NBTTagCompound signInfo = stack.getTagCompound();

        if (!isMoving(stack.getItemDamage())) {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof TileEntityMoarSign) {
                TileEntityMoarSign tileEntityMoarSign = (TileEntityMoarSign) tileEntity;
                signInfo = new NBTTagCompound();
                tileEntity.writeToNBT(signInfo);
                ItemStack signStack = ModItems.SIGN.createMoarItemStack(tileEntityMoarSign.texture_name, tileEntityMoarSign.isMetal);
                String unlocalizedName = signStack.getUnlocalizedName() + ".name";
                signInfo.setString(NBT_UNLOCALIZED_NAME, unlocalizedName);

                stack = toggleMoving(stack);
                tileEntityMoarSign.removeNoDrop = true;
                world.setBlockToAir(pos);
            }
        } else {
            String texture = signInfo.getString(TileEntityMoarSign.NBT_TEXTURE_TAG);
            boolean isMetal = signInfo.getBoolean(TileEntityMoarSign.NBT_METAL_TAG);

            ItemStack moarSignsStack = ModItems.SIGN.createMoarItemStack(texture, isMetal);
            moarSignsStack.getTagCompound().setBoolean(SIGN_MOVING_TAG, true);
            val = ModItems.SIGN.onItemUse(moarSignsStack, player, world, pos, hand, side, hitX, hitY, hitZ);

            if (val == EnumActionResult.SUCCESS) {
                pos = pos.offset(side);

                signInfo.removeTag(NBT_UNLOCALIZED_NAME);
                TileEntityMoarSign entityMoarSign = (TileEntityMoarSign) world.getTileEntity(pos);
                entityMoarSign.readFromNBT(signInfo);
                entityMoarSign.setPos(pos);
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

                signInfo = null;
                stack = toggleMoving(stack);
            }
        }

        stack.setTagCompound(signInfo);
        return val == EnumActionResult.SUCCESS ? EnumActionResult.PASS: EnumActionResult.SUCCESS;
    }

    private void doExchange(World world, BlockPos pos, EntityPlayer player) {
        FMLNetworkHandler.openGui(player, MoarSigns.instance, GuiHandler.EXCHANGE, world, pos.getX(), pos.getY(), pos.getZ());
    }

    private void doPreview(World world, BlockPos pos, EntityPlayer player) {
        FMLNetworkHandler.openGui(player, MoarSigns.instance, GuiHandler.PREVIEW, world, pos.getX(), pos.getY(), pos.getZ());
    }

    private ActionResult<ItemStack> rotateModes(ItemStack stack) {
        int mode = stack.getItemDamage();
        mode = mode + 1 >= 6 ? 0 : mode + 1;
        stack.setItemDamage(mode);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
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
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.COPY.translate(Colors.LIGHTGRAY.toString() + "[" + GameSettings.getKeyDisplayString(gameSettings.keyBindSneak.getKeyCode()) + "]" + Colors.GRAY.toString(), Colors.LIGHTGRAY.toString() + "[", "]" + Colors.GRAY.toString(), "\n" + Colors.LIGHTGRAY.toString() + "[");
                if (stack.getTagCompound() != null)
                    str += "\n" + Colors.LIGHTGRAY + Localization.ITEM.SIGNTOOLBOX.CURRENT_TEXT.translate() + getFormattedData(stack.getTagCompound());
                break;
            case MOVE_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.MOVE.translate(Colors.LIGHTGRAY.toString() + "[", "]" + Colors.GRAY.toString(), "\n" + Colors.GRAY.toString(), "\n" + Colors.RED.toString());
                if (stack.getTagCompound() != null) {
                    String unlocName = stack.getTagCompound().getString(NBT_UNLOCALIZED_NAME);
                    if (!unlocName.isEmpty()) {
                        String signName = I18n.translateToLocal(unlocName);
                        str += "\n" + Colors.LIGHTGRAY + Localization.ITEM.SIGNTOOLBOX.CURRENT_SIGN.translate() + " " + Colors.WHITE + signName + "\n" + Colors.LIGHTGRAY + Localization.ITEM.SIGNTOOLBOX.CURRENT_TEXT.translate() + getFormattedData(stack.getTagCompound());
                    }
                }
                break;
            case EXCHANGE_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.EXCHANGE.translate("\n" + Colors.GRAY.toString());
                break;
            case PREVIEW_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.PREVIEW.translate("\n" + Colors.GRAY.toString());
                break;
            case ROTATE_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.ROTATE.translate(Colors.LIGHTGRAY.toString(), Colors.GRAY.toString(), "\n" + Colors.GRAY, Colors.LIGHTGRAY + "[" + GameSettings.getKeyDisplayString(gameSettings.keyBindSneak.getKeyCode()) + "]" + Colors.GRAY.toString());
                break;
            case EDIT_MODE:
                str += "\n" + Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.EDIT.translate(Colors.LIGHTGRAY.toString(), Colors.GRAY.toString());
        }

        String[] strList = str.split("\n");
        for (int i = 0; i < strList.length; i++) strList[i] = strList[i].trim();
        Collections.addAll(list, strList);
    }

    private String getFormattedData(NBTTagCompound compound) {
        String s = "\n";

        for (int i = 0; i < 4; i++) {
            String text = compound.getString("Text" + (i + 1));
            ITextComponent component = ITextComponent.Serializer.jsonToComponent(text);
            s += Colors.WHITE + "[" + Colors.GRAY + component.getFormattedText() + Colors.WHITE + "]\n";
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

    @Override
    public boolean isUsable(ItemStack item, EntityLivingBase user, int x, int y, int z) {
        int mode = isMoving(item.getItemDamage()) ? 2 : item.getItemDamage();
        return ToolBoxModes.values()[mode] == ROTATE_MODE;
    }

    @Override
    public void toolUsed(ItemStack item, EntityLivingBase user, int x, int y, int z) {
        
    }
}

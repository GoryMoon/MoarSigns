package gory_moon.moarsigns.items;

import com.google.common.collect.Lists;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.lib.Constants;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static gory_moon.moarsigns.lib.ToolBoxModes.*;

public class ItemSignToolbox extends VariantItem {

    public static final String SIGN_MOVING_TAG = "SignMoving";
    public static final String NBT_UNLOCALIZED_NAME = "SignUnlocalizedName";

    public ItemSignToolbox() {
        setRegistryName(Constants.SIGN_TOOLBOX_ITEM_KEY);
        setTranslationKey("moarsigns.signtoolbox");
        setCreativeTab(MoarSignsCreativeTab.tabMS);
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            RayTraceResult movingObjectPosition = rayTrace(world, player, false);
            RayTraceResult.Type hit = movingObjectPosition != null ? movingObjectPosition.typeOfHit : RayTraceResult.Type.MISS;

            if (hit == RayTraceResult.Type.MISS) {
                int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
                if (player.isSneaking() && !isMoving(stack.getItemDamage())) {
                    return rotateModes(stack);
                } else if (values()[mode] == EXCHANGE_MODE) {
                    doExchange(world, BlockPos.ORIGIN, player, hand);
                } else if (values()[mode] == PREVIEW_MODE) {
                    doPreview(world, BlockPos.ORIGIN, player);
                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
            switch (values()[mode]) {
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
                    doExchange(world, pos, player, hand);
                    return EnumActionResult.SUCCESS;
                case PREVIEW_MODE:
                    doPreview(world, pos, player);
                    return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    private void doRotate(World world, BlockPos pos, EntityPlayer player) {
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
                String name = signStack.getTranslationKey() + ".name";
                signInfo.setString(NBT_UNLOCALIZED_NAME, name);

                stack = toggleMoving(stack);
                tileEntityMoarSign.removeNoDrop = true;
                world.setBlockToAir(pos);
            }
        } else {
            val = ModItems.SIGN.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);

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
        return val == EnumActionResult.SUCCESS ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
    }

    private void doExchange(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
        FMLNetworkHandler.openGui(player, MoarSigns.instance, GuiHandler.EXCHANGE, world, hand.ordinal(), 0, 0);
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
    public String getTranslationKey(ItemStack stack) {
        if (stack.getItemDamage() > 5 && !isMoving(stack.getItemDamage()))
            stack.setItemDamage(0);
        int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
        return super.getTranslationKey(stack) + "." + values()[mode].toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        GameSettings gameSettings = FMLClientHandler.instance().getClient().gameSettings;
        String str = Colors.GRAY + Localization.ITEM.SIGNTOOLBOX.CHANGE.translate(Colors.LIGHTGRAY + "[" + GameSettings.getKeyDisplayString(gameSettings.keyBindSneak.getKeyCode()) + "]" + Colors.GRAY.toString());

        int mode = isMoving(stack.getItemDamage()) ? 2 : stack.getItemDamage();
        switch (values()[mode]) {
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
        for (int i = 0; i < strList.length; i++)
            strList[i] = strList[i].trim();
        Collections.addAll(tooltip, strList);
    }

    private String getFormattedData(NBTTagCompound compound) {
        StringBuilder s = new StringBuilder("\n");

        for (int i = 0; i < 4; i++) {
            String text = compound.getString("Text" + (i + 1));
            ITextComponent component = ITextComponent.Serializer.jsonToComponent(text);
            s.append(Colors.WHITE + "[" + Colors.GRAY).append(component.getFormattedText()).append(Colors.WHITE).append("]\n");
        }

        return s.toString();
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

    private List<Integer> metas = Lists.newArrayList(0, 1, 2, 3, 4, 5, 7);

    @Override
    public List<Integer> getMetas() {
        return metas;
    }

    @Override
    public String getVariant(int meta) {
        if (meta >= 0 && meta <= 7 && meta != 6)
            return values()[meta == 7 ? 2: meta].toString().toLowerCase().replaceAll("_mode", "");
        return "";
    }
}

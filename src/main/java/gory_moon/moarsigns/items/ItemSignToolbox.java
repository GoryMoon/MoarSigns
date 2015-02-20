package gory_moon.moarsigns.items;

import cpw.mods.fml.client.FMLClientHandler;
import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.lib.ToolBoxModes;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

import static gory_moon.moarsigns.lib.ToolBoxModes.values;

public class ItemSignToolbox extends Item {

    public ItemSignToolbox() {
        setUnlocalizedName("moarsign.signtoolbox");
        setCreativeTab(MoarSignsCreativeTab.tabMS);
        setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            MovingObjectPosition.MovingObjectType hit = FMLClientHandler.instance().getClient().objectMouseOver.typeOfHit;

            if (hit == MovingObjectPosition.MovingObjectType.MISS && player.isSneaking()) {
                return rotateModes(stack);
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            switch (ToolBoxModes.values()[stack.getItemDamage()]) {
                case EDIT_MODE:
                    doEdit(world, x, y, z, player);
                    break;
                case ROTATE_MODE:
                    doRotate(world, x, y, z);
                    break;
                case MOVE_MODE:
                    doMove(world, x, y, z, stack, player.isSneaking());
                    break;
                case COPY_MODE:
                    doCopy(world, x, y, z, stack, player.isSneaking());
                    break;
                case EXCHANGE_MODE:
                    doExchange(world, x, y, z);
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
                PacketHandler.INSTANCE.sendTo(new MessageSignOpenGui(tileEntity), (EntityPlayerMP) player);
            }
        }
    }


    private void doCopy(World world, int x, int y, int z, ItemStack stack, boolean sneaking) {

    }

    private void doMove(World world, int x, int y, int z, ItemStack stack, boolean sneaking) {

    }

    private void doExchange(World world, int x, int y, int z) {

    }

    private ItemStack rotateModes(ItemStack stack) {
        int mode = stack.getItemDamage();
        mode = mode + 1 >= 5 ? 0: mode + 1;
        stack.setItemDamage(mode);
        return stack;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() > 4) stack.setItemDamage(0);
        return super.getUnlocalizedName(stack) + "." + values()[stack.getItemDamage()].toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean extraInfo) {
        GameSettings gameSettings = FMLClientHandler.instance().getClient().gameSettings;
        String str = GuiColor.GRAY + Localization.ITEM.SIGNTOOLBOX.CHANGE.translate(GuiColor.LIGHTGRAY + "[" + GameSettings.getKeyDisplayString(gameSettings.keyBindSneak.getKeyCode()) + "]" + GuiColor.GRAY.toString());

        switch (values()[stack.getItemDamage()]) {
            case COPY_MODE:
                str += "\n" + GuiColor.GRAY + Localization.ITEM.SIGNTOOLBOX.COPY.translate(GuiColor.LIGHTGRAY.toString() + "[", (Minecraft.isRunningOnMac ? "0": "1"), "]" + GuiColor.GRAY.toString(), "\n" + GuiColor.LIGHTGRAY.toString() + "[");
                break;
            case MOVE_MODE:
                str += "\n" + GuiColor.GRAY + Localization.ITEM.SIGNTOOLBOX.MOVE.translate(GuiColor.LIGHTGRAY.toString() + "[", "]" + GuiColor.GRAY.toString(), "\n" + GuiColor.GRAY.toString(), "\n" + GuiColor.RED.toString());
                break;
            case EXCHANGE_MODE:
                str += "\n" + GuiColor.GRAY + Localization.ITEM.SIGNTOOLBOX.values()[stack.getItemDamage() + 1].translate("\n" + GuiColor.GRAY.toString());
                break;
            default:
                str += "\n" + GuiColor.GRAY + Localization.ITEM.SIGNTOOLBOX.values()[stack.getItemDamage() + 1].translate(GuiColor.LIGHTGRAY.toString(), GuiColor.GRAY.toString());
        }

        String[] strList = str.split("\n");
        for (int i = 0; i < strList.length; i++) strList[i] = strList[i].trim();
        Collections.addAll(list, strList);
    }
}

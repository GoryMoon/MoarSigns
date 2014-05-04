package gory_moon.moarsigns.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketOpenMoarSignsGui extends AbstractPacket {

    PacketSignMainInfo mainInfo = new PacketSignMainInfo();

    public PacketOpenMoarSignsGui() {
    }

    public PacketOpenMoarSignsGui(String texture, boolean isMetal, int activeMaterialIndex, int id, int meta, int fontSize, int offset, String[] text, int posX, int posY, int posZ) {
        mainInfo = new PacketSignMainInfo(texture, isMetal, activeMaterialIndex, id, meta, fontSize, offset, text, posX, posY, posZ);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        mainInfo.encodeInto(ctx, buffer);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        mainInfo.decodeInto(ctx, buffer);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(EntityPlayer player) {
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world.blockExists(mainInfo.signUpdate.posX, mainInfo.signUpdate.posY, mainInfo.signUpdate.posZ)) {
            TileEntity tileentity = world.getTileEntity(mainInfo.signUpdate.posX, mainInfo.signUpdate.posY, mainInfo.signUpdate.posZ);

            TileEntityMoarSign sign = null;
            if (tileentity == null) {
                sign = new TileEntityMoarSign();
                sign.setWorldObj(player.worldObj);
                sign.xCoord = mainInfo.signUpdate.posX;
                sign.yCoord = mainInfo.signUpdate.posY;
                sign.zCoord = mainInfo.signUpdate.posZ;
            } else {
                sign = (TileEntityMoarSign) tileentity;
            }

            mainInfo.markDirty = false;
            mainInfo.skipChecks = true;
            mainInfo.skipTileEntity = sign;
            mainInfo.handleClientSide(player);

            Minecraft.getMinecraft().displayGuiScreen(new GuiMoarSign(sign));
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }
}

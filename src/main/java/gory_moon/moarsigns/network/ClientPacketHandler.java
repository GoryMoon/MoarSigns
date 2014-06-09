package gory_moon.moarsigns.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.lib.PacketIDs;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet130UpdateSign;
import net.minecraft.network.packet.Packet133TileEditorOpen;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientPacketHandler implements IPacketHandler {

    public static void sendSignUpdate(TileEntityMoarSign tileEntity) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        try {

            dataStream.writeByte((byte) PacketIDs.SIGN_TEXT_PACKET.getID());

            dataStream.writeInt(tileEntity.fontSize);
            dataStream.writeInt(tileEntity.textOffset);
            Packet130UpdateSign packet = new Packet130UpdateSign(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.signText);
            packet.writePacketData(dataStream);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL_S, byteStream.toByteArray()));

        } catch (IOException e) {
            System.err.append("[MoarSigns]- Failed to send sign update packet");
        }
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
        EntityPlayer entityPlayer = (EntityPlayer) player;

        SignInfoPacket infoPacket = new SignInfoPacket();

        switch (PacketIDs.getID(reader.readByte())) {
            case MAIN_INFO_PACKET:

                infoPacket.readInfo(reader);

                Packet130UpdateSign signPacket = new Packet130UpdateSign();
                signPacket.readPacketData(reader);

                handleUpdateSign(signPacket, infoPacket);
                break;
            case OPEN_GUI_PACKET:

                infoPacket.readInfo(reader);

                Packet133TileEditorOpen open = new Packet133TileEditorOpen();
                open.readPacketData(reader);

                handleClientGuiPacket(open, entityPlayer, infoPacket);
                break;
        }

    }

    public void handleUpdateSign(Packet130UpdateSign packet, SignInfoPacket infoPacket) {
        if (Minecraft.getMinecraft().theWorld.blockExists(packet.xPosition, packet.yPosition, packet.zPosition)) {
            TileEntity tileentity = Minecraft.getMinecraft().theWorld.getBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition);

            if (tileentity instanceof TileEntityMoarSign) {
                TileEntityMoarSign sign = (TileEntityMoarSign) tileentity;

                sign.isMetal = infoPacket.isMetal;
                sign.activeMaterialIndex = infoPacket.activeMaterialIndex;
                sign.materialId = infoPacket.id;
                sign.materialMeta = infoPacket.meta;
                sign.fontSize = infoPacket.fontSize;
                sign.textOffset = infoPacket.offset;
                sign.setResourceLocation(infoPacket.texture);

                if (sign.isEditable()) {
                    System.arraycopy(packet.signLines, 0, sign.signText, 0, 4);
                    sign.onInventoryChanged();
                }
            }
        }
    }

    private void handleClientGuiPacket(Packet133TileEditorOpen open, EntityPlayer entityPlayer, SignInfoPacket infoPacket) {
        int x = open.field_142035_b, y = open.field_142036_c, z = open.field_142034_d;

        TileEntity te = entityPlayer.worldObj.getBlockTileEntity(x, y, z);
        TileEntityMoarSign moarSign = null;

        if (te == null) {
            moarSign = new TileEntityMoarSign();
            moarSign.setWorldObj(entityPlayer.worldObj);
            moarSign.xCoord = x;
            moarSign.yCoord = y;
            moarSign.zCoord = z;
        } else if (te instanceof TileEntityMoarSign) {
            moarSign = (TileEntityMoarSign) te;
        }

        if (moarSign != null) {
            moarSign.isMetal = infoPacket.isMetal;
            moarSign.materialId = infoPacket.id;
            moarSign.materialMeta = infoPacket.meta;
            moarSign.fontSize = infoPacket.fontSize;
            moarSign.textOffset = infoPacket.offset;
            moarSign.setResourceLocation(infoPacket.texture);

            Minecraft.getMinecraft().displayGuiScreen(new GuiMoarSign(moarSign));
        }

    }

}

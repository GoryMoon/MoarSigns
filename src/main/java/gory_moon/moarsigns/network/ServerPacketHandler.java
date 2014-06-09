package gory_moon.moarsigns.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.lib.PacketIDs;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet130UpdateSign;
import net.minecraft.network.packet.Packet133TileEditorOpen;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.World;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerPacketHandler implements IPacketHandler {

    public static Packet250CustomPayload getTextureNamePacket(TileEntityMoarSign tileEntity) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        try {

            dataStream.writeByte((byte) PacketIDs.MAIN_INFO_PACKET.getID());

            SignInfoPacket infoPacket = new SignInfoPacket(tileEntity.texture_name, tileEntity.isMetal, tileEntity.activeMaterialIndex,
                    tileEntity.materialId, tileEntity.materialMeta, tileEntity.fontSize, tileEntity.textOffset);
            infoPacket.writeInfo(dataStream);

            String[] astring = new String[4];
            System.arraycopy(tileEntity.signText, 0, astring, 0, 4);

            Packet130UpdateSign packet = new Packet130UpdateSign(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, astring);
            packet.writePacketData(dataStream);

            return new Packet250CustomPayload(ModInfo.CHANNEL_C, byteStream.toByteArray());

        } catch (IOException e) {
            System.err.append("[MoarSigns]- Failed to send texture name packet");
        }
        return null;
    }

    public static void openMoarSignGui(EntityPlayer player, TileEntityMoarSign tileEntity) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        try {

            dataStream.writeByte((byte) PacketIDs.OPEN_GUI_PACKET.getID());

            SignInfoPacket infoPacket = new SignInfoPacket(tileEntity.texture_name, tileEntity.isMetal, tileEntity.activeMaterialIndex,
                    tileEntity.materialId, tileEntity.materialMeta, tileEntity.fontSize, tileEntity.textOffset);
            infoPacket.writeInfo(dataStream);

            Packet133TileEditorOpen editorOpen = new Packet133TileEditorOpen(0, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            editorOpen.writePacketData(dataStream);

            PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(ModInfo.CHANNEL_C, byteStream.toByteArray()), (Player) player);

        } catch (IOException e) {
            System.err.append("[MoarSigns]- Failed to send gui open to player packet. Player: ").append(player.getDisplayName());
        }
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
        EntityPlayer entityPlayer = (EntityPlayer) player;

        switch (PacketIDs.getID(reader.readByte())) {
            case SIGN_TEXT_PACKET:

                int fontSize = reader.readInt();
                int offset = reader.readInt();
                Packet130UpdateSign signPacket2 = new Packet130UpdateSign();
                signPacket2.readPacketData(reader);

                handleServerUpdateSign(signPacket2, (EntityPlayerMP) entityPlayer, fontSize, offset);
                break;
        }
    }

    public void handleServerUpdateSign(Packet130UpdateSign packet, EntityPlayerMP player, int fontSize, int offset) {
        player.func_143004_u();
        World world = player.worldObj;

        if (world.blockExists(packet.xPosition, packet.yPosition, packet.zPosition)) {
            TileEntity tileentity = world.getBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition);

            int i;
            int j;

            for (j = 0; j < 4; ++j) {
                boolean flag = true;

                if (packet.signLines[j].length() > 15) {
                    flag = false;
                } else {
                    for (i = 0; i < packet.signLines[j].length(); ++i) {
                        if (ChatAllowedCharacters.allowedCharacters.indexOf(packet.signLines[j].charAt(i)) < 0) {
                            flag = false;
                        }
                    }
                }

                if (!flag) {
                    packet.signLines[j] = "!?";
                }
            }

            if (tileentity instanceof TileEntityMoarSign) {

                int x = packet.xPosition;
                int y = packet.yPosition;
                int z = packet.zPosition;

                TileEntityMoarSign tileentitysign1 = (TileEntityMoarSign) tileentity;
                System.arraycopy(packet.signLines, 0, tileentitysign1.signText, 0, 4);
                tileentitysign1.fontSize = fontSize;
                tileentitysign1.textOffset = offset;

                tileentitysign1.onInventoryChanged();
                world.markBlockForUpdate(x, y, z);
            }
        }
    }
}

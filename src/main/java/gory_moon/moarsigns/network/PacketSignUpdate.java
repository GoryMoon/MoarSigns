package gory_moon.moarsigns.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldServer;

import java.io.IOException;

public class PacketSignUpdate extends AbstractPacket {

    public int fontSize;
    public int offset;

    public int posX;
    public int posY;
    public int posZ;
    public String[] text = new String[] {"", "", "", ""};

    public PacketSignUpdate() {
    }

    public PacketSignUpdate(int posX, int posY, int posZ, String[] text, int fontSize, int offset) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.text = text;
        this.fontSize = fontSize;
        this.offset = offset;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

        buffer.writeInt(fontSize);
        buffer.writeInt(offset);

        buffer.writeInt(posX);
        buffer.writeInt(posY);
        buffer.writeInt(posZ);

        for(int i = 0; i < 4; i++) {
            try {
                PacketPipeline.writeString(text[i], buffer);
            } catch (IOException ignored) {}
        }
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

        fontSize = buffer.readInt();
        offset = buffer.readInt();

        posX = buffer.readInt();
        posY = buffer.readInt();
        posZ = buffer.readInt();

        for (int i = 0; i < 4; i++) {
            try {
                text[i] = PacketPipeline.readStringFromBuffer(15, buffer);
            } catch (IOException ignored) {
                text[i] = "";
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        ((EntityPlayerMP)player).func_143004_u();
        WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(player.dimension);

        if (worldserver.blockExists(posX, posY, posZ))
        {
            TileEntity tileentity = worldserver.getTileEntity(posX, posY, posZ);

            if (tileentity instanceof TileEntitySign)
            {
                TileEntitySign sign = (TileEntitySign)tileentity;

                if (!sign.func_145914_a() || sign.func_145911_b() != player)
                {
                    MinecraftServer.getServer().logWarning("Player " + player.getCommandSenderName() + " just tried to change non-editable sign");
                    return;
                }

                int rows = fontSize > 15 ? 1: (fontSize > 5 ? 2: (fontSize > 1 ? 3 : 4));
                int maxLength = fontSize > 17 ? 5: (fontSize > 13 ? 6: (fontSize > 10 ? 7: (fontSize > 7 ? 8: (fontSize > 5 ? 9: (fontSize > 4 ? 11: (fontSize > 1 ? 12: (fontSize > 0 ? 13: 15)))))));

                for (int i = 0; i < 4; ++i) {
                    boolean flag = true;

                    text[i] = text[i].substring(0, Math.min(text[i].length(), maxLength));
                    if (i > rows) {
                        text[i] = "";
                    }

                    for (int j = 0; j < text[i].length(); ++j) {
                        if (!ChatAllowedCharacters.isAllowedCharacter(text[i].charAt(j))) {
                            flag = false;
                        }
                    }

                    if (!flag) {
                        text[i] = "!?";
                    }
                }
                ((TileEntityMoarSign)sign).fontSize = fontSize;
                ((TileEntityMoarSign)sign).textOffset = offset;

                System.arraycopy(text, 0, sign.signText, 0, 4);
                sign.markDirty();
                worldserver.markBlockForUpdate(posX, posY, posZ);
            }
        }
    }
}

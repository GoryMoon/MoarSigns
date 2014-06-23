package gory_moon.moarsigns.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldServer;

public class MessageSignUpdate implements IMessage, IMessageHandler<MessageSignUpdate, IMessage> {

    public int x, y, z;

    public int fontSize;
    public int offset;
    public String[] text = new String[]{"", "", "", ""};

    public MessageSignUpdate() {
    }

    public MessageSignUpdate(int x, int y, int z, int fontSize, int offset, String[] text) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.fontSize = fontSize;
        this.offset = offset;
        this.text = text;
    }

    public MessageSignUpdate(TileEntityMoarSign tileEntity) {
        this(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.fontSize, tileEntity.textOffset, tileEntity.signText);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

        this.fontSize = buf.readInt();
        this.offset = buf.readInt();

        for (int i = 0; i < 4; i++) {
            int textLength = buf.readInt();
            text[i] = new String(buf.readBytes(textLength).array());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeInt(fontSize);
        buf.writeInt(offset);

        for (int i = 0; i < 4; i++) {
            buf.writeInt(text[i].length());
            buf.writeBytes(text[i].getBytes());
        }
    }

    @Override
    public IMessage onMessage(MessageSignUpdate message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        player.func_143004_u();
        WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(player.dimension);
        if (worldserver.blockExists(message.x, message.y, message.z)) {
            TileEntity tileentity = worldserver.getTileEntity(message.x, message.y, message.z);

            if (tileentity instanceof TileEntityMoarSign) {
                TileEntityMoarSign tileentitysign = (TileEntityMoarSign) tileentity;
                if (!tileentitysign.func_145914_a() || tileentitysign.func_145911_b() != player) {
                    MoarSigns.logger.warn("Player " + player.getCommandSenderName() + " just tried to change non-editable sign");
                    return null;
                }

                int maxLength = Utils.getMaxLength(message.fontSize);
                int rows = Utils.getRows(message.fontSize);

                for (int i = 0; i < 4; ++i) {
                    boolean flag = true;

                    message.text[i] = message.text[i].substring(0, Math.min(message.text[i].length(), maxLength));
                    if (i > rows) {
                        message.text[i] = "";
                    }

                    for (int j = 0; j < message.text[i].length(); ++j) {
                        if (!ChatAllowedCharacters.isAllowedCharacter(message.text[i].charAt(j))) {
                            flag = false;
                        }
                    }

                    if (!flag) {
                        message.text[i] = "!?";
                    }
                }

                tileentitysign.fontSize = message.fontSize;
                tileentitysign.textOffset = message.offset;

                System.arraycopy(message.text, 0, tileentitysign.signText, 0, 4);
                tileentitysign.markDirty();
                worldserver.markBlockForUpdate(message.x, message.y, message.z);

            }
        }
        return null;
    }
}

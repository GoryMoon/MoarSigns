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
import net.minecraft.world.WorldServer;

public class MessageSignUpdate implements IMessage, IMessageHandler<MessageSignUpdate, IMessage> {

    public int x, y, z;

    public int[] rowLocations = new int[4];
    public int[] rowSizes = {0, 0, 0, 0};
    public boolean[] visibleRows = {true, true, true, true};
    public boolean[] shadowRows = new boolean[4];
    public boolean lockedChanges;
    public String[] text = new String[]{"", "", "", ""};

    @SuppressWarnings("unused")
    public MessageSignUpdate() {
    }

    public MessageSignUpdate(int x, int y, int z, int[] rowLocations, int[] rowSizes, boolean[] visibleRows, boolean[] shadowRows, boolean lockedChanges, String[] text) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rowLocations = rowLocations;
        this.rowSizes = rowSizes;
        this.visibleRows = visibleRows;
        this.shadowRows = shadowRows;
        this.lockedChanges = lockedChanges;
        this.text = text;
    }

    public MessageSignUpdate(TileEntityMoarSign tileEntity) {
        this(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.rowLocations,
                tileEntity.rowSizes, tileEntity.visibleRows, tileEntity.shadowRows, tileEntity.lockedChanges, tileEntity.signText);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

        for (int i = 0; i < 4; i++) rowLocations[i] = buf.readInt();
        for (int i = 0; i < 4; i++) rowSizes[i] = buf.readInt();
        for (int i = 0; i < 4; i++) visibleRows[i] = buf.readBoolean();
        for (int i = 0; i < 4; i++) shadowRows[i] = buf.readBoolean();
        lockedChanges = buf.readBoolean();

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

        for (int i = 0; i < 4; i++) buf.writeInt(rowLocations[i]);
        for (int i = 0; i < 4; i++) buf.writeInt(rowSizes[i]);
        for (int i = 0; i < 4; i++) buf.writeBoolean(visibleRows[i]);
        for (int i = 0; i < 4; i++) buf.writeBoolean(shadowRows[i]);
        buf.writeBoolean(lockedChanges);

        for (int i = 0; i < 4; i++) {
            buf.writeInt(text[i].getBytes().length);
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

                tileentitysign.rowLocations = message.rowLocations;
                tileentitysign.rowSizes = message.rowSizes;
                tileentitysign.visibleRows = message.visibleRows;
                tileentitysign.shadowRows = message.shadowRows;
                tileentitysign.lockedChanges = message.lockedChanges;

                for (int i = 0; i < 4; ++i) {
                    boolean flag = true;

                    for (int j = 0; j < message.text[i].length(); ++j) {
                        if (!Utils.isAllowedCharacter(message.text[i].charAt(j))) {
                            flag = false;
                        }
                    }

                    if (!flag) {
                        message.text[i] = "!?";
                    }
                }

                System.arraycopy(message.text, 0, tileentitysign.signText, 0, 4);
                tileentitysign.markDirty();
                worldserver.markBlockForUpdate(message.x, message.y, message.z);

            }
        }
        return null;
    }
}

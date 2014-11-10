package gory_moon.moarsigns.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

public class MessageSignMainInfo implements IMessage, IMessageHandler<MessageSignMainInfo, IMessage> {

    public int x, y, z;

    public String texture;
    public boolean isMetal;

    public int[] rowLocations = new int[4];
    public int[] rowSizes = {0,0,0,0};
    public boolean[] visibleRows = {true, true, true, true};
    public boolean lockedChanges;

    public String[] text = new String[]{"", "", "", ""};

    public MessageSignMainInfo() {
    }

    public MessageSignMainInfo(int x, int y, int z, String texture, boolean isMetal, int[] rowLocations, int[] rowSizes, boolean[] visibleRows, boolean lockedChanges, String[] text) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.texture = texture;
        this.isMetal = isMetal;
        this.rowLocations = rowLocations;
        this.rowSizes = rowSizes;
        this.visibleRows = visibleRows;
        this.lockedChanges = lockedChanges;
        this.text = text;
    }

    public MessageSignMainInfo(TileEntityMoarSign tileEntity) {
        this(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.texture_name, tileEntity.isMetal,
                tileEntity.rowLocations, tileEntity.rowSizes, tileEntity.visibleRows, tileEntity.lockedChanges, tileEntity.signText);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

        int textureLength = buf.readInt();
        this.texture = new String(buf.readBytes(textureLength).array());
        this.isMetal = buf.readBoolean();

        for (int i = 0; i < 4; i++) rowLocations[i] = buf.readInt();
        for (int i = 0; i < 4; i++) rowSizes[i] = buf.readInt();
        for (int i = 0; i < 4; i++) visibleRows[i] = buf.readBoolean();
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

        buf.writeInt(texture.length());
        buf.writeBytes(texture.getBytes());
        buf.writeBoolean(isMetal);

        for (int i = 0; i < 4; i++)  buf.writeInt(rowLocations[i]);
        for (int i = 0; i < 4; i++)  buf.writeInt(rowSizes[i]);
        for (int i = 0; i < 4; i++)  buf.writeBoolean(visibleRows[i]);
        buf.writeBoolean(lockedChanges);

        for (int i = 0; i < 4; i++) {
            buf.writeInt(text[i].getBytes().length);
            buf.writeBytes(text[i].getBytes());
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage onMessage(MessageSignMainInfo message, MessageContext ctx) {
        WorldClient world = FMLClientHandler.instance().getClient().theWorld;
        TileEntity tileEntity;

        boolean flag = false;

        if (world.blockExists(message.x, message.y, message.z)) {
        tileEntity = world.getTileEntity(message.x, message.y, message.z);
            if (tileEntity instanceof TileEntityMoarSign) {
                TileEntityMoarSign sign = (TileEntityMoarSign) tileEntity;

                sign.isMetal = message.isMetal;
                sign.rowLocations = message.rowLocations;
                sign.rowSizes = message.rowSizes;
                sign.visibleRows = message.visibleRows;
                sign.lockedChanges = message.lockedChanges;
                sign.setResourceLocation(message.texture);

                if (sign.isEditable()) {
                    System.arraycopy(message.text, 0, sign.signText, 0, 4);
                }
                flag = true;
            }
        }
        if (!flag && FMLClientHandler.instance().getClient().thePlayer != null) {
            MoarSigns.logger.info("Unable to locate sign at " + message.x + ", " + message.y + ", " + message.z);
            FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText("Unable to locate sign at " + message.x + ", " + message.y + ", " + message.z));
        }

        return null;
    }
}

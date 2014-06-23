package gory_moon.moarsigns.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;

public class MessageSignOpenGui implements IMessage, IMessageHandler<MessageSignOpenGui, IMessage> {

    public int x, y, z;

    public String texture;
    public boolean isMetal;
    public int fontSize;
    public int offset;
    public String[] text = new String[]{"", "", "", ""};

    public MessageSignOpenGui() {
    }

    public MessageSignOpenGui(int x, int y, int z, String texture, boolean isMetal, int fontSize, int offset, String[] text) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.texture = texture;
        this.isMetal = isMetal;
        this.fontSize = fontSize;
        this.offset = offset;
        this.text = text;
    }

    public MessageSignOpenGui(TileEntityMoarSign tileEntity) {
        this.x = tileEntity.xCoord;
        this.y = tileEntity.yCoord;
        this.z = tileEntity.zCoord;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

        int textureLength = buf.readInt();
        this.texture = new String(buf.readBytes(textureLength).array());
        this.isMetal = buf.readBoolean();
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

        buf.writeInt(texture.length());
        buf.writeBytes(texture.getBytes());
        buf.writeBoolean(isMetal);
        buf.writeInt(fontSize);
        buf.writeInt(offset);

        for (int i = 0; i < 4; i++) {
            buf.writeInt(text[i].length());
            buf.writeBytes(text[i].getBytes());
        }
    }

    @Override
    public IMessage onMessage(MessageSignOpenGui message, MessageContext ctx) {
        WorldClient world = FMLClientHandler.instance().getClient().theWorld;
        if (world.blockExists(message.x, message.y, message.z)) {
            TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

            if (tileEntity == null) {
                tileEntity = new TileEntityMoarSign();
                tileEntity.setWorldObj(FMLClientHandler.instance().getClient().theWorld);
                tileEntity.xCoord = message.x;
                tileEntity.yCoord = message.y;
                tileEntity.zCoord = message.z;
            }

            FMLClientHandler.instance().getClient().displayGuiScreen(new GuiMoarSign((TileEntityMoarSign) tileEntity));
        }
        return null;
    }
}

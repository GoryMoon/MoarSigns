package gory_moon.moarsigns.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

    public MessageSignOpenGui(int x, int y, int z, String texture) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.texture = texture;
    }

    public MessageSignOpenGui(TileEntityMoarSign tileEntity) {
        this.x = tileEntity.xCoord;
        this.y = tileEntity.yCoord;
        this.z = tileEntity.zCoord;
        this.texture = tileEntity.texture_name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

        int textureLength = buf.readInt();
        this.texture = new String(buf.readBytes(textureLength).array());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeInt(texture.length());
        buf.writeBytes(texture.getBytes());
    }

    @SideOnly(Side.CLIENT)
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

            ((TileEntityMoarSign)tileEntity).setResourceLocation(message.texture);

            FMLClientHandler.instance().getClient().displayGuiScreen(new GuiMoarSign((TileEntityMoarSign) tileEntity));
        }
        return null;
    }
}

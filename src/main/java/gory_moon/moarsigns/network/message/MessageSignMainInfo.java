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
    public int fontSize;
    public int offset;
    public String[] text = new String[]{"", "", "", ""};
    public boolean openGui;

    public MessageSignMainInfo() {
    }

    public MessageSignMainInfo(int x, int y, int z, String texture, boolean isMetal, int fontSize, int offset, String[] text, boolean openGui) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.texture = texture;
        this.isMetal = isMetal;
        this.fontSize = fontSize;
        this.offset = offset;
        this.text = text;
        this.openGui = openGui;
    }

    public MessageSignMainInfo(TileEntityMoarSign tileEntity, boolean openGui) {
        this(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.texture_name, tileEntity.isMetal,
                tileEntity.fontSize, tileEntity.textOffset, tileEntity.signText, openGui);
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
        this.openGui = buf.readBoolean();

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
            buf.writeInt(text[i].getBytes().length);
            buf.writeBytes(text[i].getBytes());
        }
        buf.writeBoolean(openGui);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage onMessage(MessageSignMainInfo message, MessageContext ctx) {
        WorldClient world = FMLClientHandler.instance().getClient().theWorld;
        TileEntity tileEntity = null;
        if (message.openGui) {
            if (world.blockExists(message.x, message.y, message.z)) {
                tileEntity = world.getTileEntity(message.x, message.y, message.z);

                if (tileEntity == null || !(tileEntity instanceof TileEntityMoarSign)) {
                    tileEntity = new TileEntityMoarSign();
                    tileEntity.setWorldObj(FMLClientHandler.instance().getClient().theWorld);
                    tileEntity.xCoord = message.x;
                    tileEntity.yCoord = message.y;
                    tileEntity.zCoord = message.z;
                }
            }
        }

        boolean flag = false;

        if (world.blockExists(message.x, message.y, message.z)) {
            if (!message.openGui) tileEntity = world.getTileEntity(message.x, message.y, message.z);
            if (tileEntity instanceof TileEntityMoarSign) {
                TileEntityMoarSign sign = (TileEntityMoarSign) tileEntity;

                sign.isMetal = message.isMetal;
                sign.fontSize = message.fontSize;
                sign.textOffset = message.offset;
                sign.setResourceLocation(message.texture);

                if (sign.isEditable()) {
                    System.arraycopy(message.text, 0, sign.signText, 0, 4);
                    if (!message.openGui) sign.markDirty();
                }
                flag = true;
            }
        }
        if (!flag && FMLClientHandler.instance().getClient().thePlayer != null) {
            MoarSigns.logger.info("Unable to locate sign at " + message.x + ", " + message.y + ", " + message.z);
            FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText("Unable to locate sign at " + message.x + ", " + message.y + ", " + message.z));
        }

        if (message.openGui && tileEntity instanceof TileEntityMoarSign)
            FMLClientHandler.instance().getClient().displayGuiScreen(new GuiMoarSign((TileEntityMoarSign) tileEntity));

        return null;
    }
}

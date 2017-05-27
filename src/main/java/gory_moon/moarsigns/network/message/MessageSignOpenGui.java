package gory_moon.moarsigns.network.message;

import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.network.ClientMessageHandler;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.charset.Charset;

public class MessageSignOpenGui implements IMessage {

    public BlockPos pos;

    public String texture;
    public boolean isMetal;
    public boolean isMoving;

    @SuppressWarnings("unused")
    public MessageSignOpenGui() {
    }

    @SuppressWarnings("unused")
    public MessageSignOpenGui(BlockPos pos, String texture, boolean isMetal) {
        this.pos = pos;
        this.texture = texture;
        this.isMetal = isMetal;
    }

    public MessageSignOpenGui(TileEntityMoarSign tileEntity, boolean isMoving) {
        this.pos = tileEntity.getPos();
        this.texture = tileEntity.texture_name;
        this.isMetal = tileEntity.isMetal;
        this.isMoving = isMoving;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        this.pos = packetBuf.readBlockPos();
        int textureLength = packetBuf.readInt();
        this.texture = new String(packetBuf.readBytes(textureLength).array(), Charset.forName("utf-8"));
        this.isMetal = packetBuf.readBoolean();
        this.isMoving = packetBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        packetBuf.writeBlockPos(pos);
        packetBuf.writeInt(texture.length());
        packetBuf.writeBytes(texture.getBytes(Charset.forName("utf-8")));
        packetBuf.writeBoolean(isMetal);
        packetBuf.writeBoolean(isMoving);
    }


    public static class Handler extends ClientMessageHandler<MessageSignOpenGui> {

        @Override
        @SideOnly(Side.CLIENT)
        protected void handle(MessageSignOpenGui message, MessageContext ctx) {
            TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.pos);

            if (!(tileEntity instanceof TileEntityMoarSign)) {
                tileEntity = new TileEntityMoarSign();
                tileEntity.setWorldObj(FMLClientHandler.instance().getClient().theWorld);
                tileEntity.setPos(message.pos);
            }

            ((TileEntityMoarSign) tileEntity).isMetal = message.isMetal;
            ((TileEntityMoarSign) tileEntity).setResourceLocation(message.texture);
            tileEntity.markDirty();

            if (!message.isMoving)
                FMLClientHandler.instance().getClient().displayGuiScreen(new GuiMoarSign((TileEntityMoarSign) tileEntity));
        }
    }
}

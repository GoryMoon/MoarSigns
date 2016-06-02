package gory_moon.moarsigns.network.message;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.network.ClientMessageHandler;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;
import java.nio.charset.Charset;

public class MessageSignMainInfo implements IMessage {

    public BlockPos pos;

    public String texture;
    public boolean isMetal;

    public int[] rowLocations = new int[4];
    public int[] rowSizes = {0, 0, 0, 0};
    public boolean[] visibleRows = {true, true, true, true};
    public boolean[] shadowRows = new boolean[4];
    public boolean lockedChanges;

    public ITextComponent[] text;

    @SuppressWarnings("unused")
    public MessageSignMainInfo() {
    }

    public MessageSignMainInfo(BlockPos pos, String texture, boolean isMetal, int[] rowLocations, int[] rowSizes, boolean[] visibleRows, boolean[] shadowRows, boolean lockedChanges, ITextComponent[] text) {
        this.pos = pos;
        this.texture = texture;
        this.isMetal = isMetal;
        this.rowLocations = rowLocations;
        this.rowSizes = rowSizes;
        this.visibleRows = visibleRows;
        this.shadowRows = shadowRows;
        this.lockedChanges = lockedChanges;
        this.text = text;
    }

    public MessageSignMainInfo(TileEntityMoarSign tileEntity) {
        this(tileEntity.getPos(), tileEntity.texture_name, tileEntity.isMetal,
                tileEntity.rowLocations, tileEntity.rowSizes, tileEntity.visibleRows, tileEntity.shadowRows, tileEntity.lockedChanges, tileEntity.signText);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        pos = packetBuf.readBlockPos();
        text = new ITextComponent[4];

        if (packetBuf.readBoolean()) {
            int textureLength = packetBuf.readInt();
            this.texture = new String(packetBuf.readBytes(textureLength).array(), Charset.forName("utf-8"));
            this.isMetal = packetBuf.readBoolean();

            for (int i = 0; i < 4; i++) rowLocations[i] = packetBuf.readInt();
            for (int i = 0; i < 4; i++) rowSizes[i] = packetBuf.readInt();
            for (int i = 0; i < 4; i++) visibleRows[i] = packetBuf.readBoolean();
            for (int i = 0; i < 4; i++) shadowRows[i] = packetBuf.readBoolean();
            lockedChanges = packetBuf.readBoolean();

            for (int i = 0; i < 4; i++) {
                try {
                    text[i] = packetBuf.readTextComponent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeBlockPos(pos);

        if (texture != null && rowLocations != null && rowSizes != null && visibleRows != null && text != null) {
            packetBuf.writeBoolean(true);
            packetBuf.writeInt(texture.length());
            packetBuf.writeBytes(texture.getBytes(Charset.forName("utf-8")));
            packetBuf.writeBoolean(isMetal);

            for (int i = 0; i < 4; i++) packetBuf.writeInt(rowLocations[i]);
            for (int i = 0; i < 4; i++) packetBuf.writeInt(rowSizes[i]);
            for (int i = 0; i < 4; i++) packetBuf.writeBoolean(visibleRows[i]);
            for (int i = 0; i < 4; i++) packetBuf.writeBoolean(shadowRows[i]);
            packetBuf.writeBoolean(lockedChanges);

            for (int i = 0; i < 4; i++) {
                packetBuf.writeTextComponent(text[i]);
            }
        } else {
            buf.writeBoolean(false);
        }
    }
    public static class Handler extends ClientMessageHandler<MessageSignMainInfo> {

        @Override
        protected void handle(MessageSignMainInfo message, MessageContext ctx) {
            WorldClient world = FMLClientHandler.instance().getClient().theWorld;
            TileEntity tileEntity;

            boolean flag = false;

            if (message.texture != null && message.rowLocations != null && message.rowSizes != null && message.visibleRows != null && message.shadowRows != null && message.text != null) {
                if (world.isBlockLoaded(message.pos)) {
                    tileEntity = world.getTileEntity(message.pos);

                    if (tileEntity instanceof TileEntityMoarSign) {
                        TileEntityMoarSign sign = (TileEntityMoarSign) tileEntity;

                        if (sign.getIsEditable()) {
                            sign.isMetal = message.isMetal;
                            sign.rowLocations = message.rowLocations;
                            sign.rowSizes = message.rowSizes;
                            sign.visibleRows = message.visibleRows;
                            sign.shadowRows = message.shadowRows;
                            sign.lockedChanges = message.lockedChanges;
                            sign.setResourceLocation(message.texture);

                            System.arraycopy(message.text, 0, sign.signText, 0, 4);
                            sign.markDirty();
                        }
                        flag = true;
                    }
                }
                if (!flag && FMLClientHandler.instance().getClient().thePlayer != null) {
                    MoarSigns.logger.info("Unable to locate sign at " + message.pos.toString());
                    FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new TextComponentString("Unable to locate sign at " + message.pos.getX() + ", " + message.pos.getY() + ", " + message.pos.getZ()));
                }
            } else {
                MoarSigns.logger.error("An error with packages occurred");
            }
        }
    }
}

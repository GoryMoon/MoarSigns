package gory_moon.moarsigns.network.message;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.network.ServerMessageHandler;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

public class MessageSignUpdate implements IMessage {

    public BlockPos pos;

    public int[] rowLocations = new int[4];
    public int[] rowSizes = {0, 0, 0, 0};
    public boolean[] visibleRows = {true, true, true, true};
    public boolean[] shadowRows = new boolean[4];
    public boolean lockedChanges;
    public ITextComponent[] text;

    @SuppressWarnings("unused")
    public MessageSignUpdate() {
    }

    public MessageSignUpdate(BlockPos pos, int[] rowLocations, int[] rowSizes, boolean[] visibleRows, boolean[] shadowRows, boolean lockedChanges, ITextComponent[] text) {
        this.pos = pos;
        this.rowLocations = rowLocations;
        this.rowSizes = rowSizes;
        this.visibleRows = visibleRows;
        this.shadowRows = shadowRows;
        this.lockedChanges = lockedChanges;
        this.text = text;
    }

    public MessageSignUpdate(TileEntityMoarSign tileEntity) {
        this(tileEntity.getPos(), tileEntity.rowLocations,
                tileEntity.rowSizes, tileEntity.visibleRows, tileEntity.shadowRows, tileEntity.lockedChanges, tileEntity.signText);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        pos = packetBuf.readBlockPos();
        text = new ITextComponent[4];

        for (int i = 0; i < 4; i++) rowLocations[i] = packetBuf.readInt();
        for (int i = 0; i < 4; i++) rowSizes[i] = packetBuf.readInt();
        for (int i = 0; i < 4; i++) visibleRows[i] = packetBuf.readBoolean();
        for (int i = 0; i < 4; i++) shadowRows[i] = packetBuf.readBoolean();
        lockedChanges = packetBuf.readBoolean();

        for (int i = 0; i < 4; i++) {
            try {
                text[i] = packetBuf.readChatComponent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);
        packetBuf.writeBlockPos(pos);

        for (int i = 0; i < 4; i++) packetBuf.writeInt(rowLocations[i]);
        for (int i = 0; i < 4; i++) packetBuf.writeInt(rowSizes[i]);
        for (int i = 0; i < 4; i++) packetBuf.writeBoolean(visibleRows[i]);
        for (int i = 0; i < 4; i++) packetBuf.writeBoolean(shadowRows[i]);
        packetBuf.writeBoolean(lockedChanges);

        for (int i = 0; i < 4; i++) {
            packetBuf.writeChatComponent(text[i]);
        }
    }

    public static class Handler extends ServerMessageHandler<MessageSignUpdate> {

        @Override
        protected void handle(MessageSignUpdate message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            player.markPlayerActive();

            WorldServer worldserver = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.dimension);
            BlockPos pos = message.pos;

            if (worldserver.isBlockLoaded(pos)) {

                IBlockState iblockstate = worldserver.getBlockState(pos);
                TileEntity tileentity = worldserver.getTileEntity(pos);

                if (tileentity instanceof TileEntityMoarSign) {
                    TileEntityMoarSign tileentitysign = (TileEntityMoarSign) tileentity;

                    if (!tileentitysign.getIsEditable() || tileentitysign.getPlayer() != player) {
                        MoarSigns.logger.warn("Player " + player.getName() + " just tried to change non-editable sign");
                        return;
                    }

                    tileentitysign.rowLocations = message.rowLocations;
                    tileentitysign.rowSizes = message.rowSizes;
                    tileentitysign.visibleRows = message.visibleRows;
                    tileentitysign.shadowRows = message.shadowRows;
                    tileentitysign.lockedChanges = message.lockedChanges;

                    ITextComponent[] components = message.text;

                    for (int i = 0; i < components.length; ++i) {
                        tileentitysign.signText[i] = new TextComponentString(components[i].getUnformattedText());
                    }

                    tileentitysign.markDirty();
                    worldserver.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);

                }
            }
        }
    }
}

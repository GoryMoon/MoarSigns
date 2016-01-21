package gory_moon.moarsigns.network.message;

import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.charset.Charset;

public class MessageSignRotate implements IMessage, IMessageHandler<MessageSignRotate, IMessage> {

    BlockPos pos;
    int meta;
    String texture;

    public MessageSignRotate() {
    }

    public MessageSignRotate(BlockPos pos, int meta, String texture) {
        this.pos = pos;
        this.meta = meta;
        this.texture = texture;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        pos = packetBuf.readBlockPos();
        meta = packetBuf.readInt();
        int textureLength = packetBuf.readInt();
        texture = new String(packetBuf.readBytes(textureLength).array(), Charset.forName("utf-8"));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuf = new PacketBuffer(buf);

        packetBuf.writeBlockPos(pos);
        packetBuf.writeInt(meta);
        packetBuf.writeInt(texture.length());
        packetBuf.writeBytes(texture.getBytes(Charset.forName("utf-8")));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage onMessage(MessageSignRotate message, MessageContext ctx) {

        World world = FMLClientHandler.instance().getClient().theWorld;
        Block block = world.getBlockState(message.pos).getBlock();

        if (block instanceof BlockMoarSign) {
            TileEntityMoarSign sign = (TileEntityMoarSign) world.getTileEntity(message.pos);
            world.setBlockState(message.pos, block.getStateFromMeta(meta));
            sign.setResourceLocation(message.texture);
        }

        return null;
    }
}

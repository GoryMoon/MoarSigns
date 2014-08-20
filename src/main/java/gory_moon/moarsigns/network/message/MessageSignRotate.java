package gory_moon.moarsigns.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class MessageSignRotate implements IMessage, IMessageHandler<MessageSignRotate, IMessage> {

    int x, y, z;
    int meta;
    String texture;

    public MessageSignRotate() {
    }

    public MessageSignRotate(int x, int y, int z, int meta, String texture) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.meta = meta;
        this.texture = texture;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        meta = buf.readInt();
        int textureLength = buf.readInt();
        this.texture = new String(buf.readBytes(textureLength).array());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(meta);
        buf.writeInt(texture.length());
        buf.writeBytes(texture.getBytes());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage onMessage(MessageSignRotate message, MessageContext ctx) {

        World world = FMLClientHandler.instance().getClient().theWorld;

        Block block = world.getBlock(message.x, message.y, message.z);

        if (block instanceof BlockMoarSign) {
            TileEntityMoarSign sign = (TileEntityMoarSign) world.getTileEntity(message.x, message.y, message.z);
            sign.blockMetadata = message.meta;
            sign.setResourceLocation(message.texture);
        }

        return null;
    }
}

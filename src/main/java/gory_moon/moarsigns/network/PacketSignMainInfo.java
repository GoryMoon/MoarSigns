package gory_moon.moarsigns.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.io.IOException;

public class PacketSignMainInfo extends AbstractPacket {

    public String texture;
    public boolean isMetal;
    public int activeMaterialIndex;
    public int id;
    public int meta;

    public boolean markDirty = true;
    public boolean skipChecks = false;
    public TileEntityMoarSign skipTileEntity = null;

    public PacketSignUpdate signUpdate = new PacketSignUpdate();

    public PacketSignMainInfo() {
    }

    public PacketSignMainInfo(String texture, boolean isMetal, int activeMaterialIndex, int id, int meta, int fontSize, int offset, String[] text, int posX, int posY, int posZ) {
        this.texture = texture;
        this.isMetal = isMetal;
        this.activeMaterialIndex = activeMaterialIndex;
        this.id = id;
        this.meta = meta;
        signUpdate.fontSize = fontSize;
        signUpdate.offset = offset;
        signUpdate.text = text;
        signUpdate.posX = posX;
        signUpdate.posY = posY;
        signUpdate.posZ = posZ;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        try {
            PacketPipeline.writeString(texture, buffer);
        } catch (IOException ignored) {}

        buffer.writeBoolean(isMetal);
        buffer.writeInt(activeMaterialIndex);
        buffer.writeInt(id);
        buffer.writeInt(meta);

        signUpdate.encodeInto(ctx, buffer);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        try {
            texture = PacketPipeline.readStringFromBuffer(30 + "_sign".length(), buffer);
        } catch (IOException ignored) {}

        isMetal = buffer.readBoolean();
        activeMaterialIndex = buffer.readInt();
        id = buffer.readInt();
        meta = buffer.readInt();

        signUpdate.decodeInto(ctx, buffer);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(EntityPlayer player) {
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (skipChecks || world.blockExists(signUpdate.posX, signUpdate.posY, signUpdate.posZ)) {
            TileEntity tileentity = world.getTileEntity(signUpdate.posX, signUpdate.posY, signUpdate.posZ);

            if (skipChecks || tileentity instanceof TileEntityMoarSign) {
                TileEntityMoarSign sign = (TileEntityMoarSign) tileentity;
                if (skipChecks) sign = skipTileEntity;

                sign.isMetal = isMetal;
                sign.activeMaterialIndex = activeMaterialIndex;
                sign.materialId = id;
                sign.materialMeta = meta;
                sign.fontSize = signUpdate.fontSize;
                sign.textOffset = signUpdate.offset;
                sign.setResourceLocation(texture);

                if (sign.isEditable()) {
                    System.arraycopy(signUpdate.text, 0, sign.signText, 0, 4);
                    if (markDirty) sign.markDirty();
                }
            }
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }
}

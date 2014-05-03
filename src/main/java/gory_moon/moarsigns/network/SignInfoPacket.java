package gory_moon.moarsigns.network;

import com.google.common.io.ByteArrayDataInput;
import net.minecraft.network.packet.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class SignInfoPacket {

    public String texture;
    public boolean isMetal;
    public int activeMaterialIndex;
    public int id;
    public int meta;
    public int fontSize;
    public int offset;

    public SignInfoPacket() {
    }

    public SignInfoPacket(String texture, boolean isMetal, int activeMaterialIndex, int id, int meta, int fontSize, int offset) {
        this.texture = texture;
        this.isMetal = isMetal;
        this.activeMaterialIndex = activeMaterialIndex;
        this.id = id;
        this.meta = meta;
        this.fontSize = fontSize;
        this.offset = offset;
    }

    public void readInfo(ByteArrayDataInput reader) {
        try {
            texture = Packet.readString(reader, 30 + "_sign".length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        isMetal = reader.readBoolean();
        activeMaterialIndex = reader.readInt();
        id = reader.readInt();
        meta = reader.readInt();
        fontSize = reader.readInt();
        offset = reader.readInt();
    }

    public void writeInfo(DataOutputStream writer) throws IOException {

        Packet.writeString(texture, writer);
        writer.writeBoolean(isMetal);
        writer.writeInt(activeMaterialIndex);
        writer.writeInt(id);
        writer.writeInt(meta);
        writer.writeInt(fontSize);
        writer.writeInt(offset);
    }

}

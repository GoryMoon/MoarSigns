package gory_moon.moarsigns.tileentites;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.network.PacketSignMainInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;

public class TileEntityMoarSign extends TileEntitySign {

    //public String[] signText = new String[] {"", "", "", ""};

    public int lineBeingEdited = -1;
    private boolean isEditable = true;
    public boolean isMetal = false;
    public String materialName;
    public String materialPath;
    public String texture_name;
    public int fontSize = 0;
    public int textOffset = 0;

    private int rows = 4;
    private int maxLength = 15;

    private EntityPlayer playerEditing;
    private ResourceLocation resourceLocation;

    private boolean textureReq = false;
    private int oldFontSize;

    private final int NBT_VERSION = 1;

    @Override
    public void updateEntity() {

        if (worldObj.isRemote) {

            if (fontSize != oldFontSize) {
                rows = fontSize > 16 ? 1: fontSize > 6 ? 2: fontSize > 1 ? 3 : 4;
                maxLength = fontSize > 17 ? 5: fontSize > 13 ? 6: fontSize > 10 ? 7: fontSize > 7 ? 8: fontSize > 5 ? 9: fontSize > 4 ? 11: fontSize > 1 ? 12: fontSize > 0 ? 13: 15;
                oldFontSize = fontSize;
            }
            if (!textureReq) {
                textureReq = true;
                Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
                worldObj.addBlockEvent(xCoord, yCoord, zCoord, block,  0, 0);
            }
        }
    }

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("nbtVersion", NBT_VERSION);
        for (int i = 0; i < 4; i++) {
            compound.setString("Text" + (i + 1), signText[i]);
        }
        compound.setBoolean("isMetal", isMetal);
        compound.setString("materialName", materialName);
        compound.setString("materialPath", materialPath);
        compound.setString("texture", texture_name);
        compound.setInteger("fontSize", fontSize);
        compound.setInteger("textOffset", textOffset);
    }

    public void readFromNBT(NBTTagCompound compound) {
        isEditable = false;
        super.readFromNBT(compound);

        fontSize = compound.getInteger("fontSize");
        rows = fontSize > 16 ? 1: fontSize > 6 ? 2: fontSize > 1 ? 3 : 4;
        maxLength = fontSize > 17 ? 5: fontSize > 13 ? 6: fontSize > 10 ? 7: fontSize > 7 ? 8: fontSize > 5 ? 9: fontSize > 4 ? 11: fontSize > 1 ? 12: fontSize > 0 ? 13: 15;

        for (int i = 0; i < 4; ++i) {
            signText[i] = compound.getString("Text" + (i + 1));

            if (signText[i].length() > maxLength) {
                signText[i] = signText[i].substring(0, maxLength);
            }

            if (i > rows) {
                signText[i] = "";
            }
        }
        isMetal = compound.getBoolean("isMetal");
        if (compound.hasKey("nbtVersion")) {
            materialName = compound.getString("materialName");
            materialPath = compound.getString("materialPath");
        } else {
            materialName = "";
            materialPath = "";
        }
        texture_name = compound.getString("texture");
        textOffset = compound.getInteger("textOffset");

    }

    @Override
    public Packet getDescriptionPacket() {
        MoarSigns.packetPipeline.sendToAll(new PacketSignMainInfo(texture_name, isMetal, materialName, materialPath, fontSize, textOffset, signText, xCoord, yCoord, zCoord));
        return null;
    }

    public boolean isEditable()
    {
        return this.isEditable;
    }

    @SideOnly(Side.CLIENT)
    public void setEditable(boolean state) {
        this.isEditable = state;

        if (!state) {
            playerEditing = null;
        }
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String texture) {
        if (!worldObj.isRemote) {
            texture_name = texture;
        } else if (resourceLocation == null) {
            texture_name = texture;
            resourceLocation = MoarSigns.instance.getResourceLocation(texture, isMetal);
        }
    }

    public String getTexture_name() {
        return texture_name;
    }

    @Override
    public void func_145912_a(EntityPlayer par1EntityPlayer) {
        this.playerEditing = par1EntityPlayer;
    }

    @Override
    public EntityPlayer func_145911_b() {
        return this.playerEditing;
    }
}

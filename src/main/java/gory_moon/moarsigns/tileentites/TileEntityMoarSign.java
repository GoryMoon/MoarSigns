package gory_moon.moarsigns.tileentites;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignMainInfo;
import gory_moon.moarsigns.util.Utils;
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
    public boolean isRemovedByPlayerAndCreative;

    @Override
    public void updateEntity() {

        if (worldObj.isRemote) {

            if (fontSize != oldFontSize) {
                rows = Utils.getRows(fontSize);
                maxLength = Utils.getMaxLength(fontSize);
                oldFontSize = fontSize;
            }
            if (!textureReq) {
                textureReq = true;
                Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
                worldObj.addBlockEvent(xCoord, yCoord, zCoord, block, 0, 0);
            }
            SignInfo sign = SignRegistry.get(texture_name);
            if (sign != null && sign.property != null) sign.property.onUpdate();
        }
    }

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("nbtVersion", NBT_VERSION);
        for (int i = 0; i < 4; i++) {
            compound.setString("Text" + (i + 1), signText[i]);
        }
        compound.setBoolean("isMetal", isMetal);
        compound.setString("texture", texture_name);
        compound.setInteger("fontSize", fontSize);
        compound.setInteger("textOffset", textOffset);
    }

    public void readFromNBT(NBTTagCompound compound) {
        isEditable = false;
        super.readFromNBT(compound);

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
        texture_name = compound.getString("texture");
        fontSize = compound.getInteger("fontSize");
        rows = Utils.getRows(fontSize);
        maxLength = Utils.getMaxLength(fontSize);

        textOffset = compound.getInteger("textOffset");

    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageSignMainInfo(this, false));
    }

    public boolean isEditable() {
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

    @Override
    public void func_145912_a(EntityPlayer par1EntityPlayer) {
        this.playerEditing = par1EntityPlayer;
    }

    @Override
    public EntityPlayer func_145911_b() {
        return this.playerEditing;
    }
}

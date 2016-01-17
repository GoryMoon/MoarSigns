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
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;

public class TileEntityMoarSign extends TileEntitySign {

    public static final String NBT_VERSION_TAG = "nbtVersion";
    public static final String NBT_SETTINGS_TAG = "settings";
    public static final String NBT_LOCKED_CHANGES_TAG = "lockedChanges";
    public static final String NBT_METAL_TAG = "isMetal";
    public static final String NBT_TEXTURE_TAG = "texture";

    private final int NBT_VERSION = 2;
    public int[] rowLocations = new int[4];
    public int[] rowSizes = {0, 0, 0, 0};
    public boolean[] visibleRows = {true, true, true, true};
    public boolean[] shadowRows = new boolean[4];
    public boolean lockedChanges;
    public boolean isMetal = false;
    public String texture_name;
    public boolean showInGui = false;
    public boolean removeNoDrop;
    private boolean isEditable = true;
    private EntityPlayer playerEditing;
    private ResourceLocation resourceLocation;
    private boolean textureReq = false;

    public TileEntityMoarSign() {
        super();
        for (int i = 0; i < 4; i++) {
            rowLocations[i] = 2 + 10 * i;
        }
    }

    @Override
    public void updateEntity() {

        if (worldObj.isRemote) {
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
        compound.setInteger(NBT_VERSION_TAG, NBT_VERSION);

        for (int i = 0; i < 4; i++) {
            compound.setString("Text" + (i + 1), signText[i]);
        }

        NBTTagList settings = new NBTTagList();

        int[] loc = new int[5];
        loc[0] = 0;
        System.arraycopy(rowLocations, 0, loc, 1, 4);

        int[] size = new int[5];
        size[0] = 1;
        System.arraycopy(rowSizes, 0, size, 1, 4);

        int[] visible = new int[5];
        visible[0] = 2;
        for (int i = 0; i < 4; i++) visible[i + 1] = visibleRows[i] ? 1 : 0;

        int[] shadow = new int[5];
        shadow[0] = 3;
        for (int i = 0; i < 4; i++) shadow[i + 1] = shadowRows[i] ? 1 : 0;

        NBTTagIntArray locations = new NBTTagIntArray(loc);
        NBTTagIntArray sizes = new NBTTagIntArray(size);
        NBTTagIntArray hidden = new NBTTagIntArray(visible);
        NBTTagIntArray shadows = new NBTTagIntArray(shadow);

        settings.appendTag(locations);
        settings.appendTag(sizes);
        settings.appendTag(hidden);
        settings.appendTag(shadows);

        compound.setTag(NBT_SETTINGS_TAG, settings);
        compound.setBoolean(NBT_LOCKED_CHANGES_TAG, lockedChanges);
        compound.setBoolean(NBT_METAL_TAG, isMetal);
        if (texture_name != null)
            compound.setString(NBT_TEXTURE_TAG, texture_name);
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        int nbtVersion = compound.getInteger(NBT_VERSION_TAG);

        if (nbtVersion == 1) {
            int fontSize = compound.getInteger("fontSize");
            int rows = Utils.getRows(fontSize);

            rowSizes = new int[]{fontSize, fontSize, fontSize, fontSize};
            visibleRows = new boolean[]{false, false, false, false};
            for (int i = 0; i < rows; i++) {
                visibleRows[i] = true;
            }

            for (int i = 0; i < 4; ++i) {
                signText[i] = compound.getString("Text" + (i + 1));

                if (i > rows) {
                    signText[i] = "";
                }
            }

            int textOffset = compound.getInteger("textOffset");
            for (int i = 0; i < 4; i++) {
                int temp = Math.abs(textOffset) + rowLocations[i] - (textOffset != 0 ? 2 : 0);

                if (temp < 0) temp = 0;

                rowLocations[i] = temp;
            }


        } else if (nbtVersion == 2) {

            lockedChanges = compound.getBoolean(NBT_LOCKED_CHANGES_TAG);

            NBTTagList settings = compound.getTagList(NBT_SETTINGS_TAG, 11);

            for (int i = 0; i < settings.tagCount(); i++) {
                int[] array = settings.func_150306_c(i);
                if (array[0] == 0) {
                    System.arraycopy(array, 1, rowLocations, 0, 4);
                } else if (array[0] == 1) {
                    System.arraycopy(array, 1, rowSizes, 0, 4);
                } else if (array[0] == 2) {
                    int[] hidden = new int[4];
                    System.arraycopy(array, 1, hidden, 0, 4);
                    for (int j = 0; j < 4; j++) visibleRows[j] = hidden[j] == 1;
                } else if (array[0] == 3) {
                    int[] shadows = new int[4];
                    System.arraycopy(array, 1, shadows, 0, 4);
                    for (int j = 0; j < 4; j++) shadowRows[j] = shadows[j] == 1;
                }

            }


            for (int i = 0; i < 4; ++i) {
                signText[i] = compound.getString("Text" + (i + 1));
            }
        }
        if (compound.hasKey(NBT_METAL_TAG))
            isMetal = compound.getBoolean(NBT_METAL_TAG);
        if (compound.hasKey(NBT_TEXTURE_TAG)) texture_name = compound.getString(NBT_TEXTURE_TAG);
        if (texture_name == null || texture_name.isEmpty()) texture_name = "oak_sign";

    }

    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageSignMainInfo(this));
    }

    @Override
    public boolean func_145914_a() {
        return this.isEditable;
    }

    @SideOnly(Side.CLIENT)
    @Override
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

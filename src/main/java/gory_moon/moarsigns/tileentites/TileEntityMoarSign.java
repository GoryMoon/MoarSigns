package gory_moon.moarsigns.tileentites;

import com.google.gson.JsonParseException;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileEntityMoarSign extends TileEntitySign implements ITickable {

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

    public void setBlockType(Block block) {
        blockType = block;
    }

    @Override
    public void update() {
        if (worldObj.isRemote) {
            if (!textureReq) {
                textureReq = true;
                Block block = worldObj.getBlockState(pos).getBlock();
                worldObj.addBlockEvent(pos, block, 0, 0);
            }
            SignInfo sign = SignRegistry.get(texture_name);
            if (sign != null && sign.property != null) sign.property.onUpdate();
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(NBT_VERSION_TAG, NBT_VERSION);

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

        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        ICommandSender icommandsender = new ICommandSender()
        {
            /**
             * Get the name of this object. For players this returns their username
             */
            public String getName()
            {
                return "Sign";
            }
            /**
             * Get the formatted ChatComponent that will be used for the sender's username in chat
             */
            public ITextComponent getDisplayName()
            {
                return new TextComponentString(this.getName());
            }
            /**
             * Send a chat message to the CommandSender
             */
            public void addChatMessage(ITextComponent component)
            {
            }
            /**
             * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
             */
            public boolean canCommandSenderUseCommand(int permLevel, String commandName)
            {
                return permLevel <= 2; //Forge: Fixes  MC-75630 - Exploit with signs and command blocks
            }
            /**
             * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return the coordinates 0, 0, 0
             */
            public BlockPos getPosition()
            {
                return pos;
            }
            /**
             * Get the position vector. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return 0.0D, 0.0D, 0.0D
             */
            public Vec3d getPositionVector()
            {
                return new Vec3d((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
            }
            /**
             * Get the world, if available. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
             * return the overworld
             */
            public World getEntityWorld()
            {
                return worldObj;
            }
            /**
             * Returns the entity associated with the command sender. MAY BE NULL!
             */
            public Entity getCommandSenderEntity()
            {
                return null;
            }
            /**
             * Returns true if the command sender should be sent feedback about executed commands
             */
            public boolean sendCommandFeedback()
            {
                return false;
            }
            public void setCommandStat(CommandResultStats.Type type, int amount)
            {
            }

            @Override
            public MinecraftServer getServer() {
                return worldObj.getMinecraftServer();
            }
        };

        int nbtVersion = compound.getInteger(NBT_VERSION_TAG);

        if (nbtVersion == 1) {
            int fontSize = compound.getInteger("fontSize");
            int rows = Utils.getRows(fontSize);

            rowSizes = new int[]{fontSize, fontSize, fontSize, fontSize};
            visibleRows = new boolean[]{false, false, false, false};
            for (int i = 0; i < rows; i++) {
                visibleRows[i] = true;
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
                int[] array = settings.getIntArrayAt(i);
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
        }

        for (int i = 0; i < 4; ++i) {
            String s = compound.getString("Text" + (i + 1));

            try  {
                ITextComponent ichatcomponent = ITextComponent.Serializer.jsonToComponent(s);

                try {
                    this.signText[i] = TextComponentUtils.processComponent(icommandsender, ichatcomponent, (Entity)null);
                } catch (CommandException var7) {
                    this.signText[i] = ichatcomponent;
                }
            } catch (JsonParseException var8) {
                this.signText[i] = new TextComponentString(s);
            }
        }

        if (compound.hasKey(NBT_METAL_TAG))     isMetal = compound.getBoolean(NBT_METAL_TAG);
        if (compound.hasKey(NBT_TEXTURE_TAG))   texture_name = compound.getString(NBT_TEXTURE_TAG);
        if (texture_name == null || texture_name.isEmpty()) texture_name = "oak_sign";
        setResourceLocation(texture_name);

    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return (oldState.getBlock() != newSate.getBlock());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        markDirty();
    }

    @Override
    public boolean getIsEditable() {
        return this.isEditable;
    }

    public void setEditAble(boolean state) {
        this.isEditable = state;

        if (!state) {
            playerEditing = null;
        }
    }

    @Override
    public void setEditable(boolean editable) {
        setEditAble(editable);
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String texture) {
        if (worldObj != null && !worldObj.isRemote) {
            texture_name = texture;
        } else if (resourceLocation == null) {
            texture_name = texture;
            resourceLocation = Utils.getResourceLocation(texture, isMetal);
        }
    }

    @Override
    public void setPlayer(EntityPlayer par1EntityPlayer) {
        this.playerEditing = par1EntityPlayer;
    }

    @Override
    public EntityPlayer getPlayer() {
        return this.playerEditing;
    }

    @Override
    public boolean canRenderBreaking() {
        return true;
    }
}

package gory_moon.moarsigns.client.interfaces;

import gory_moon.moarsigns.client.interfaces.containers.ContainerDebug;
import gory_moon.moarsigns.client.interfaces.containers.ContainerExchange;
import gory_moon.moarsigns.client.interfaces.containers.ContainerPreview;
import gory_moon.moarsigns.client.interfaces.containers.InventoryExchange;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int DEBUG_TILE = 0;
    public static final int DEBUG_ITEM = 1;
    public static final int EXCHANGE = 2;
    public static final int PREVIEW = 3;
    private IInventory tempInv;

    public GuiHandler() {
        tempInv = new IInventory() {

            @Override
            public IChatComponent getDisplayName() {
                return null;
            }

            ItemStack stack;

            @Override
            public int getSizeInventory() {
                return 1;
            }

            @Override
            public ItemStack getStackInSlot(int i) {
                return stack;
            }

            @Override
            public ItemStack decrStackSize(int i, int j) {
                ItemStack itemstack = getStackInSlot(i);

                if (itemstack != null) {
                    if (itemstack.stackSize <= j) {
                        setInventorySlotContents(i, null);
                    } else {
                        itemstack = itemstack.splitStack(j);
                        markDirty();
                    }
                }

                return itemstack;
            }

            @Override
            public ItemStack removeStackFromSlot(int i) {
                ItemStack item = getStackInSlot(i);

                setInventorySlotContents(i, null);

                return item;
            }

            @Override
            public void setInventorySlotContents(int i, ItemStack itemstack) {
                stack = itemstack;

                if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
                    itemstack.stackSize = getInventoryStackLimit();
                }

                markDirty();
            }

            @Override
            public String getName() {
                return "DummyInventory";
            }

            @Override
            public boolean hasCustomName() {
                return false;
            }

            @Override
            public int getInventoryStackLimit() {
                return 1;
            }

            @Override
            public void markDirty() {

            }

            @Override
            public boolean isUseableByPlayer(EntityPlayer entityplayer) {
                return true;
            }

            @Override
            public void openInventory(EntityPlayer player) {

            }

            @Override
            public void closeInventory(EntityPlayer player) {

            }
            @Override
            public boolean isItemValidForSlot(int i, ItemStack itemstack) {
                return true;
            }

            @Override
            public int getField(int id) {
                return 0;
            }

            @Override
            public void setField(int id, int value) {

            }

            @Override
            public int getFieldCount() {
                return 0;
            }

            @Override
            public void clear() {

            }
        };
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case DEBUG_TILE:
            case DEBUG_ITEM:
                return new ContainerDebug(player.inventory, ID, tempInv);
            case EXCHANGE:
                return new ContainerExchange(player.inventory, new InventoryExchange());
            case PREVIEW:
                return new ContainerPreview();
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        BlockPos pos  = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        switch (ID) {
            case DEBUG_TILE:
            case DEBUG_ITEM:
                return new GuiDebug(player.inventory, ID, world, pos, tempInv, (TileEntityMoarSign) te);
            case EXCHANGE:
                return new GuiExchange(player.inventory, new InventoryExchange());
            case PREVIEW:
                return new GuiPreview();
        }

        return null;
    }
}

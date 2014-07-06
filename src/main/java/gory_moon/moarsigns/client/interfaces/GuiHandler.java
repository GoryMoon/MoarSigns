package gory_moon.moarsigns.client.interfaces;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    private IInventory tempInv;

    public GuiHandler() {
        tempInv = new IInventory() {
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
            public ItemStack getStackInSlotOnClosing(int i) {
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
            public String getInventoryName() {
                return "DummyInventory";
            }

            @Override
            public boolean hasCustomInventoryName() {
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
            public void openInventory() {

            }

            @Override
            public void closeInventory() {

            }

            @Override
            public boolean isItemValidForSlot(int i, ItemStack itemstack) {
                return true;
            }
        };
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case 0:
            case 1:
                return new ContainerDebug(player.inventory, ID, tempInv);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case 0:
            case 1:
                return new GuiDebug(player.inventory, ID, world, x, y, z, tempInv);
        }

        return null;
    }
}

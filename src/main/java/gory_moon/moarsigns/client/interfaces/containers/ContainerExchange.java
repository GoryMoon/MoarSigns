package gory_moon.moarsigns.client.interfaces.containers;

import gory_moon.moarsigns.client.interfaces.containers.slots.SlotInput;
import gory_moon.moarsigns.client.interfaces.containers.slots.SlotSelection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerExchange extends Container {

    public final InventoryExchange inventory;
    public InventoryPlayer playerInventory;
    public ItemStack itemToolBox;
    public boolean close;
    int toolBoxSlot;

    public ContainerExchange(InventoryPlayer inventoryPlayer, InventoryExchange exchangeInv) {
        inventory = exchangeInv;
        playerInventory = inventoryPlayer;
        toolBoxSlot = playerInventory.currentItem;
        exchangeInv.container = this;

        addSlotToContainer(new SlotInput(this, exchangeInv, 0, 22, 26));

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new SlotSelection(this, exchangeInv, x + y * 9 + 1, 58 + 18 * x, 8 + y * 18));
            }
        }

        //Player inventory
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(inventoryPlayer, x, 40 + 18 * x, 138));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 40 + 18 * x, 80 + y * 18));
            }
        }

        itemToolBox = inventoryPlayer.getCurrentItem();
        inventory.update();
    }

    public void onSlotChanged() {
        ItemStack stack = playerInventory.mainInventory[toolBoxSlot];
        if (stack == null || !stack.isItemEqual(itemToolBox)) {
            close = true;
        }
    }

    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer player) {

        int clickedSlot = par1 - inventory.getSizeInventory();

        if (clickedSlot == toolBoxSlot || (par3 == 2 && par2 == toolBoxSlot)) {
            return null;
        }

        return super.slotClick(par1, par2, par3, player);
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_) {
        inventory.clearInventory();
        super.onContainerClosed(p_75134_1_);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entity, int slotIdx) {
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotIdx);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (slotIdx > 27) {
                if (((Slot) this.inventorySlots.get(0)).isItemValid(itemStack1)) {
                    if (!this.mergeItemStack(itemStack1, 0, 1, false)) {
                        return null;
                    }
                }
            } else {
                if (slotIdx < 28) {
                    entity.inventory.setItemStack(itemStack1.copy());
                    slot.onPickupFromSlot(entity, itemStack1);
                    itemStack1 = entity.inventory.getItemStack();
                    entity.inventory.setItemStack(null);
                }

                if (!this.mergeItemStack(itemStack1, 28, 28 + 36, false)) {
                    return null;
                }
            }
            slot.onSlotChange(itemStack1, itemStack);

            if (itemStack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack1.stackSize == itemStack.stackSize) {
                return null;
            }

            if (slotIdx > 28 || slotIdx == 0) {
                slot.onPickupFromSlot(entity, itemStack1);
            }

            if (itemStack1.stackSize == 0) {
                slot.putStack(null);
                return null;
            }
        }
        return itemStack;
    }
}

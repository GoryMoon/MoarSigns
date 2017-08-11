package gory_moon.moarsigns.client.interfaces.containers;

import gory_moon.moarsigns.client.interfaces.containers.slots.SlotInput;
import gory_moon.moarsigns.client.interfaces.containers.slots.SlotSelection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ContainerExchange extends Container {

    public final InventoryExchange inventory;
    private InventoryPlayer playerInventory;
    private ItemStack itemToolBox;
    public boolean close;
    public int toolBoxSlot;

    public ContainerExchange(InventoryPlayer inventoryPlayer, InventoryExchange exchangeInv, EnumHand hand) {
        inventory = exchangeInv;
        playerInventory = inventoryPlayer;
        toolBoxSlot = hand == EnumHand.MAIN_HAND ? playerInventory.currentItem: playerInventory.getSizeInventory() - 1;
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
        ItemStack stack = playerInventory.mainInventory.get(toolBoxSlot);
        if (stack.isEmpty() || !stack.isItemEqual(itemToolBox)) {
            close = true;
        }
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (clickTypeIn != ClickType.QUICK_CRAFT && slotId >= 0) {
            int clickedSlot = slotId - inventory.getSizeInventory();

            if (clickedSlot == toolBoxSlot || (clickTypeIn == ClickType.SWAP && dragType == toolBoxSlot)) {
                return ItemStack.EMPTY;
            }

        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        inventory.clearInventory();
        if (!player.inventory.addItemStackToInventory(inventory.getStackInSlot(0))) {
            InventoryHelper.dropInventoryItems(player.world, player, inventory);
        }

        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entity, int slotIdx) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotIdx);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (slotIdx > 27) {
                if (this.inventorySlots.get(0).isItemValid(itemStack1) && !this.mergeItemStack(itemStack1, 0, 1, false))
                    return ItemStack.EMPTY;
            } else {
                if (slotIdx < 28 && !itemStack1.isEmpty()) {
                    ItemStack tempStack = entity.inventory.getItemStack();
                    entity.inventory.setItemStack(itemStack1.copy());
                    slot.onTake(entity, itemStack1);
                    itemStack1 = entity.inventory.getItemStack();
                    entity.inventory.setItemStack(tempStack);
                }

                if (!this.mergeItemStack(itemStack1, 28, 28 + 36, false)) {
                    return ItemStack.EMPTY;
                }
            }
            slot.onSlotChange(itemStack1, itemStack);

            if (itemStack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            if (slotIdx > 28 || slotIdx == 0) {
                slot.onTake(entity, itemStack1);
            }

            if (itemStack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
                return ItemStack.EMPTY;
            } else {
                slot.putStack(itemStack1);
                return itemStack1;
            }
        }
        return itemStack;
    }
}

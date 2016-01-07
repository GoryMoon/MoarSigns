package gory_moon.moarsigns.client.interfaces.containers;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSelection extends Slot {

    private final ContainerExchange container;
    private final InventoryExchange inventoryExchange;

    public SlotSelection(ContainerExchange container, InventoryExchange inventory, int i, int j, int k) {
        super(inventory, i, j, k);
        this.container = container;
        this.inventoryExchange = inventory;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer entityPlayer) {
        return !container.close && entityPlayer.inventory.getItemStack() == null;

    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
        ItemStack held = player.inventory.getItemStack();

        if (held == null) {
            inventoryExchange.decrStackSize(0, 1);
        } else {
            putStack(itemStack.copy());

            player.inventory.setItemStack(null);

            if (inventoryExchange.inventory[0] == null) {
                return;
            }

            ItemStack stack = itemStack.copy();
            stack.stackSize = inventoryExchange.inventory[0].stackSize;
            player.inventory.setItemStack(stack);
            inventoryExchange.setInventorySlotContents(0, null);
        }

        inventoryExchange.update();
    }
}

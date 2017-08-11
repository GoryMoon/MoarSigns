package gory_moon.moarsigns.client.interfaces.containers.slots;


import gory_moon.moarsigns.client.interfaces.containers.ContainerExchange;
import gory_moon.moarsigns.client.interfaces.containers.InventoryExchange;
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
        return !container.close && entityPlayer.inventory.getItemStack().isEmpty();

    }

    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack itemStack) {
        ItemStack held = player.inventory.getItemStack();

        if (held.isEmpty()) {
            inventoryExchange.decrStackSize(0, 1);
        } else {
            putStack(itemStack.copy());

            player.inventory.setItemStack(ItemStack.EMPTY);

            if (inventoryExchange.inventory.get(0).isEmpty()) {
                return ItemStack.EMPTY;
            }

            ItemStack stack = itemStack.copy();
            stack.setCount(inventoryExchange.inventory.get(0).getCount());
            player.inventory.setItemStack(stack);
            inventoryExchange.setInventorySlotContents(0, ItemStack.EMPTY);
        }

        inventoryExchange.update();
        return itemStack;
    }
}

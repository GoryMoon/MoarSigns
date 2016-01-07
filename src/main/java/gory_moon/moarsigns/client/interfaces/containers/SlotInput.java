package gory_moon.moarsigns.client.interfaces.containers;

import gory_moon.moarsigns.items.ItemMoarSign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot {

    private final ContainerExchange container;
    private final InventoryExchange inventoryExchange;

    public SlotInput(ContainerExchange container, InventoryExchange inventory, int i, int j, int k) {
        super(inventory, i, j, k);
        this.container = container;
        this.inventoryExchange = inventory;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return !container.close && itemStack != null && itemStack.getItem() instanceof ItemMoarSign;
    }

    @Override
    public boolean canTakeStack(EntityPlayer entityPlayer) {
        return !container.close && super.canTakeStack(entityPlayer);
    }

    @Override
    public void onSlotChanged() {
        if (container.close)
            return;

        super.onSlotChanged();

        inventoryExchange.update();
    }
}

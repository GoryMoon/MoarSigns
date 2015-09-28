package gory_moon.moarsigns.client.interfaces.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerDebug extends Container {

    public ContainerDebug(InventoryPlayer inventory, int ID, IInventory tempInv) {
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(inventory, x, 8 + 18 * x, 96));
        }

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 8 + 18 * x, 38 + y * 18));
            }
        }

        if (ID == 1) {
            addSlotToContainer(new Slot(tempInv, 0, 8, 11));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

}

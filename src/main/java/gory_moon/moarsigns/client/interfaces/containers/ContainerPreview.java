package gory_moon.moarsigns.client.interfaces.containers;

import gory_moon.moarsigns.client.interfaces.containers.slots.SlotPreview;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ContainerPreview extends Container {
        public List itemList = new ArrayList();

        private static InventoryBasic inventory;

        public ContainerPreview() {
            if (inventory == null) inventory = new InventoryBasic("Preview Inventory", true, 35);

            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 5; x++) {
                    addSlotToContainer(new SlotPreview(inventory, x + y * 5, 8 + 18 * x, 8 + y * 18));
                }
            }

            this.scrollTo(0.0F);
        }

        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }

        /**
         * Updates the gui slots ItemStack's based on scroll position.
         */
        public void scrollTo(float pos) {
            int i = this.itemList.size() / 5 - 6;
            int j = (int) ((double) (pos * (float) i) + 0.5D);

            if (j < 0) {
                j = 0;
            }


            for (int k = 0; k < 7; ++k) {
                for (int l = 0; l < 5; ++l) {
                    int i1 = l + (k + j) * 5;


                    if (i1 >= 0 && i1 < this.itemList.size()) {
                        inventory.setInventorySlotContents(l + k * 5, (ItemStack) this.itemList.get(i1));
                    } else {
                        inventory.setInventorySlotContents(l + k * 5, (ItemStack) null);
                    }
                }
            }
        }

        public boolean needsScrollBars() {
            return this.itemList.size() > 35;
        }

        protected void retrySlotClick(int p_75133_1_, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_) {
        }

        @Override
        public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer player) {
            return null;
        }

        public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
            return null;
        }

        public boolean func_94530_a(ItemStack stack, Slot slot) {
            return false;
        }

        public boolean canDragIntoSlot(Slot slot) {
            return false;
        }
}

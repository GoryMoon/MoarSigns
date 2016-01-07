package gory_moon.moarsigns.client.interfaces.containers;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.MaterialRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ItemSignToolbox;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

public class InventoryExchange implements IInventory {

    ItemStack[] inventory;
    ContainerExchange container;

    public InventoryExchange() {
        super();

        inventory = new ItemStack[28];
    }

    public void clearInventory() {
        for (int i = 1; i < inventory.length; i++) {
            inventory[i] = null;
        }
    }

    public void update() {
        ItemStack signStack = inventory[0];
        clearInventory();

        if (signStack == null) {
            container.onSlotChanged();
            return;
        }

        Item item = signStack.getItem();
        if (item == null) {
            return;
        }

        if (!(item instanceof ItemMoarSign)) {
            return;
        }

        String texture = ((ItemMoarSign) item).getTextureFromNBTFull(signStack.getTagCompound());
        SignInfo signInfo = SignRegistry.get(texture);

        if (signInfo == null) return;

        HashSet<MaterialInfo> materials = MaterialRegistry.get(signInfo.material.materialName);
        MoarSigns.logger.info("MaterialSize: " + (materials != null ? materials.size() : -1));
        if (materials == null || materials.size() < 1) {
            return;
        }

        ArrayList<SignInfo> signs = SignRegistry.getSignInfoFromMaterials(materials);

        if (signs == null || signs.size() < 1) {
            return;
        }
        int i = 0;
        while (i + 1 < 28 && i < signs.size()) {
            SignInfo info = signs.get(i);
            inventory[i + 1] = ModItems.sign.createMoarItemStack(info.material.path + info.itemName, info.isMetal);
            i++;
        }

        container.onSlotChanged();
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack stack;

            if (this.inventory[slot].stackSize <= amount) {
                stack = this.inventory[slot];
                this.inventory[slot] = null;
                return stack;
            } else {
                stack = this.inventory[slot].splitStack(amount);

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                return stack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);

        if (stack == null) {
            return null;
        }
        inventory[slot] = null;

        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
    }

    @Override
    public String getInventoryName() {
        return "Exchange Inventory";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 16;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return !(stack != null && (stack.getItem() instanceof ItemSignToolbox)) && i == inventory.length && stack != null && stack.getItem() instanceof ItemMoarSign;
    }
}

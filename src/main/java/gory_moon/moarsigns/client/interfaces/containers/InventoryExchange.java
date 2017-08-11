package gory_moon.moarsigns.client.interfaces.containers;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.MaterialRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ItemSignToolbox;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.ArrayList;
import java.util.HashSet;

public class InventoryExchange implements IInventory {

    public NonNullList<ItemStack> inventory = NonNullList.withSize(28, ItemStack.EMPTY);
    public ContainerExchange container;

    public InventoryExchange() {
        super();
    }

    public void clearInventory() {
        for (int i = 1; i < inventory.size(); i++) {
           setInventorySlotContents(i, ItemStack.EMPTY);
        }
    }

    public void update() {
        ItemStack signStack = inventory.get(0);
        clearInventory();

        if (signStack.isEmpty()) {
            return;
        }

        Item item = signStack.getItem();
        if (item instanceof ItemBlock && !((ItemBlock) item).getBlock().equals(Blocks.AIR)) {
            return;
        }

        if (!(item instanceof ItemMoarSign)) {
            return;
        }

        String texture = ((ItemMoarSign) item).getTextureFromNBTFull(signStack.getTagCompound());
        SignInfo signInfo = SignRegistry.get(texture);

        if (signInfo == null)
            return;

        HashSet<MaterialInfo> materials = MaterialRegistry.get(signInfo.material.materialName);
        if (materials == null || materials.size() < 1) {
            return;
        }

        ArrayList<SignInfo> signs = SignRegistry.getSignInfoFromMaterials(materials);

        if (signs.size() < 1) {
            return;
        }

        int i = 0;
        while (i + 1 < 28 && i < signs.size()) {
            SignInfo info = signs.get(i);
            setInventorySlotContents(i + 1, ModItems.SIGN.createMoarItemStack(info.material.path + info.itemName, info.isMetal));
            i++;
        }

        container.onSlotChanged();
    }

    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory.get(i);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = inventory.get(slot);
        if (!stack.isEmpty()) {
            if (stack.getCount() <= amount) {
                setInventorySlotContents(slot, ItemStack.EMPTY);
                return stack;
            } else {
                stack = stack.splitStack(amount);

                if (stack.getCount() == 0) {
                    setInventorySlotContents(slot, ItemStack.EMPTY);
                }

                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        ItemStack stack = getStackInSlot(slot);

        setInventorySlotContents(slot, ItemStack.EMPTY);
        inventory.set(slot, ItemStack.EMPTY);

        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public String getName() {
        return "Exchange Inventory";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
    }

    @Override
    public int getInventoryStackLimit() {
        return 16;
    }

    @Override
    public void markDirty() {
        // Ignoring method
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        ItemStack held = player.inventory.getStackInSlot(container.toolBoxSlot);
        return !held.isEmpty() && held.getItem() instanceof ItemSignToolbox;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        // Ignoring opening the inventory
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        // Ignoring closing the inventory
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return !(!stack.isEmpty() && (stack.getItem() instanceof ItemSignToolbox)) && i == inventory.size() && !stack.isEmpty()&& stack.getItem() instanceof ItemMoarSign;
    }

    @Override
    public int getField(int id) {
        return id;
    }

    @Override
    public void setField(int id, int value) {
        // Ignoring method
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        inventory.clear();
    }


}

package gory_moon.moarsigns.client.interfaces;

import gory_moon.moarsigns.client.interfaces.containers.ContainerExchange;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;

public class GuiExchange extends GuiContainer {

    public GuiExchange(IInventory inventory) {
        super(new ContainerExchange(inventory));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

    }
}

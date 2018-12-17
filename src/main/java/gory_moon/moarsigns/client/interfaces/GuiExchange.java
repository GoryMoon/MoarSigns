package gory_moon.moarsigns.client.interfaces;

import gory_moon.moarsigns.client.interfaces.containers.ContainerExchange;
import gory_moon.moarsigns.client.interfaces.containers.InventoryExchange;
import gory_moon.moarsigns.items.ItemSignToolbox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;

public class GuiExchange extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("moarsigns", "textures/gui/sign_exchange.png");

    public EntityPlayer player;

    public GuiExchange(InventoryPlayer inventory, InventoryExchange exchangeInv, EnumHand hand) {
        super(new ContainerExchange(inventory, exchangeInv, hand));

        player = inventory.player;
        xSize = 226;
        ySize = 162;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        inventorySlots.onContainerClosed(player);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        Iterator<ItemStack> held = player.getHeldEquipment().iterator();
        boolean isHolding = false;
        while (held.hasNext()) {
            ItemStack tmp = held.next();
            if (tmp != null && (tmp.getItem() instanceof ItemSignToolbox)) {
                isHolding = true;
            }
        }

        if (!isHolding) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1, 1, 1, 1);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}

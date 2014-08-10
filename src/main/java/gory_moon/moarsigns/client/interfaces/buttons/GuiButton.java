package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiRectangle;

import java.util.List;

public abstract class GuiButton extends GuiRectangle {

    private int srcX;
    public boolean isDisabled = false;
    public boolean isPressed = false;

    public GuiButton(int x, int y, int srcX) {
        super(x, y, 20, 20);
        this.srcX = srcX;
    }

    public void drawButton(GuiBase gui, int mouseX, int mouseY) {
        int buttonType = isDisabled ? 60: isPressed ? 40 : inRect(mouseX, mouseY) ? 20: 0;

        super.draw(gui, buttonType, 204);

        gui.drawTexturedModalRect(x + 2 + (isPressed ? 1: 0), y + 2 + (isPressed ? 0: 0), srcX, isDisabled ? 224: 240, 16, 16);
    }

    public void hoverText(GuiBase gui, int x, int y) {
        drawString(gui, x, y, getButtonInfo());
    }

    public abstract String getButtonInfo();

    public abstract void action(GuiBase gui);
}

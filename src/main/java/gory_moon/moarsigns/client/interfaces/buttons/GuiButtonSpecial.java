package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;

public abstract class GuiButtonSpecial extends GuiButton {

    protected int srcY;

    public GuiButtonSpecial(int x, int y, int w, int h, int srcX, int srcY) {
        super(x, y, w, h, srcX);
        this.srcY = srcY;
    }

    @Override
    public void drawButton(GuiBase gui, int mouseX, int mouseY) {
        int buttonType = isDisabled ? srcY + h * 2: inRect(mouseX, mouseY) ? srcY + h : srcY;

        super.draw(gui, srcX, buttonType);
    }

}

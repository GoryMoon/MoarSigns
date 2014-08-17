package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.client.interfaces.GuiRectangle;
import net.minecraft.client.gui.Gui;

import java.util.List;

public abstract class GuiButton extends GuiRectangle {

    protected int srcX;
    public boolean isDisabled = false;

    protected String newLine = "\n" + GuiColor.GRAY.toString();

    public GuiButton(int x, int y, int srcX) {
        super(x, y, 20, 20);
        this.srcX = srcX;
    }

    public void drawButton(GuiBase gui, int mouseX, int mouseY) {
        int buttonType = isDisabled ? 60: inRect(mouseX, mouseY) ? 20: 0;

        super.draw(gui, buttonType, 204);

        gui.drawTexturedModalRect(x + 2, y + 2, srcX, isDisabled ? 224: 240, 16, 16);
    }

    public void hoverText(GuiBase gui, int x, int y) {
        drawString(gui, x, y, getButtonInfo());
    }

    public abstract String getButtonInfo();

    public abstract void action(GuiBase gui);

    public void update(GuiMoarSign gui) {}

    public boolean onClick(GuiMoarSign gui, int x, int y) {
        if (inRect(x, y)) {
            action(gui);
            return true;
        }
        return false;
    }
}

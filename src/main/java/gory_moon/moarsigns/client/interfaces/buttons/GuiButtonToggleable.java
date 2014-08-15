package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;

public abstract class GuiButtonToggleable extends GuiButton {

    private boolean state;

    public GuiButtonToggleable(int x, int y, int srcX) {
        super(x, y, srcX);
    }

    @Override
    public void drawButton(GuiBase gui, int mouseX, int mouseY) {
        int buttonType = isDisabled ? 60: getState() ? 40: inRect(mouseX, mouseY) ? 20: 0;

        super.draw(gui, buttonType, 204);

        gui.drawTexturedModalRect(x + 2, y + 2, srcX, isDisabled ? 224: 240, 16, 16);
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}


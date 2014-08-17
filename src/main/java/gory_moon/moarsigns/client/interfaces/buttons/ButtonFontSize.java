package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;

public class ButtonFontSize extends GuiButtonSpecial {

    int id;
    private boolean increase;

    public ButtonFontSize(int x, int y, int id, boolean increase) {
        super(x, y, 16, 8, increase ? 224: 240, 0);
        this.increase = increase;
        this.id = id;
    }

    @Override
    public String getButtonInfo() {
        return "";
    }

    @Override
    public void action(GuiBase gui) {
        if (increase) {

        } else {

        }
    }
}

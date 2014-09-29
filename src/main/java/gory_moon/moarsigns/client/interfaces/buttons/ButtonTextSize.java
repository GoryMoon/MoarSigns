package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import net.minecraft.client.gui.GuiScreen;

public class ButtonTextSize extends GuiButtonSpecial {

    private int id;
    private boolean increase;

    public ButtonTextSize(int id, int x, int y, boolean increase) {
        super(x, y, 16, 16, increase ? 224: 240, 24);
        this.increase = increase;
        this.id = id;
    }

    @Override
    public String getButtonInfo() {
        return "";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = ((GuiMoarSign)gui);

        int change = GuiScreen.isShiftKeyDown() ? 10: 1;
        guiM.changeTextSize(id, increase ? change: -change);
    }
}

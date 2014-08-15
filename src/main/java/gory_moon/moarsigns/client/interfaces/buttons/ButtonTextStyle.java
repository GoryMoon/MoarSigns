package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;

public class ButtonTextStyle extends GuiButtonToggleable {

    public ButtonTextStyle(int x, int y) {
        super(x, y, 128);
    }

    @Override
    public String getButtonInfo() {
        return "Text Style\n" + GuiColor.GRAY + "Set the style for\n" + GuiColor.GRAY + "the following text from\n" + GuiColor.GRAY + "the cursor position";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        guiM.showTextStyles = true;
    }

    @Override
    public void update(GuiMoarSign gui) {
        if (gui.selectedTextField != -1 && gui.hasClipboardContent()) {
            isDisabled = false;
        } else if (gui.selectedTextField == -1) {
            isDisabled = true;
            gui.showTextStyles = false;
        }

        if (!getState()) gui.showTextStyles = false;
    }

    @Override
    public boolean onClick(GuiMoarSign gui, int x, int y) {
        if (super.onClick(gui, x, y)) {
            setState(!getState());
            return true;
        } else {
            setState(false);
            gui.showTextStyles = false;
            return false;
        }
    }

}

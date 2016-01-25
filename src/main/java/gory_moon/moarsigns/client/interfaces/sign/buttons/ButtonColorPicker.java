package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;

public class ButtonColorPicker extends GuiButtonToggleable {

    public ButtonColorPicker(int x, int y) {
        super(x, y, 112);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.COLORSELECTOR.translateTitles() + newLine + Localization.GUI.BUTTONS.COLORSELECTOR.translateDescriptions(newLine, newLine + newLine + Colors.CYAN.toString());
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign) gui;
        guiM.showColors = true;
        guiM.overlay = guiM.textColorsRect;
    }

    @Override
    public void update(GuiMoarSign gui) {
        if (gui.selectedTextField != -1) {
            isDisabled = false;
        } else {
            isDisabled = true;
            gui.showColors = false;
        }

        if (!getState()) {
            gui.showColors = false;
        }
    }

    @Override
    public boolean onClick(GuiMoarSign gui, int x, int y) {
        if (super.onClick(gui, x, y)) {
            setState(!getState());
            return true;
        } else {
            setState(false);
            gui.showColors = false;
            return false;
        }
    }
}

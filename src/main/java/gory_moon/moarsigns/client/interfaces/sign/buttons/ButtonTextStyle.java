package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;

public class ButtonTextStyle extends GuiButtonToggleable {

    public ButtonTextStyle(int x, int y) {
        super(x, y, 128);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.TEXTSTYLE.translateTitles() + newLine + Localization.GUI.BUTTONS.TEXTSTYLE.translateDescriptions(newLine, newLine + newLine + Colors.CYAN.toString());
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign) gui;
        guiM.showTextStyles = true;
    }

    @Override
    public void update(GuiMoarSign gui) {
        if (gui.selectedTextField != -1) {
            isDisabled = false;
        } else {
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

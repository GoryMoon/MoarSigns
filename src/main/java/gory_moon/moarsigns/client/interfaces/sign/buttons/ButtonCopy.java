package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;

public class ButtonCopy extends GuiButton {

    public ButtonCopy(int x, int y) {
        super(x, y, 16);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.COPY.translateTitles() + newLine + Localization.GUI.BUTTONS.COPY.translateDescriptions(newLine);
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign) gui;
        GuiScreen.setClipboardString(guiM.guiTextFields[guiM.selectedTextField].getSelectedText());
    }

    @Override
    public void update(GuiMoarSign gui) {
        isDisabled = gui.selectedTextField == -1 || gui.guiTextFields[gui.selectedTextField].getSelectedText().equals("");
    }
}

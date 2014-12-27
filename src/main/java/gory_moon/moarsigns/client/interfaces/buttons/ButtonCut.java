package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;

public class ButtonCut extends GuiButton {

    public ButtonCut(int x, int y) {
        super(x, y, 0);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.CUT.translateTitles() + newLine + Localization.GUI.BUTTONS.CUT.translateDescriptions(newLine);
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign) gui;
        GuiScreen.setClipboardString(guiM.guiTextFields[guiM.selectedTextField].getSelectedText());
        guiM.guiTextFields[guiM.selectedTextField].deleteFromCursor(0);
    }

    @Override
    public void update(GuiMoarSign gui) {
        isDisabled = gui.selectedTextField == -1 || gui.guiTextFields[gui.selectedTextField].getSelectedText().equals("");
    }
}

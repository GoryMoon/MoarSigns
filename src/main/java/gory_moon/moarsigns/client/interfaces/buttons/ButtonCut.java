package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;

public class ButtonCut extends GuiButton {

    public ButtonCut(int x, int y) {
        super(x, y, 0);
    }

    @Override
    public String getButtonInfo() {
        return "Cut\n" + GuiColor.GRAY + "Cuts the selected text\n" + GuiColor.GRAY + "to the clipboard";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        gui.setClipboardContent(guiM.guiTextFields[guiM.selectedTextField].getSelectedText());
        guiM.guiTextFields[guiM.selectedTextField].deleteFromCursor(0);
        guiM.guiTextFields[guiM.selectedTextField].setFocused(true);
    }
}

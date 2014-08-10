package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;

public class ButtonCopy extends GuiButton {

    public ButtonCopy(int x, int y) {
        super(x, y, 16);
    }

    @Override
    public String getButtonInfo() {
        return "Copy\n" + GuiColor.GRAY + "Copies the selected text\n" + GuiColor.GRAY + "to the clipboard";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        gui.setClipboardContent(guiM.guiTextFields[guiM.selectedTextField].getSelectedText());
        guiM.guiTextFields[guiM.selectedTextField].setFocused(true);
    }
}

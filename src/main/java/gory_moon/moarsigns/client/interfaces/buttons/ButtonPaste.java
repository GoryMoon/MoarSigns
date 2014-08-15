package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;

public class ButtonPaste extends GuiButton {

    public ButtonPaste(int x, int y) {
        super(x, y, 32);
    }

    @Override
    public String getButtonInfo() {
        return "Paste\n" + GuiColor.GRAY + "Pastes text from the clipboard";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        guiM.guiTextFields[guiM.selectedTextField].writeText(guiM.getClipboardContent());
    }

    @Override
    public void update(GuiMoarSign gui) {
        if (gui.selectedTextField != -1 && gui.hasClipboardContent()) {
            isDisabled = false;
        } else if (gui.selectedTextField == -1) {
            isDisabled = true;
        }
    }
}

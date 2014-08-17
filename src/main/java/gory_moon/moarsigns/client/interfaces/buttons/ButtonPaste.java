package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;

public class ButtonPaste extends GuiButton {

    public ButtonPaste(int x, int y) {
        super(x, y, 32);
    }

    @Override
    public String getButtonInfo() {
        return Localization.GUI.BUTTONS.PASTE.translateTitles() + newLine + Localization.GUI.BUTTONS.PASTE.translateDescriptions("");
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        guiM.guiTextFields[guiM.selectedTextField].writeText(GuiScreen.getClipboardString());
    }

    @Override
    public void update(GuiMoarSign gui) {
        if (gui.selectedTextField != -1 && !GuiScreen.getClipboardString().equals("")) {
            isDisabled = false;
        } else if (gui.selectedTextField == -1) {
            isDisabled = true;
        }
    }
}

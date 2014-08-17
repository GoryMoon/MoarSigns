package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ButtonCopySign extends GuiButton {

    public ButtonCopySign(int x, int y) {
        super(x, y, 64);
    }

    @Override
    public String getButtonInfo() {
        return Localization.GUI.BUTTONS.COPYSIGN.translateTitles() + newLine + Localization.GUI.BUTTONS.COPYSIGN.translateDescriptions(newLine);
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        String s = "moarsign";
        for (GuiTextField textField: guiM.guiTextFields) {
            s += "\u001D" + textField.getText();
        }
        GuiScreen.setClipboardString(s + "\u001Dmoarsign");
    }
}

package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ButtonCutSign extends GuiButton {

    public ButtonCutSign(int x, int y) {
        super(x, y, 48);
    }

    @Override
    public String getButtonInfo() {
        return Localization.GUI.BUTTONS.CUTSIGN.translateTitles() + newLine + Localization.GUI.BUTTONS.CUTSIGN.translateDescriptions(newLine);
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        String s = "moarsign";
        for (GuiTextField textField: guiM.guiTextFields) {
            s += "\u001D" + textField.getText();
            textField.setText("");
        }
        GuiScreen.setClipboardString(s + "\u001Dmoarsign");
    }
}

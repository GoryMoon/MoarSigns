package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import net.minecraft.client.gui.GuiTextField;

public class ButtonCutSign extends GuiButton {

    public ButtonCutSign(int x, int y) {
        super(x, y, 48);
    }

    @Override
    public String getButtonInfo() {
        return "Cut Sign\n" + GuiColor.GRAY + "Cuts all text from the\n" + GuiColor.GRAY + "sign and puts it into\n" + GuiColor.GRAY +
                "the clipboard ready to be\n" + GuiColor.GRAY + "pasted into a sign";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        String s = "moarsign";
        for (GuiTextField textField: guiM.guiTextFields) {
            s += ":" + textField.getText();
            textField.setText("");
        }
        gui.setClipboardContent(s + ":moarsign");
    }
}

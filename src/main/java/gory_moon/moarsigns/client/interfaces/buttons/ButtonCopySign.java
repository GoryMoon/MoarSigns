package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ButtonCopySign extends GuiButton {

    public ButtonCopySign(int x, int y) {
        super(x, y, 64);
    }

    @Override
    public String getButtonInfo() {
        return "Copy Sign\n" + GuiColor.GRAY + "Copies all text from the\n" + GuiColor.GRAY +
                "sign and puts it into\n" + GuiColor.GRAY + "the clipboard ready to be\n" + GuiColor.GRAY +
                "pasted into a sign";
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

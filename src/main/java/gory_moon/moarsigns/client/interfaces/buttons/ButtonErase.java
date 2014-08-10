package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import net.minecraft.client.gui.GuiTextField;

public class ButtonErase extends GuiButton {

    public ButtonErase(int x, int y) {
        super(x, y, 96);
    }

    @Override
    public String getButtonInfo() {
        return GuiColor.RED + "Erase\n" + GuiColor.GRAY + "Erases all text from sign";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;

        for (GuiTextField textField: guiM.guiTextFields) {
            textField.setText("");
        }
    }
}

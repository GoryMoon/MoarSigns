package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiTextField;

public class ButtonReset extends GuiButton {

    public ButtonReset(int x, int y) {
        super(x, y, 96);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return GuiColor.RED + Localization.GUI.BUTTONS.RESET.translateTitles() + newLine + Localization.GUI.BUTTONS.RESET.translateDescriptions(newLine);
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;

        for (GuiTextField textField: guiM.guiTextFields) {
            textField.setText("");
        }

        for (GuiButton button: guiM.buttons) {
            if (button instanceof ButtonShowHide) {
                ((ButtonShowHide)button).isHidden = false;
            }
        }

        for (int i = 0; i < guiM.visibleRows.length; i++) guiM.visibleRows[i] = true;
        for (int i = 0; i < 4; i++) guiM.rowLocations[i] = 2 + 10 * i;
        for (int i = 0; i < guiM.rowSizes.length; i++) guiM.rowSizes[i] = 0;

        guiM.buttonLock.unlock(gui);
    }
}

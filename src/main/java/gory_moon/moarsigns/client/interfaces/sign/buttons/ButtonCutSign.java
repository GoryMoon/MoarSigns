package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ButtonCutSign extends GuiButton {

    public ButtonCutSign(int x, int y) {
        super(x, y, 48);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.CUTSIGN.translateTitles() + newLine + Localization.GUI.BUTTONS.CUTSIGN.translateDescriptions(newLine, "\n" + Colors.LIGHTGRAY);
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign) gui;
        String s = "moarsign";
        for (GuiTextField textField : guiM.guiTextFields) {
            s += "\u001D" + textField.getText();
        }

        if (GuiMoarSign.isShiftKeyDown()) {
            s += "\u001E";

            int[] sizes = guiM.rowSizes;
            int[] locations = guiM.rowLocations;
            boolean[] hidden = guiM.visibleRows;
            boolean[] shadows = guiM.shadowRows;

            for (int i = 0; i < 4; i++) {
                s += (i > 0 ? "\u001F" : "") + sizes[i] + ":" + locations[i] + ":" + (hidden[i] ? 1 : 0) + ":" + (shadows[i] ? 1 : 0);
            }
        }

        guiM.buttonErase.action(gui);
        GuiScreen.setClipboardString(s + "\u001Dmoarsign");
    }
}

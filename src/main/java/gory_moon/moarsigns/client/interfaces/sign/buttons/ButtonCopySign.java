package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ButtonCopySign extends GuiButton {

    public ButtonCopySign(int x, int y) {
        super(x, y, 64);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.COPYSIGN.translateTitles() + newLine + Localization.GUI.BUTTONS.COPYSIGN.translateDescriptions(newLine, "\n" + GuiColor.LIGHTGRAY);
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

            for (int i = 0; i < 4; i++) {
                s += (i > 0 ? "\u001F": "") + sizes[i] + ":" + locations[i] + ":" + (hidden[i] ? 1: 0);
            }
        }

        GuiScreen.setClipboardString(s + "\u001Dmoarsign");
    }
}

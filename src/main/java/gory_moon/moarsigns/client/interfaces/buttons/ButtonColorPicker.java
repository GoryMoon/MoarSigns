package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;

public class ButtonColorPicker extends GuiButtonToggleable {

    public ButtonColorPicker(int x, int y) {
        super(x, y, 112);
    }

    @Override
    public String getButtonInfo() {
        return "Color Selector\n" + GuiColor.GRAY + "Add color to the text\n" + GuiColor.GRAY + "at the cursor position";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign)gui;
        guiM.showColors = !guiM.showColors;
        guiM.guiTextFields[guiM.selectedTextField].setFocused(true);
    }

}

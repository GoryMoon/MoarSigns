package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ButtonTextSize extends GuiButtonSpecial {

    private int id;
    private boolean increase;

    public ButtonTextSize(int id, int x, int y, boolean increase) {
        super(x, y, 16, 16, increase ? 224: 240, 24);
        this.increase = increase;
        this.id = id;
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        String change = GuiColor.LIGHTGRAY + (increase ? "+": "-");
        return Localization.GUI.BUTTONS.TEXT_SIZE.translateTitles(
                increase ? GuiColor.LIME.toString(): GuiColor.RED.toString(), increase ? "0": "1")
                + newLine +
                Localization.GUI.BUTTONS.TEXT_SIZE.translateDescriptions(
                        newLine, "\n" + GuiColor.WHITE,
                        GuiColor.CYAN + String.valueOf(((GuiMoarSign) gui).rowSizes[id]) + "\n" +
                                GuiColor.LIGHTBLUE, change + "1\n" + GuiColor.ORANGE, change + "10");
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = ((GuiMoarSign) gui);

        int change = GuiScreen.isShiftKeyDown() ? 10: 1;

        guiM.changeTextSize(id, increase ? change: -change);

        ArrayList<Integer> ids = new ArrayList<Integer>();
        if (guiM.buttonLock.getState()) {
            for (GuiButton next : guiM.textButtons) {
                if (next instanceof ButtonTextSize) {
                    int nextID = ((ButtonTextSize) next).id;
                    if (!ids.contains(nextID)) {
                        ids.add(nextID);
                        guiM.changeTextSize(nextID, increase ? change: -change);
                    }
                }
            }
        }
    }
}

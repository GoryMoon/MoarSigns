package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class ButtonTextLocation extends GuiButtonSpecial {

    int id;
    private boolean moveUp;

    public ButtonTextLocation(int id, int x, int y, boolean moveUp) {
        super(x, y, 16, 8, moveUp ? 224: 240, 0);
        this.moveUp = moveUp;
        this.id = id;
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.TEXT_POSITION.translateTitles(moveUp ? "0": "1") + newLine +
                Localization.GUI.BUTTONS.TEXT_POSITION.translateDescriptions(newLine, "\n" + GuiColor.LIGHTBLUE,
                        GuiColor.LIGHTGRAY.toString(), "\n" + GuiColor.ORANGE.toString());
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = ((GuiMoarSign)gui);

        int change = GuiScreen.isShiftKeyDown() ? 10: 1;

        guiM.changeTextPosition(id, moveUp ? -change: change);

        ArrayList<Integer> ids = new ArrayList<Integer>();
        if (guiM.buttonLock.getState()) {
            for (GuiButton next : guiM.textButtons) {
                if (next instanceof ButtonTextLocation) {
                    int nextID = ((ButtonTextLocation) next).id;
                    if (!ids.contains(nextID)) {
                        ids.add(nextID);
                        guiM.changeTextPosition(nextID, moveUp ? -change : change);
                    }
                }
            }
        }
    }
}

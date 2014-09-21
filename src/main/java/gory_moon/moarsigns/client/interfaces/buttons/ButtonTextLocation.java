package gory_moon.moarsigns.client.interfaces.buttons;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiMoarSign;
import gory_moon.moarsigns.util.Utils;

public class ButtonTextLocation extends GuiButtonSpecial {

    int id;
    private boolean moveUp;

    public ButtonTextLocation(int x, int y, int id, boolean moveUp) {
        super(x, y, 16, 8, moveUp ? 224: 240, 0);
        this.moveUp = moveUp;
        this.id = id;
    }

    @Override
    public String getButtonInfo() {
        return "";
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = ((GuiMoarSign)gui);
        if (moveUp) {
            guiM.textLocations[id] = guiM.textLocations[id] - 1 < 0 ? guiM.textLocations[id]: guiM.textLocations[id] - 1;
        } else {
            MoarSigns.logger.info(guiM.textLocations[id]);
            guiM.textLocations[id] = Utils.getMaxTextOffset(/*guiM.fontSizes[id]*/0) > guiM.textLocations[id] + 1 ? guiM.textLocations[id] + 1: guiM.textLocations[id];
        }
    }
}

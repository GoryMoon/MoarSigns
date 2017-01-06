package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;

import java.util.ArrayList;

public class ButtonTextShadow extends GuiButtonSpecial {

    public int id;
    public boolean hasShadow;

    public ButtonTextShadow(int id, int x, int y, boolean hasShadow) {
        super(x, y, 16, 16, hasShadow ? 224 : 240, 120);
        this.id = id;
        this.hasShadow = hasShadow;
    }

    @Override
    public void drawButton(GuiBase gui, int mouseX, int mouseY) {
        int srcX = hasShadow ? 224 : 240;
        int buttonType = isDisabled ? srcY + h * 2 : !gui.isOnOverlay(mouseX, mouseY) && inRect(mouseX, mouseY) ? srcY + h : srcY;

        super.draw(gui, srcX, buttonType);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.TEXT_SHADOW.translateTitles(hasShadow ? "1" : "0") + newLine + Localization.GUI.BUTTONS.TEXT_SHADOW.translateDescriptions(hasShadow ? "1" : "0");
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = ((GuiMoarSign) gui);

        setShadow(gui, !hasShadow);

        ArrayList<Integer> ids = new ArrayList<Integer>();
        if (guiM.buttonLock.getState()) {
            for (GuiButton next : guiM.textButtons) {
                if (next instanceof ButtonTextShadow) {
                    int nextID = ((ButtonTextShadow) next).id;
                    if (!ids.contains(nextID)) {
                        ids.add(nextID);
                        ((ButtonTextShadow) next).setShadow(gui, hasShadow);
                    }
                }
            }
        }
    }

    public void setShadow(GuiBase gui, boolean shadow) {
        GuiMoarSign guiM = ((GuiMoarSign) gui);

        guiM.shadowRows[id] = hasShadow = shadow;
    }
}

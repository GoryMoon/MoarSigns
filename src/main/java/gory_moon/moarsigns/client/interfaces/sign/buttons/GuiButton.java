package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiRectangle;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Colors;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public abstract class GuiButton extends GuiRectangle {

    public boolean isDisabled = false;
    protected int srcX;
    protected String newLine = "\n" + Colors.GRAY.toString();

    public GuiButton(int x, int y, int srcX) {
        this(x, y, 20, 20, srcX);
    }

    public GuiButton(int x, int y, int w, int h, int srcX) {
        super(x, y, w, h);
        this.srcX = srcX;
    }

    public void drawButton(GuiBase gui, int mouseX, int mouseY) {
        int buttonType = isDisabled ? 60 : !gui.isOnOverlay(mouseX, mouseY) && inRect(mouseX, mouseY) ? 20 : 0;

        super.draw(gui, buttonType, 204);

        gui.drawTexturedModalRect(x + 2, y + 2, srcX, isDisabled ? 224 : 240, 16, 16);
    }

    public void hoverText(GuiBase gui, int x, int y) {
        drawString(gui, x, y, getButtonInfo(gui));
    }

    public abstract String getButtonInfo(GuiBase gui);

    public abstract void action(GuiBase gui);

    public void update(GuiMoarSign gui) {
    }

    public boolean onClick(GuiMoarSign gui, int x, int y) {
        if (!gui.isOnOverlay(x, y) && inRect(x, y)) {
            action(gui);
            gui.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        return false;
    }
}

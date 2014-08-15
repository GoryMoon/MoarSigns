package gory_moon.moarsigns.client.interfaces;

import net.minecraft.client.gui.Gui;

public class GuiColorButton extends GuiRectangle {

    private int id;

    public GuiColorButton(int x, int y, int w, int h, int id) {
        super(x, y, w, h);
        this.id = id;
    }

    @Override
    public void draw(GuiBase gui, int srcX, int srcY) {
        Gui.drawRect(x, y, x + w, y + h, inRect(srcX, srcY) ? 0xffb2b2b2 : 0xff424242);
    }

    public int getId(GuiBase gui, int x, int y) {
        return inRect(x, y) ? id: -1;
    }

}

package gory_moon.moarsigns.client.interfaces;

import net.minecraft.client.gui.Gui;

public class GuiColorButton extends GuiRectangle {

    private int id;
    private int color1, color2;

    public GuiColorButton(int x, int y, int w, int h, int id, int color1, int color2) {
        super(x, y, w, h);
        this.id = id;
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public void draw(GuiBase gui, int srcX, int srcY) {
        Gui.drawRect(x, y, x + w, y + h, inRect(srcX, srcY) ? color1 : color2);
    }

    public int getId(GuiBase gui, int x, int y) {
        return inRect(x, y) ? id : -1;
    }

}

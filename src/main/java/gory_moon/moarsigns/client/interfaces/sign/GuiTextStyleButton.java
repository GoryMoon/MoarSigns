package gory_moon.moarsigns.client.interfaces.sign;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiRectangle;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;


public class GuiTextStyleButton extends GuiRectangle {

    private int id;
    private String style = "klmnor";
    private Localization.GUI.TEXTSTYLES[] names = {Localization.GUI.TEXTSTYLES.RANDOM, Localization.GUI.TEXTSTYLES.BOLD, Localization.GUI.TEXTSTYLES.STRIKETHROUGH, Localization.GUI.TEXTSTYLES.UNDERLINE, Localization.GUI.TEXTSTYLES.ITALIC, Localization.GUI.TEXTSTYLES.RESET};

    public GuiTextStyleButton(int x, int y, int w, int h, int id) {
        super(x, y, w, h);
        this.id = id;
    }

    @Override
    public void draw(GuiBase gui, int srcX, int srcY) {
        GlStateManager.pushMatrix();
        Gui.drawRect(x, y, x + w, y + h, inRect(srcX, srcY) ? 0xffb2b2b2 : 0xff424242);

        GlStateManager.color(1.0F, 1.0F, 1.0F);

        gui.drawCenteredString(gui.getFontRenderer(), getDrawnString(gui), x + (w / 2), y + 4, Colors.WHITE.getARGB());
        GlStateManager.popMatrix();
    }

    @SuppressWarnings("unused")
    public int getId(int x, int y) {
        return inRect(x, y) ? id : -1;
    }

    @SuppressWarnings("unused")
    public char getStyleChar() {
        return style.charAt(id);
    }

    public String getName() {
        return names[id].translate(id == names.length - 1 ? "\n" + Colors.LIGHTGRAY : "");
    }

    public String getDrawnString(GuiBase gui) {
        return ((char) 167) + "" + getStyleChar() + Localization.GUI.TEXTSTYLES.EXAMPLE_TEXT.translate("");
    }

    public void setWidth(int w) {
        this.w = w;
    }

}
package gory_moon.moarsigns.client.interfaces.sign;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.client.interfaces.GuiRectangle;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;


public class GuiTextStyleButton extends GuiRectangle {

    private int id;
    private String style = "klmnor";
    private Localization.GUI.TEXTSTYLES[] names = {Localization.GUI.TEXTSTYLES.RANDOM, Localization.GUI.TEXTSTYLES.BOLD, Localization.GUI.TEXTSTYLES.STRIKETHROUGH,
            Localization.GUI.TEXTSTYLES.UNDERLINE, Localization.GUI.TEXTSTYLES.ITALIC, Localization.GUI.TEXTSTYLES.RESET};

    public GuiTextStyleButton(int x, int y, int w, int h, int id) {
        super(x, y, w, h);
        this.id = id;
    }

    @Override
    public void draw(GuiBase gui, int srcX, int srcY) {
        GL11.glPushMatrix();
        Gui.drawRect(x, y, x + w, y + h, inRect(srcX, srcY) ? 0xffb2b2b2 : 0xff424242);

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        gui.getFontRenderer().drawString(((char) 167) + "" + getStyleChar(srcX, srcY) + "AaBbCc", x + 5, y + 5, GuiColor.WHITE.getARGB());
        GL11.glPopMatrix();
    }

    @SuppressWarnings("unused")
    public int getId(int x, int y) {
        return inRect(x, y) ? id : -1;
    }

    @SuppressWarnings("unused")
    public char getStyleChar(int x, int y) {
        return style.charAt(id);
    }

    public String getName() {
        return names[id].translate();
    }

}
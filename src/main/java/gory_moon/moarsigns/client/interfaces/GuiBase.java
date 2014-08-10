package gory_moon.moarsigns.client.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.List;

public class GuiBase extends GuiScreen {

    public int guiLeft;
    public int guiTop;
    public int xSize = 224;
    public int ySize = 200;

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    public boolean inBounds(int x, int y, int w, int h, int mX, int mY) {
        return x <= mX && mX < x + w && y <= mY && mY < y + h;
    }

    public void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        super.drawHoveringText(par1List, par2, par3, font);
    }

    public FontRenderer getFontRenderer() {
        return fontRendererObj;
    }

    public static void bindTexture(ResourceLocation resource)  {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    }

    public boolean hasClipboardContent() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable content = clipboard.getContents(null);
        return content != null && content.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    public String getClipboardContent() {
        String result = "";

        if (hasClipboardContent()) {
            Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            try {
                result = (String) content.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ignored) {}
        }

        return result;
    }

    public void setClipboardContent(String s) {
        StringSelection selection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
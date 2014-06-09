package gory_moon.moarsigns.client.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.network.ClientPacketHandler;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiMoarSign extends GuiBase {

    private static final String allowedCharacters = ChatAllowedCharacters.allowedCharacters;
    private TileEntityMoarSign entitySign;
    private int updateCounter;
    private int editLine;
    private int rows = 4;
    private int maxLength = 15;
    private int minOffset = -1;
    private int[] row;

    private int size = 0;

    private int SIZE_W = 50;
    private int SIZE_X2 = width / 2 + 65 + SIZE_W;
    private int OFFSET_X2 = width / 2 + 130 + SIZE_W;
    private int SIZE_H = 20;
    private int SIZE_Y = 105 - SIZE_H;
    private int SIZE_Y2 = 85 + SIZE_H;
    private int OFFSET_Y = 105 - SIZE_H;
    private int OFFSET_Y2 = 85 + SIZE_H;
    private int SIZE_X = width / 2 + 65;
    private int OFFSET_W = 50;
    private int OFFSET_H = 20;
    private int OFFSET_X = width / 2 + 130;


    public GuiMoarSign(TileEntityMoarSign te) {
        entitySign = te;
    }

    @Override
    public void initGui() {

        buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        SIZE_X = width / 2 + 65;
        SIZE_X2 = width / 2 + 65 + SIZE_W;
        OFFSET_X = width / 2 + 130;
        OFFSET_X2 = width / 2 + 130 + SIZE_W;

        buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
        buttonList.add(new GuiButton(1, SIZE_X - 2, SIZE_Y - 24, 54, 20, "+"));
        buttonList.add(new GuiButton(2, SIZE_X - 2, SIZE_Y + 24, 54, 20, "-"));
        buttonList.add(new GuiButton(3, this.width / 2 + 128, 95 - 34, 54, 20, "+"));
        buttonList.add(new GuiButton(4, this.width / 2 + 128, 95 + 14, 54, 20, "-"));
        entitySign.setEditable(false);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.entitySign.setEditable(true);

        for (int i = 0; i < entitySign.signText.length; i++) {
            entitySign.signText[i] = entitySign.signText[i].substring(0, Math.min(entitySign.signText[i].length(), maxLength));
            if (i > rows) {
                entitySign.signText[i] = "";
            }
        }

        ClientPacketHandler.sendSignUpdate(entitySign);
    }

    private void updateSize() {
        size = entitySign.fontSize;
        rows = size > 15 ? 1 : (size > 5 ? 2 : (size > 1 ? 3 : 4));
        maxLength = size > 17 ? 5 : (size > 13 ? 6 : (size > 10 ? 7 : (size > 7 ? 8 : (size > 4 ? 9 : (size > 3 ? 11 : (size > 1 ? 12 : (size > 0 ? 13 : 15)))))));

        row = Info.textPostion[size];
        minOffset = row[0];

        if (row.length > 1 && entitySign.textOffset > minOffset) {
            for (int i = 0; i < row.length; i++) {
                if (entitySign.textOffset < row[i]) {
                    rows = i;
                    break;
                }
            }
        } else {
            rows = 1;
            entitySign.textOffset = entitySign.textOffset < minOffset ? minOffset : entitySign.textOffset;
        }

    }

    @Override
    public void updateScreen() {
        ++updateCounter;
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                entitySign.onInventoryChanged();
                mc.thePlayer.closeScreen();
            }
            if (guiButton.id == 1) {
                entitySign.fontSize = isShiftKeyDown() ? entitySign.fontSize + 10 : entitySign.fontSize + 1;
                entitySign.fontSize = entitySign.fontSize + 1 > 20 ? 20 : entitySign.fontSize;
                updateSize();

                if (editLine > rows - 1) {
                    editLine = rows - 1;
                }

            }
            if (guiButton.id == 2) {
                entitySign.fontSize = isShiftKeyDown() ? entitySign.fontSize - 10 : entitySign.fontSize - 1;
                entitySign.fontSize = entitySign.fontSize - 1 < 0 ? 0 : entitySign.fontSize;
                updateSize();
            }

            if (guiButton.id == 3) {
                entitySign.textOffset = isShiftKeyDown() ? entitySign.textOffset + 10 : entitySign.textOffset + 1;
                entitySign.textOffset = entitySign.textOffset + 1 > 0 ? 0 : entitySign.textOffset;
                updateSize();
            }
            if (guiButton.id == 4) {
                entitySign.textOffset = isShiftKeyDown() ? entitySign.textOffset - 10 : entitySign.textOffset - 1;
                entitySign.textOffset = entitySign.textOffset - 1 < minOffset ? minOffset : entitySign.textOffset;
                updateSize();
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int key) {


        if (key == 200) {
            editLine = rows > 1 ? (editLine - 1 < 0 ? rows - 1 : editLine - 1) : 0;
        }

        if (key == 208 || key == 28 || key == 156) {
            editLine = rows > 1 ? (editLine + 1 > rows - 1 ? 0 : editLine + 1) : 0;
        }

        updateSize();

        if (key == 14 && entitySign.signText[editLine].length() > 0) {
            if (entitySign.signText[editLine].length() > maxLength)
                entitySign.signText[editLine] = entitySign.signText[editLine].substring(0, maxLength);

            entitySign.signText[editLine] = entitySign.signText[editLine].substring(0, entitySign.signText[editLine].length() - 1);
        }

        if (allowedCharacters.indexOf(typedChar) >= 0 && entitySign.signText[editLine].length() < maxLength) {
            entitySign.signText[editLine] = entitySign.signText[editLine] + typedChar;
        }

        if (key == 1) {
            mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void drawScreen(int x, int y, float par3) {


        drawDefaultBackground();
        super.drawScreen(x, y, par3);
        drawCenteredString(fontRenderer, "Edit sign message:", width / 2, 40, 16777215);
        GL11.glColor4f(1, 1, 1, 1);


        if (SIZE_X - 1 <= x && x <= SIZE_X + SIZE_W && SIZE_Y - 1 <= y && y <= SIZE_Y + SIZE_H) {
            drawRect(SIZE_X - 1, SIZE_Y - 1, SIZE_X2 + 1, SIZE_Y2 + 1, -11250336);
        } else {
            drawRect(SIZE_X - 1, SIZE_Y - 1, SIZE_X2 + 1, SIZE_Y2 + 1, -6250336);
        }

        drawRect(SIZE_X, SIZE_Y, SIZE_X2, SIZE_Y2, -16777216);

        GL11.glColor4f(1, 1, 1, 1);
        if (OFFSET_X - 1 <= x && x <= OFFSET_X + OFFSET_W && OFFSET_Y - 1 <= y && y <= OFFSET_Y + OFFSET_H) {
            drawRect(OFFSET_X - 1, OFFSET_Y - 1, OFFSET_X2 + 1, OFFSET_Y2 + 1, -11250336);
        } else {
            drawRect(OFFSET_X - 1, OFFSET_Y - 1, OFFSET_X2 + 1, OFFSET_Y2 + 1, -6250336);
        }

        drawRect(OFFSET_X, OFFSET_Y, OFFSET_X2, OFFSET_Y2, -16777216);

        drawCenteredString(fontRenderer, String.valueOf(entitySign.fontSize), width / 2 + 65 + 25, 95 - 3, 16777215);
        drawCenteredString(fontRenderer, "Font Size", width / 2 + 65 + 25, 40, 16777215);

        drawCenteredString(fontRenderer, String.valueOf(entitySign.textOffset), width / 2 + 130 + 25, 95 - 3, 16777215);
        drawCenteredString(fontRenderer, "Text Offset", width / 2 + 158, 40, 16777215);

        GL11.glColor4f(1, 1, 1, 1);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) (width / 2), 0.0F, 50.0F);
        float scale = 93.75F;
        GL11.glScalef(-scale, -scale, -scale);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        Block block = entitySign.getBlockType();

        if (block == Blocks.signStandingWood || block == Blocks.signStandingMetal) {
            float rotation = (float) (entitySign.getBlockMetadata() * 360) / 16.0F;
            GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        } else {
            int k = entitySign.getBlockMetadata();
            float f3 = 0.0F;

            if (k == 2) {
                f3 = 180.0F;
            } else if (k == 4) {
                f3 = 90.0F;
            } else if (k == 5) {
                f3 = -90.0F;
            }

            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
        }

        if (updateCounter / 6 % 2 == 0) {
            entitySign.lineBeingEdited = editLine;
        }

        TileEntityRenderer.instance.renderTileEntityAt(entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
        entitySign.lineBeingEdited = -1;
        GL11.glPopMatrix();

        if (SIZE_X - 1 <= x && x <= SIZE_X + SIZE_W && SIZE_Y - 1 <= y && y <= SIZE_Y + SIZE_H) {
            List<String> text = new ArrayList<String>();
            text.add("Text size can go from 0-20");
            text.add(GuiColor.GREEN + "Hold Shift to change with 10");
            drawHoveringText(text, x, y, fontRenderer);
        }

        if (OFFSET_X - 1 <= x && x <= OFFSET_X + OFFSET_W && OFFSET_Y - 1 <= y && y <= OFFSET_Y + OFFSET_H) {
            List<String> text = new ArrayList<String>();
            text.add("Text offset can't be bigger then 0, only lower.");
            text.add("The lowest value is dependant on the text size");

            text.add(GuiColor.GRAY + "Current lowest value is: " + GuiColor.CYAN + minOffset);
            text.add(GuiColor.GREEN + "Hold Shift to change with 10");
            drawHoveringText(text, x, y, fontRenderer);
        }
    }

}

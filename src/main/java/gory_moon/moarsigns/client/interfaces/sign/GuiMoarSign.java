package gory_moon.moarsigns.client.interfaces.sign;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.GuiColorButton;
import gory_moon.moarsigns.client.interfaces.GuiRectangle;
import gory_moon.moarsigns.client.interfaces.sign.buttons.*;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignUpdate;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class GuiMoarSign extends GuiBase {

    public static final ResourceLocation texture = new ResourceLocation(Info.TEXTURE_LOCATION, "textures/gui/sign_base.png");
    private final int TEXT_EDIT_AREA = 14;

    public int selectedTextField = 0;
    public boolean showColors = false;
    public boolean showTextStyles;
    public GuiRectangle textStyleRect;
    public GuiRectangle textColorsRect;
    public List<GuiButton> buttons = new ArrayList<GuiButton>();
    public GuiSignTextField[] guiTextFields = new GuiSignTextField[4];
    public int[] rowSizes = new int[4];
    public int[] rowLocations = new int[4];
    public boolean[] visibleRows = new boolean[4];
    public boolean[] shadowRows = new boolean[4];
    public ArrayList<GuiButton> textButtons = new ArrayList<GuiButton>();
    public ButtonReset buttonErase;
    public ButtonColorPicker buttonColorPicker;
    public ButtonTextStyle buttonTextStyle;
    public ButtonLock buttonLock;
    int oldSelectedIndex = -1;
    private GuiColorButton[] colorButtons = new GuiColorButton[16];
    private GuiTextStyleButton[] styleButtons = new GuiTextStyleButton[6];
    private ButtonCutSign buttonCutSign;
    private ButtonCopySign buttonCopySign;

    private TileEntityMoarSign entitySign;

    public GuiMoarSign(TileEntityMoarSign te) {
        entitySign = te;
    }

    public static String[] getSignTextWithColor(String[] array) {
        String[] result = new String[array.length];

        Pattern p = Pattern.compile("(?<=[" + (char) 8747 + "])([a-z0-9])(?=\\})+");
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (!s.equals("")) {

                Matcher m = p.matcher(s);
                while (m.find()) {
                    s = s.replace("{" + (char) 8747 + m.group(1) + "}", (char) 167 + m.group(1));
                }
            }
            result[i] = s;
        }

        return result;
    }

    public static String[] getSignTextWithCode(String[] array) {
        String[] result = new String[array.length];

        Pattern p = Pattern.compile("(?<=[" + (char) 167 + "])([a-z0-9])+");
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (!s.equals("")) {

                Matcher m = p.matcher(s);
                while (m.find()) {
                    s = s.replace((char) 167 + m.group(1), "{" + (char) 8747 + m.group(1) + "}");
                }
            }
            result[i] = s;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();

        buttonList.clear();
        buttons.clear();
        Keyboard.enableRepeatEvents(true);
        entitySign.setEditAble(false);

        String[] text = getSignTextWithCode(entitySign.signText);
        rowSizes = Arrays.copyOf(entitySign.rowSizes, entitySign.rowSizes.length);
        rowLocations = Arrays.copyOf(entitySign.rowLocations, entitySign.rowLocations.length);
        visibleRows = Arrays.copyOf(entitySign.visibleRows, entitySign.visibleRows.length);
        shadowRows = Arrays.copyOf(entitySign.shadowRows, entitySign.shadowRows.length);

        int k = 0;
        int j;
        for (int i = 0; i < rowSizes.length; i++) {
            int row = guiTop + 100 + k * 18;
            ButtonTextLocation btnText1 = new ButtonTextLocation(i, guiLeft + TEXT_EDIT_AREA + 108, row, true);
            ButtonTextLocation btnText2 = new ButtonTextLocation(i, guiLeft + TEXT_EDIT_AREA + 108, row + 8, false);
            ButtonTextSize btnSize1 = new ButtonTextSize(i, guiLeft + TEXT_EDIT_AREA + 125, row, true);
            ButtonTextSize btnSize2 = new ButtonTextSize(i, guiLeft + TEXT_EDIT_AREA + 142, row, false);
            ButtonTextShadow btnSha = new ButtonTextShadow(i, guiLeft + TEXT_EDIT_AREA + 159, row, shadowRows[i]);

            buttons.add(btnText1);
            buttons.add(btnText2);
            buttons.add(new ButtonShowHide(i, guiLeft + TEXT_EDIT_AREA, row, !visibleRows[i]));
            buttons.add(btnSize1);
            buttons.add(btnSize2);
            buttons.add(btnSha);

            if (i > 0) {
                textButtons.add(btnText1);
                textButtons.add(btnText2);
                textButtons.add(btnSize1);
                textButtons.add(btnSize2);
                textButtons.add(btnSha);
            }

            guiTextFields[i] = new GuiSignTextField(fontRendererObj, guiLeft + TEXT_EDIT_AREA + 17, row, 90, 16);
            guiTextFields[i].setText(text[i]);
            k++;
        }

        if (selectedTextField != -1) guiTextFields[selectedTextField].setFocused(true);


        k = 0;
        j = 0;
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new GuiColorButton(guiLeft + 150 + 5 + 14 * k, guiTop + 30 + 5 + 14 * j, 12, 12, i, 0xffb2b2b2, 0xff424242);
            if (k > 2) {
                k = 0;
                j++;
            } else k++;
        }

        for (int i = 0; i < styleButtons.length; i++) {
            styleButtons[i] = new GuiTextStyleButton(guiLeft + 150 + 5, guiTop + 30 + 5 + 18 * i, 50, 16, i);
        }
        textStyleRect = new GuiRectangle(guiLeft + 150, guiTop + 30, 60, 116);
        textColorsRect = new GuiRectangle(guiLeft + 150, guiTop + 30, 65, 65);

        buttonCutSign = new ButtonCutSign(guiLeft + 74, guiTop + 10);
        buttonCopySign = new ButtonCopySign(guiLeft + 95, guiTop + 10);
        buttonErase = new ButtonReset(guiLeft + 137, guiTop + 10);
        buttonColorPicker = new ButtonColorPicker(guiLeft + 158, guiTop + 10);
        buttonTextStyle = new ButtonTextStyle(guiLeft + 179, guiTop + 10);
        int LOCK_BASE_POS = 224;
        buttonLock = new ButtonLock(guiLeft + TEXT_EDIT_AREA + 181, guiTop + 136, LOCK_BASE_POS);

        buttons.add(new ButtonCut(guiLeft + 11, guiTop + 10));
        buttons.add(new ButtonCopy(guiLeft + 32, guiTop + 10));
        buttons.add(new ButtonPaste(guiLeft + 53, guiTop + 10));
        buttons.add(buttonCutSign);
        buttons.add(buttonCopySign);
        buttons.add(new ButtonPasteSign(guiLeft + 116, guiTop + 10));
        buttons.add(buttonErase);
        buttons.add(buttonColorPicker);
        buttons.add(buttonTextStyle);
        buttons.add(buttonLock);

        buttonList.add(new net.minecraft.client.gui.GuiButton(0, guiLeft + 12, guiTop + 174, I18n.format("gui.done", new Object[0])));

        update();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.entitySign.setEditAble(true);

        for (int i = 0; i < entitySign.signText.length; i++) {
            int maxLength = Utils.getMaxLength(rowSizes[i]);
            entitySign.signText[i] = fontRendererObj.trimStringToWidth(entitySign.signText[i], Math.min(fontRendererObj.getStringWidth(entitySign.signText[i]), maxLength - toPixelWidth(getStyleOffset(i))));
        }

        PacketHandler.INSTANCE.sendToServer(new MessageSignUpdate(entitySign));
    }

    @Override
    protected void actionPerformed(net.minecraft.client.gui.GuiButton btn) {
        if (btn.enabled) {
            if (btn.id == 0) {
                this.entitySign.markDirty();
                mc.thePlayer.closeScreen();
            }
        }
    }

    @Override
    public void updateScreen() {
        for (GuiTextField guiTextField : guiTextFields) guiTextField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int key) {

        if (selectedTextField != -1) {
            int index = 0;
            for (GuiTextField textField : guiTextFields) {
                if (textField.isFocused()) textField.textboxKeyTyped(typedChar, key);
                entitySign.signText[index++] = textField.getText();
            }
        }

        update();

        if (key == 200) {
            guiTextFields[selectedTextField].setFocused(false);
            selectedTextField = selectedTextField - 1 < 0 ? 3 : selectedTextField - 1;
            guiTextFields[selectedTextField].setFocused(true);
        }

        if (key == 208 || key == 28 || key == 156) {
            guiTextFields[selectedTextField].setFocused(false);
            selectedTextField = selectedTextField + 1 > 3 ? 0 : selectedTextField + 1;
            guiTextFields[selectedTextField].setFocused(true);
        }

        if (key == 1) {
            this.entitySign.markDirty();
            mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void drawScreen(int x, int y, float par3) {

        drawDefaultBackground();

        GL11.glColor4f(1, 1, 1, 1);

        bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        super.drawScreen(x, y, par3);
        GL11.glColor4f(1, 1, 1, 1);
        bindTexture(texture);

        for (GuiButton button : buttons) {
            button.drawButton(this, x, y);
        }

        drawVerticalLine(guiLeft + TEXT_EDIT_AREA + 189, guiTop + 126, guiTop + 136, Colors.BLACK.getARGB());
        drawVerticalLine(guiLeft + TEXT_EDIT_AREA + 189, guiTop + 150, guiTop + 162, Colors.BLACK.getARGB());
        drawHorizontalLine(guiLeft + TEXT_EDIT_AREA + 175, guiLeft + TEXT_EDIT_AREA + 189, guiTop + 126, Colors.BLACK.getARGB());
        drawHorizontalLine(guiLeft + TEXT_EDIT_AREA + 175, guiLeft + TEXT_EDIT_AREA + 181, guiTop + 144, Colors.BLACK.getARGB());
        drawHorizontalLine(guiLeft + TEXT_EDIT_AREA + 175, guiLeft + TEXT_EDIT_AREA + 189, guiTop + 162, Colors.BLACK.getARGB());

        for (GuiTextField textField : guiTextFields) textField.drawTextBox();

        GL11.glColor4f(1, 1, 1, 1);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) guiLeft + 112F, (float) guiTop - 27.0F, 40.0F);
        float scale = 93.75F;
        GL11.glScalef(-scale, -scale, -scale);

        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

        int i = entitySign.getBlockMetadata();
        entitySign.showInGui = true;

        GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
        entitySign.blockMetadata = 2;

        GL11.glTranslatef(0.0F, -0.8F, 0.0F);

        TileEntityRendererDispatcher.instance.renderTileEntityAt(entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
        GL11.glPopMatrix();
        entitySign.showInGui = false;
        entitySign.blockMetadata = i;


        if (showColors) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 90.0F);
            GL11.glDisable(GL11.GL_LIGHTING);

            bindTexture(texture);
            drawTexturedModalRect(guiLeft + 150, guiTop + 30, 0, 0, 60, 60);
            drawTexturedModalRect(guiLeft + 209, guiTop + 30, 219, 0, 5, 60);
            drawTexturedModalRect(guiLeft + 150, guiTop + 89, 0, 195, 35, 5);
            drawTexturedModalRect(guiLeft + 184, guiTop + 89, 194, 195, 30, 5);

            for (GuiColorButton color : colorButtons) {
                color.draw(this, x, y);
            }
            int k1 = 0;
            int j = 0;
            for (Colors color : Colors.values()) {
                drawRect(guiLeft + 152 + 4 + k1 * 14, guiTop + 32 + 4 + j * 14, guiLeft + 152 + 14 + k1 * 14, guiTop + 32 + 14 + j * 14, color.getARGB());
                if (k1 > 2) {
                    k1 = 0;
                    j++;
                } else k1++;
            }
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();

            for (GuiColorButton button : colorButtons) {
                if (button.inRect(x, y)) {
                    Localization.GUI.COLORS s = Localization.GUI.COLORS.values()[button.getId(this, x, y)];
                    drawHoveringText(Lists.asList(s.translate(), new String[0]), x, y, fontRendererObj);
                }
            }
        }

        if (showTextStyles) {
            GL11.glPushMatrix();

            GL11.glTranslatef(0.0F, 0.0F, 91.0F);
            GL11.glDisable(GL11.GL_LIGHTING);

            bindTexture(texture);

            drawTexturedModalRect(guiLeft + 150, guiTop + 30, 0, 0, 55, 111);
            drawTexturedModalRect(guiLeft + 205, guiTop + 30, 219, 0, 5, 111);

            drawTexturedModalRect(guiLeft + 150, guiTop + 141, 0, 195, 35, 5);
            drawTexturedModalRect(guiLeft + 180, guiTop + 141, 194, 195, 30, 5);

            zLevel += 100.0F;
            for (GuiTextStyleButton button : styleButtons) {
                button.draw(this, x, y);
            }
            zLevel -= 100.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();

            for (GuiTextStyleButton button : styleButtons) {
                if (button.inRect(x, y))
                    drawHoveringText(Arrays.asList(button.getName().split("\n")), x, y, fontRendererObj);
            }
        }

        for (GuiButton button : buttons) button.hoverText(this, x, y);
    }

    @Override
    protected void mouseClicked(int x, int y, int b) {
        super.mouseClicked(x, y, b);
        if (b == 0) {
            boolean noTextFieldClick = false;

            if (showColors) {
                int id;
                for (GuiColorButton button : colorButtons) {
                    id = button.getId(this, x, y);
                    if (id != -1) {
                        showColors = false;
                        overlay = null;
                        guiTextFields[selectedTextField].setFocused(true);
                        guiTextFields[selectedTextField].writeText("{" + (char) 8747 + Integer.toHexString(Colors.values()[id].getNumber()) + "}");
                        update();
                        noTextFieldClick = true;

                        buttonColorPicker.onClick(this, x, y);
                    }
                }
            }

            if (showTextStyles) {
                for (GuiTextStyleButton button : styleButtons) {
                    if (button.inRect(x, y)) {
                        showTextStyles = false;
                        overlay = null;
                        guiTextFields[selectedTextField].setFocused(true);
                        guiTextFields[selectedTextField].writeText("{" + (char) 8747 + button.getStyleChar(x, y) + "}");
                        update();
                        noTextFieldClick = true;

                        buttonTextStyle.onClick(this, x, y);
                    }
                }
            }

            for (GuiButton button : buttons) {
                if (!button.isDisabled && button.onClick(this, x, y)) {
                    noTextFieldClick = true;
                    update();
                    if (selectedTextField != -1) guiTextFields[selectedTextField].setFocused(true);
                }
            }

            if (!noTextFieldClick) {

                for (GuiTextField guiTextField : guiTextFields) {
                    guiTextField.mouseClicked(x, y, b);
                }

                boolean newSet = false;
                for (int i = 0; i < guiTextFields.length; i++) {
                    if (guiTextFields[i].isFocused()) {
                        selectedTextField = i;
                        newSet = true;
                    }
                }

                if (!newSet) {
                    selectedTextField = -1;
                }
            }
        }

        update();
    }

    public void update() {

        for (GuiButton button : buttons) {
            button.update(this);
        }

        String s = "";
        String[] array = new String[guiTextFields.length];

        if (!showColors && !showTextStyles)
            overlay = null;

        for (int i = 0; i < guiTextFields.length; i++) {
            array[i] = guiTextFields[i].getText();
            s += guiTextFields[i].getText();
        }

        for (int i = 0; i < rowLocations.length; i++) {
            int max = Utils.getMaxTextOffset(rowSizes[i]) - getStyleOffset(i);
            rowLocations[i] = max > rowLocations[i] ? rowLocations[i] : max;
        }

        if (!s.equals("")) {
            buttonCopySign.isDisabled = false;
            buttonCutSign.isDisabled = false;
            buttonErase.isDisabled = false;
        } else {
            buttonCopySign.isDisabled = true;
            buttonCutSign.isDisabled = true;
            buttonErase.isDisabled = true;
        }

        entitySign.signText = getSignTextWithColor(array);
        entitySign.rowLocations = Arrays.copyOf(rowLocations, rowLocations.length);
        entitySign.visibleRows = Arrays.copyOf(visibleRows, visibleRows.length);
        entitySign.rowSizes = Arrays.copyOf(rowSizes, rowSizes.length);
        entitySign.shadowRows = Arrays.copyOf(shadowRows, shadowRows.length);
        entitySign.lockedChanges = buttonLock.getState();

        if (oldSelectedIndex != selectedTextField) oldSelectedIndex = selectedTextField;

    }

    public void changeTextSize(int id, int change) {
        if (id < rowSizes.length) {
            int rowSize = rowSizes[id];

            if (change > 0) {
                rowSizes[id] = rowSize + change <= 20 ? rowSize + change : 20;
            } else if (change < 0) {
                rowSizes[id] = rowSize + change > -1 ? rowSize + change : 0;
            }
        }
    }

    public void changeTextPosition(int id, int change) {
        if (id < rowLocations.length) {
            int rowLocation = rowLocations[id];

            if (change > 0) {
                int max = Utils.getMaxTextOffset(rowSizes[id]) - getStyleOffset(id);
                rowLocations[id] = max > rowLocation + change ? rowLocation + change : max;
            } else if (change < 0) {
                rowLocations[id] = rowLocation + change < 0 ? 0 : rowLocation + change;
            }
        }
    }

    public int getStyleOffset(int id) {
        return Utils.getStyleOffset(guiTextFields[id].getText(), shadowRows[id]);
    }

    public int toPixelWidth(int i) {
        return Utils.toPixelWidth(fontRendererObj, i);
    }

}

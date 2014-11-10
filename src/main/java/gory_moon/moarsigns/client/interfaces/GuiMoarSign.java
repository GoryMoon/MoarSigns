package gory_moon.moarsigns.client.interfaces;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gory_moon.moarsigns.client.interfaces.buttons.*;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignUpdate;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
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

    public List<GuiButton> buttons = new ArrayList<GuiButton>();
    public GuiSignTextField[] guiTextFields = new GuiSignTextField[4];
    public int selectedTextField = 0;

    public boolean showColors = false;
    private GuiColorButton[] colorButtons = new GuiColorButton[16];

    public boolean showTextStyles;
    private GuiTextStyleButton[] styleButtons = new GuiTextStyleButton[6];

    public ArrayList<GuiButton> textPosSizeButtons = new ArrayList<GuiButton>();

    private ButtonCutSign buttonCutSign;
    private ButtonCopySign buttonCopySign;
    public ButtonReset buttonErase;
    public ButtonColorPicker buttonColorPicker;
    public ButtonTextStyle buttonTextStyle;
    public ButtonLock buttonLock;

    public int[] rowSizes = new int[4];
    public int[] rowLocations = new int[4];
    public boolean[] visibleRows = new boolean[4];

    public static final ResourceLocation texture = new ResourceLocation(Info.TEXTURE_LOCATION, "textures/gui/sign_base.png");
    private final int TEXT_EDIT_AREA = 23;

    private TileEntityMoarSign entitySign;
    private int maxLength = 15;

    public GuiMoarSign(TileEntityMoarSign te) {
        entitySign = te;
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonList.clear();
        buttons.clear();
        Keyboard.enableRepeatEvents(true);

        String[] text = getSignTextWithCode(entitySign.signText);
        rowLocations = Arrays.copyOf(entitySign.rowLocations, entitySign.rowLocations.length);
        visibleRows = Arrays.copyOf(entitySign.visibleRows, entitySign.visibleRows.length);

        int k = 0;
        int j;
        for (int i = 0; i < rowSizes.length; i++) {
            ButtonTextLocation btnText1 = new ButtonTextLocation(i, guiLeft + TEXT_EDIT_AREA + 108, guiTop + 110 + k * 18, true);
            ButtonTextLocation btnText2 = new ButtonTextLocation(i, guiLeft + TEXT_EDIT_AREA + 108, guiTop + 118 + k * 18, false);
            ButtonTextSize btnSize1 =  new ButtonTextSize(i, guiLeft + TEXT_EDIT_AREA + 125, guiTop + 110 + k * 18, true);
            ButtonTextSize btnSize2 = new ButtonTextSize(i, guiLeft + TEXT_EDIT_AREA + 142, guiTop + 110 + k * 18, false);

            buttons.add(btnText1);
            buttons.add(btnText2);
            buttons.add(new ButtonShowHide(i, guiLeft + TEXT_EDIT_AREA, guiTop + 110 + 18 * k, !visibleRows[i]));
            buttons.add(btnSize1);
            buttons.add(btnSize2);

            if (i > 0) {
                textPosSizeButtons.add(btnText1);
                textPosSizeButtons.add(btnText2);
                textPosSizeButtons.add(btnSize1);
                textPosSizeButtons.add(btnSize2);
            }

            guiTextFields[i] = new GuiSignTextField(fontRendererObj, guiLeft + TEXT_EDIT_AREA + 17, guiTop + 110 + 18 * k, 90, 16);
            guiTextFields[i].setText(text[i]);
            k++;
        }

        if (selectedTextField != -1) guiTextFields[selectedTextField].setFocused(true);


        k = 0;
        j = 0;
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new GuiColorButton(guiLeft + 150 + 5 + 14 * k, guiTop + 30 + 5 + 14 * j, 12, 12, i  , 0xffb2b2b2, 0xff424242);
            if (k > 2) {
                k = 0;
                j++;
            } else k++;
        }

        for (int i = 0; i < styleButtons.length; i++) {
            styleButtons[i] = new GuiTextStyleButton(guiLeft + 150 + 5, guiTop + 30 + 5 + 18 * i, 50, 16, i);
        }

        buttonCutSign = new ButtonCutSign(guiLeft + 74, guiTop + 10);
        buttonCopySign = new ButtonCopySign(guiLeft + 95, guiTop + 10);
        buttonErase = new ButtonReset(guiLeft + 137, guiTop + 10);
        buttonColorPicker = new ButtonColorPicker(guiLeft + 158, guiTop + 10);
        buttonTextStyle = new ButtonTextStyle(guiLeft + 179, guiTop + 10);
        int LOCK_BASE_POS = 224;
        buttonLock = new ButtonLock(guiLeft + TEXT_EDIT_AREA + 164, guiTop + 146, LOCK_BASE_POS);

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

        update();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.entitySign.setEditable(true);

        for (int i = 0; i < entitySign.signText.length; i++) {
            entitySign.signText[i] = fontRendererObj.trimStringToWidth(entitySign.signText[i], Math.min(fontRendererObj.getStringWidth(entitySign.signText[i]), maxLength));
        }

        PacketHandler.INSTANCE.sendToServer(new MessageSignUpdate(entitySign));
    }

    private void updateSize() {
        int size = entitySign.fontSize;
        maxLength = Utils.getMaxLength(size);
    }

    @Override
    public void updateScreen() {
        for (GuiTextField guiTextField : guiTextFields) guiTextField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int key) {

        if (selectedTextField != -1) {
            int index = 0;
            for (GuiTextField textField: guiTextFields) {
                if (textField.isFocused()) textField.textboxKeyTyped(typedChar, key);
                entitySign.signText[index++] = textField.getText();
            }
        }

        update();

        if (key == 200) {
            guiTextFields[selectedTextField].setFocused(false);
            selectedTextField = selectedTextField - 1 < 0 ? 3: selectedTextField - 1;
            guiTextFields[selectedTextField].setFocused(true);
        }

        if (key == 208 || key == 28 || key == 156) {
            guiTextFields[selectedTextField].setFocused(false);
            selectedTextField = selectedTextField + 1 > 3 ? 0: selectedTextField + 1;
            guiTextFields[selectedTextField].setFocused(true);
        }

        updateSize();

        if (key == 1) {
            mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void drawScreen(int x, int y, float par3) {

        drawDefaultBackground();
        super.drawScreen(x, y, par3);

        GL11.glColor4f(1,1,1,1);

        bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        for (GuiButton button: buttons) {
            button.drawButton(this, x, y);
        }

        drawVerticalLine(guiLeft + TEXT_EDIT_AREA + 172, guiTop + 136, guiTop + 146, GuiColor.BLACK.getARGB());
        drawVerticalLine(guiLeft + TEXT_EDIT_AREA + 172, guiTop + 160, guiTop + 172, GuiColor.BLACK.getARGB());
        drawHorizontalLine(guiLeft + TEXT_EDIT_AREA + 158, guiLeft + TEXT_EDIT_AREA + 172, guiTop + 136, GuiColor.BLACK.getARGB());
        drawHorizontalLine(guiLeft + TEXT_EDIT_AREA + 158, guiLeft + TEXT_EDIT_AREA + 164, guiTop + 154, GuiColor.BLACK.getARGB());
        drawHorizontalLine(guiLeft + TEXT_EDIT_AREA + 158, guiLeft + TEXT_EDIT_AREA + 172, guiTop + 172, GuiColor.BLACK.getARGB());

        for (GuiTextField textField: guiTextFields) textField.drawTextBox();

        GL11.glColor4f(1, 1, 1, 1);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) guiLeft + 112F, (float) guiTop - 27.0F, 40.0F);
        float scale = 93.75F;
        GL11.glScalef(-scale, -scale, -scale);

        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

        int i = entitySign.getBlockMetadata();
        entitySign.showInGui = true;
        int k = i & 7;

        float f3 = 0.0F;
        if (k == 0 || k == 1) {
            f3 = 180.0F;
        } else if (k == 2) {
            f3 = 180.0F;
        } else if (k == 4) {
            f3 = 90.0F;
        } else if (k == 5) {
            f3 = -90.0F;
        }

        GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
        if (((i & 8) >> 3) == 1) {
            entitySign.blockMetadata = 2;

        }
        GL11.glTranslatef(0.0F, -0.8F, 0.0F);

        TileEntityRendererDispatcher.instance.renderTileEntityAt(entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
        GL11.glPopMatrix();

        if (showColors) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 50.0F);

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
            for (GuiColor color: GuiColor.values()) {
                drawRect(guiLeft + 152 + 4 + k1 * 14, guiTop + 32 + 4 + j * 14, guiLeft + 152 + 14 + k1 * 14, guiTop + 32 + 14 + j * 14, color.getARGB());
                if (k1 > 2) {
                    k1 = 0;
                    j++;
                } else k1++;
            }
            GL11.glPopMatrix();

            for (GuiColorButton button: colorButtons) {
                if (button.inRect(x, y)) {
                    Localization.GUI.COLORS s = Localization.GUI.COLORS.values()[button.getId(this, x, y)];
                    drawHoveringText(Lists.asList(s.translate(), new String[0]), x, y, fontRendererObj);
                }
            }
        }

        if (showTextStyles) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 51.0F);

            bindTexture(texture);

            drawTexturedModalRect(guiLeft + 150, guiTop + 30, 0, 0, 55, 111);
            drawTexturedModalRect(guiLeft + 205, guiTop + 30, 219, 0, 5, 111);

            drawTexturedModalRect(guiLeft + 150, guiTop + 141, 0, 195, 35, 5);
            drawTexturedModalRect(guiLeft + 180, guiTop + 141, 194, 195, 30, 5);

            for (GuiTextStyleButton button: styleButtons) {
                button.draw(this, x, y);
            }
            GL11.glPopMatrix();

            for (GuiTextStyleButton button: styleButtons) {
                if (button.inRect(x, y)) drawHoveringText(Lists.asList(button.getName(), new String[0]), x, y, fontRendererObj);
            }
        }

        for (GuiButton button: buttons) button.hoverText(this, x, y);
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
                        guiTextFields[selectedTextField].setFocused(true);
                        guiTextFields[selectedTextField].writeText("{" + "\u222B" + Integer.toHexString(GuiColor.values()[id].getNumber()) + "}");
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
                        guiTextFields[selectedTextField].setFocused(true);
                        guiTextFields[selectedTextField].writeText("{" + "\u222B" + button.getStyleChar(x, y) + "}");
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

    int oldSelectedIndex = -1;
    public void update() {

        for (GuiButton button: buttons) {
            button.update(this);
        }

        String s = "";
        String[] array = new String[guiTextFields.length];

        for (int i = 0; i < guiTextFields.length; i++) {
            array[i] = guiTextFields[i].getText();
            s += guiTextFields[i].getText();
        }

        for (int i = 0; i < rowLocations.length; i++) {
            int max = Utils.getMaxTextOffset(rowSizes[i]);
            rowLocations[i] = max > rowLocations[i] ? rowLocations[i]: max;
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
        entitySign.lockedChanges = buttonLock.getState();

        if (oldSelectedIndex != selectedTextField) oldSelectedIndex = selectedTextField;

    }

    public void changeTextSize (int id, int change) {
        if (id < rowSizes.length) {
            int rowSize = rowSizes[id];

            if (change > 0) {
                rowSizes[id] = rowSize + change <= 20 ? rowSize + change: 20;
            } else if(change < 0) {
                rowSizes[id] = rowSize + change > -1 ? rowSize + change: 0;
            }
        }
    }

    public void changeTextPosition(int id, int change) {
        if (id < rowLocations.length) {
            int rowLocation = rowLocations[id];

            if (change > 0) {
                int max = Utils.getMaxTextOffset(rowSizes[id]);
                rowLocations[id] = max > rowLocation + change ? rowLocation + change: max;
            } else if (change < 0) {
                rowLocations[id] = rowLocation + change < 0 ? 0: rowLocation + change;
            }
        }
    }

    public static String[] getSignTextWithColor(String[] array) {
        String[] result = new String[array.length];

        Pattern p = Pattern.compile("(?<=[∫])([a-z0-9])(?=\\})+");
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (!s.equals("")) {

                Matcher m = p.matcher(s);
                while (m.find()) {
                    s = s.replace("{∫" + m.group(1) + "}", "§" + m.group(1));
                }
            }
            result[i] = s;
        }

        return result;
    }

    public static String[] getSignTextWithCode(String[] array) {
        String[] result = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (!s.equals("")) {
                s = s.replaceAll("(§[a-z0-9])+", "{∫$1}");
                s = s.replaceAll("([§])+", "");
            }
            result[i] = s;
        }

        return result;
    }

}

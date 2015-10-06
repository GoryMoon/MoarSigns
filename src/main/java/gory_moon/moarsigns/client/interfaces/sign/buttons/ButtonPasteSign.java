package gory_moon.moarsigns.client.interfaces.sign.buttons;

import gory_moon.moarsigns.client.interfaces.GuiBase;
import gory_moon.moarsigns.client.interfaces.sign.GuiMoarSign;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.gui.GuiScreen;

public class ButtonPasteSign extends GuiButton {

    public ButtonPasteSign(int x, int y) {
        super(x, y, 80);
    }

    @Override
    public String getButtonInfo(GuiBase gui) {
        return Localization.GUI.BUTTONS.PASTESIGN.translateTitles() + newLine + Localization.GUI.BUTTONS.PASTESIGN.translateDescriptions(newLine);
    }

    @Override
    public void action(GuiBase gui) {
        GuiMoarSign guiM = (GuiMoarSign) gui;

        String s = GuiScreen.getClipboardString();
        boolean signPaste = (!s.equals("") && s.length() > 8 && s.substring(0, 8).equals("moarsign") && (s.split("\u001D").length == 6 || (s.contains("\u001E") && s.split("\u001F").length == 4)));
        if (signPaste) {
            if (!s.contains("\u001E")) {
                String[] rows = s.split("\u001D");
                for (int i = 1; i < rows.length - 1; i++) {
                    guiM.guiTextFields[i - 1].setText(rows[i]);
                }
            } else {
                String[] text = s.split("\u001E");

                String[] rows = text[0].split("\u001D");
                for (int i = 1; i < rows.length; i++) {
                    guiM.guiTextFields[i - 1].setText(rows[i]);
                }

                String[] data = text[1].split("\u001F");
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replace("\u001Dmoarsign", "");

                    String[] values = data[i].split(":");

                    guiM.rowSizes[i] = Integer.parseInt(values[0]);
                    guiM.rowLocations[i] = Integer.parseInt(values[1]);
                    guiM.visibleRows[i] = Integer.parseInt(values[2]) == 1;
                    guiM.shadowRows[i] = Integer.parseInt(values[3]) == 1;

                    for (GuiButton button : guiM.buttons) {
                        if (button instanceof ButtonShowHide) {
                            if (((ButtonShowHide) button).id == i) {
                                ((ButtonShowHide) button).isHidden = !guiM.visibleRows[i];
                            }
                        }

                        if (button instanceof ButtonTextShadow) {
                            if (((ButtonTextShadow) button).id == i) {
                                ((ButtonTextShadow) button).setShadow(guiM, guiM.shadowRows[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(GuiMoarSign gui) {
        String clip = GuiScreen.getClipboardString();
        isDisabled = !(!clip.equals("") && clip.length() > 8 && clip.substring(0, 8).equals("moarsign") && (clip.split("\u001D").length == 6 || (clip.contains("\u001E") && clip.split("\u001F").length == 4)));
    }
}

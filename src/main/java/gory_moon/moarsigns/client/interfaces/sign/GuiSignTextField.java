package gory_moon.moarsigns.client.interfaces.sign;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiSignTextField extends GuiTextField {

    private int maxRowLength = 90;

    public GuiSignTextField(int id, FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_) {
        super(id, p_i1032_1_, p_i1032_2_, p_i1032_3_, p_i1032_4_, p_i1032_5_);
    }

    @Override
    public void setText(String text) {

        if (fontRendererInstance.getStringWidth(text) > this.maxRowLength + getSpecialsWidth(text)) {
            this.text = fontRendererInstance.trimStringToWidth(text, this.maxRowLength + getSpecialsWidth(text));
        } else {
            this.text = text;
        }

        this.setCursorPositionEnd();

    }

    @Override
    public void writeText(String text) {
        String s1 = "";
        String s2 = ChatAllowedCharacters.filterAllowedCharacters(text);
        int i = cursorPosition < this.selectionEnd ? cursorPosition : this.selectionEnd;
        int j = cursorPosition < this.selectionEnd ? this.selectionEnd : cursorPosition;
        int k = this.maxRowLength - fontRendererInstance.getStringWidth(GuiMoarSign.getSignTextWithColor(new String[]{this.text})[0].getUnformattedText()) - (fontRendererInstance.getStringWidth(this.text.substring(0, i)) - fontRendererInstance.getStringWidth(this.text.substring(0, j)));

        if (this.text.length() > 0) {
            s1 = s1 + this.text.substring(0, i);
        }

        int l;

        if (k < fontRendererInstance.getStringWidth(s2) && !isSpecial(s2)) {
            String temp = fontRendererInstance.trimStringToWidth(s2, k);
            s1 = s1 + temp;
            l = temp.length();
        } else {
            s1 = s1 + s2;
            l = s2.length();
        }

        if (l == 0 && isSpecial(s2)) {
            s1 = s1 + s2;
            l = s2.length();
        }

        if (this.text.length() > 0 && j < this.text.length()) {
            s1 = s1 + this.text.substring(j);
        }

        this.text = s1;
        this.moveCursorBy(i - this.getSelectionEnd() + l);
    }

    @Override
    public void deleteFromCursor(int p_146175_1_) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                boolean flag = p_146175_1_ < 0;

                char[][] position = {{'{'}, {(char) 8747}, {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'k', 'l', 'm', 'n', 'o', 'r'}, {'}'}};

                int offset = getCharIndex(position, this.text.charAt(this.cursorPosition + (flag && cursorPosition != 0 ? p_146175_1_ : ((!flag && this.cursorPosition == this.text.length()) ? -1 : 0))));

                if (offset > -1 && (flag && (0 <= cursorPosition - offset - 1) && this.text.length() > this.cursorPosition + (2 - (offset)) && isSpecial(this.text.substring(this.cursorPosition - offset - 1, cursorPosition + (3 - offset)))) || (!flag && 0 <= cursorPosition - offset) && this.text.length() > this.cursorPosition + (3 - offset + 1) && isSpecial(this.text.substring(this.cursorPosition - offset, cursorPosition + (3 - offset + 1)))) {

                    this.selectionEnd = flag ? (cursorPosition + (3 - offset)) : (cursorPosition + (3 - offset + 1));
                    this.cursorPosition = flag ? (cursorPosition - offset - 1) : (cursorPosition - offset);

                    this.writeText("");

                } else {

                    int j = flag ? this.cursorPosition + p_146175_1_ : this.cursorPosition;
                    int k = flag ? this.cursorPosition : this.cursorPosition + p_146175_1_;
                    String s = "";

                    if (j >= 0) {
                        s = this.text.substring(0, j);
                    }

                    if (k < this.text.length()) {
                        s = s + this.text.substring(k);
                    }

                    this.text = s;

                    if (flag) {
                        this.moveCursorBy(p_146175_1_);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void setMaxRowLength(int maxRowLength) {
        this.maxRowLength = maxRowLength;
    }

    private boolean isSpecial(String s) {
        return s.matches("(\\{" + (char) 8747 + "[0-9a-z]\\})");

    }

    private int getSpecialsWidth(String s) {
        Matcher m = Pattern.compile("(\\{" + (char) 8747 + "[0-9a-z]\\})").matcher(s);
        int i = 0;

        while (m.find())
            i += fontRendererInstance.getStringWidth(m.group(1));
        return i;
    }

    private int getCharIndex(char[][] arr, char c) {
        int pos = -1;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                pos = arr[i][j] == c ? i : pos;
            }
        }

        return pos;
    }
}

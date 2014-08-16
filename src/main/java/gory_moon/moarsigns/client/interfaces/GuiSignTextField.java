package gory_moon.moarsigns.client.interfaces;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiSignTextField extends GuiTextField {

    private int maxRowLength = 90;

    public GuiSignTextField(FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_) {
        super(p_i1032_1_, p_i1032_2_, p_i1032_3_, p_i1032_4_, p_i1032_5_);
    }

    public static boolean isAllowedCharacter(char p_71566_0_)
    {
        return  p_71566_0_ >= 32 && p_71566_0_ != 127;
    }

    @Override
    public void setText(String text) {

        System.out.println(getAmountofSpecials(text));
        if (field_146211_a.getStringWidth(text) > this.maxRowLength + getAmountofSpecials(text))
        {
            this.text = field_146211_a.trimStringToWidth(text, this.maxRowLength + getAmountofSpecials(text));
        }
        else
        {
            this.text = text;
        }

        this.setCursorPositionEnd();

    }

    @Override
    public void writeText(String text) {
        String s1 = "";
        String s2 = ChatAllowedCharacters.filerAllowedCharacters(text);
        int i = this.getCursorPosition() < this.getSelectionEnd() ? this.getCursorPosition() : this.getSelectionEnd();
        int j = this.getCursorPosition() < this.getSelectionEnd() ? this.getSelectionEnd() : this.getCursorPosition();
        int k = this.maxRowLength  - field_146211_a.getStringWidth(GuiMoarSign.getSignTextWithColor(new String[]{this.text})[0]) - (field_146211_a.getStringWidth(this.text.substring(0, i)) - field_146211_a.getStringWidth(this.text.substring(0, j)));// - field_146211_a.getStringWidth(this.text.substring(0, i - this.getSelectionEnd()));

        if (this.text.length() > 0)
        {
            s1 = s1 + this.text.substring(0, i);
        }

        int l;

        if (k < field_146211_a.getStringWidth(s2))
        {
            String temp = field_146211_a.trimStringToWidth(s2, k);
            s1 = s1 + temp;
            l = temp.length();
        }
        else
        {
            s1 = s1 + s2;
            l = s2.length();
        }

        if (isSpecial(s2)) {
            s1 = s1 + s2;
            l = s2.length();
        }

        if (this.text.length() > 0 && j < this.text.length())
        {
            s1 = s1 + this.text.substring(j);
        }

        this.text = s1;
        this.moveCursorBy(i - this.getSelectionEnd() + l);
    }

    private boolean isSpecial(String s) {
        return s.matches("(\\{[0-9a-z]\\})");

    }

    private int getAmountofSpecials(String s) {
        Matcher m = Pattern.compile("(\\{[0-9a-z]\\})").matcher(s);
        int i = 0;

        while (m.find()) i += field_146211_a.getStringWidth(m.group(1));
        return i;
    }
}

package gory_moon.moarsigns.client.interfaces;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiSignTextField extends GuiTextField {

    public GuiSignTextField(FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_) {
        super(p_i1032_1_, p_i1032_2_, p_i1032_3_, p_i1032_4_, p_i1032_5_);
    }

    public void writeText(String p_146191_1_)
    {
        String s1 = "";
        String s2 = filerAllowedCharacters(p_146191_1_);
        int i = this.getCursorPosition() < this.getSelectionEnd() ? this.getCursorPosition() : this.getSelectionEnd();
        int j = this.getCursorPosition() < this.getSelectionEnd() ? this.getSelectionEnd() : this.getCursorPosition();
        int k = this.getMaxStringLength() - this.getText().length() - (i - this.getSelectionEnd());
        boolean flag = false;

        if (this.getText().length() > 0)
        {
            s1 = s1 + this.getText().substring(0, i);
        }

        int l;

        if (k < s2.length())
        {
            s1 = s1 + s2.substring(0, k);
            l = k;
        }
        else
        {
            s1 = s1 + s2;
            l = s2.length();
        }

        if (this.getText().length() > 0 && j < this.getText().length())
        {
            s1 = s1 + this.getText().substring(j);
        }

        this.setText(s1);
        this.moveCursorBy(i - this.getSelectionEnd() + l);
    }

    public static boolean isAllowedCharacter(char p_71566_0_)
    {
        return  p_71566_0_ >= 32 && p_71566_0_ != 127;
    }

    /**
     * Filter string by only keeping those characters for which isAllowedCharacter() returns true.
     */
    public static String filerAllowedCharacters(String p_71565_0_)
    {
        StringBuilder stringbuilder = new StringBuilder();
        char[] achar = p_71565_0_.toCharArray();
        int i = achar.length;

        for (char c0 : achar) {
            if (isAllowedCharacter(c0)) {
                stringbuilder.append(c0);
            }
        }

        return stringbuilder.toString();
    }

}

package gory_moon.moarsigns.util;

import net.minecraft.client.gui.FontRenderer;

import java.util.regex.Pattern;

public class Utils {

    private static int[] maxLengths = {90, 82, 76, 70, 66, 62, 58, 54, 52, 50, 48, 46, 44, 42, 40, 38, 36, 36, 34, 32, 32};
    private static int[] maxTextLocation = {36, 32, 29, 27, 24, 22, 21, 19, 17, 16, 15, 14, 13, 12, 11, 11, 10, 9, 9, 8, 8};

    public static int getRows(int size) {
        return size > 15 ? 1: (size > 5 ? 2: (size > 1 ? 3: 4));
    }

    public static int getMaxLength(int size) {
        return maxLengths.length > size ? maxLengths[size]: 0;
    }

    public static int getMaxTextOffset(int size) {
        return maxTextLocation.length > size ? maxTextLocation[size]: 0;
    }

    public static boolean isAllowedCharacter(char p_71566_0_) {
        return p_71566_0_ >= 32 && p_71566_0_ != 127;
    }

    public static int getStyleOffset(String s, boolean b) {
        return (isUnderlined(s) ? 1: 0) + (b ? 1: 0);
    }

    public static int toPixelWidth(FontRenderer fr, int i) {
        return fr.getCharWidth('i') * i;
    }

    public static boolean isUnderlined(String s) {
        return Pattern.compile("(\\{\u222bn\\})|(\u00a7n)").matcher(s).find();
    }

}

package gory_moon.moarsigns.util;

public class Utils {

    @Deprecated
    public static int getRows(int size) {
        return size > 15 ? 1 : (size > 5 ? 2 : (size > 1 ? 3 : 4));
    }

    private static int[] maxLengths = {90, 82, 76, 70, 66, 62, 58, 54, 52, 50, 48, 46, 44, 42, 40, 38, 36, 36, 34, 32, 32};
    public static int getMaxLength(int size) {
        return maxLengths[size];
    }

    private static int[] maxTextLocation = {36, 32, 29, 27, 25, 23, 21, 20, 18, 17, 16, 15, 14, 13, 12, 12, 11, 10, 10, 9, 9};

    public static int getMaxTextOffset(int size) {
        return maxTextLocation.length > size ? maxTextLocation[size]: 0;
    }

}

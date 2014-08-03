package gory_moon.moarsigns.util;

public class Utils {

    public static int getRows(int size) {
        return size > 15 ? 1 : (size > 5 ? 2 : (size > 1 ? 3 : 4));
    }

    private static int[] maxLenghts = {90, 82, 76, 70, 66, 62, 58, 54, 52, 50, 48, 46, 44, 42, 40, 38, 36, 36, 34, 32, 32};
    public static int getMaxLength(int size) {
        return maxLenghts[size];
    }

}

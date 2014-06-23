package gory_moon.moarsigns.util;

public class Utils {

    public static int getRows(int size) {
        return size > 15 ? 1 : (size > 5 ? 2 : (size > 1 ? 3 : 4));
    }

    public static int getMaxLength(int size) {
        return size > 17 ? 5 : (size > 13 ? 6 : (size > 10 ? 7 : (size > 7 ? 8 : (size > 4 ? 9 : (size > 3 ? 11 : (size > 1 ? 12 : (size > 0 ? 13 : 15)))))));
    }

}

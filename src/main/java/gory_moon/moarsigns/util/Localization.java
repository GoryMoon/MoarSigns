package gory_moon.moarsigns.util;

import net.minecraft.util.StatCollector;

public final class Localization {

    private static String translateString(String key, String... vars) {
        String result = StatCollector.translateToLocal(key);

        for (int i = 0; i < vars.length; i++) {
            String optionCheck = "[%" + (i + 1) + "->";
            int pos = result.indexOf(optionCheck);

            if (pos != -1) {
                int endPos = result.indexOf("]");
                if (endPos != -1) {
                    String[] options = result.substring(pos + optionCheck.length(), endPos).split("\\|");
                    int pickedOption = vars[i].equals("1") ? 1: 0;
                    if (options.length > pickedOption) {
                        String opt = options[pickedOption];
                        result = result.substring(0, pos) + opt + result.substring(endPos + 1);

                        i--;
                    }
                }
            } else {
                result = result.replace("[%" + (i + 1) + "]", vars[i]);
            }
        }

        return result;
    }

    public static class ITEM {

        public static enum SIGNTOOLBOX {

            CHANGE,
            EDIT,
            ROTATE,
            MOVE,
            COPY,
            EXCHANGE;

            private String key;

            private SIGNTOOLBOX() {
                this.key = toString().toLowerCase();
            }

            public String translate(String... vars) {
                return Localization.translateString("item.moarsign:signtoolbox.description." + key, vars);
            }
        }

    }

    public static class GUI {

        public static enum BUTTONS {

            CUT,
            COPY,
            PASTE,
            CUTSIGN,
            COPYSIGN,
            PASTESIGN,
            RESET,
            COLORSELECTOR,
            TEXTSTYLE,
            LOCK,
            TEXT_SIZE,
            TEXT_POSITION,
            TEXT_SHOWHIDE,
            TEXT_SHADOW;

            private String key;

            private BUTTONS() {
                this.key = toString().toLowerCase().replaceAll("_", ".");
            }

            public String translateTitles(String... vars) {
                return Localization.translateString("gui.moarsigns:button.title." + key, vars);
            }

            public String translateDescriptions(String... vars) {
                return Localization.translateString("gui.moarsigns:button.description." + key, vars);
            }
        }

        public static enum COLORS {
            BLACK,
            BLUE,
            GREEN,
            CYAN,
            RED,
            PURPLE,
            ORANGE,
            LIGHTGRAY,
            GRAY,
            LIGHTBLUE,
            LIME,
            TURQUISE,
            PINK,
            MAGNETA,
            YELLOW,
            WHITE;

            private String key;

            private COLORS() {
                this.key = toString().toLowerCase();
            }

            public String translate() {
                return Localization.translateString("gui.moarsigns:color." + key, "");
            }
        }

        public static enum TEXTSTYLES {
            RANDOM,
            BOLD,
            STRIKETHROUGH,
            UNDERLINE,
            ITALIC,
            RESET;

            private String key;

            private TEXTSTYLES() {
                this.key = toString().toLowerCase();
            }

            public String translate() {
                return Localization.translateString("gui.moarsigns:textstyle." + key, "");
            }
        }
    }
}

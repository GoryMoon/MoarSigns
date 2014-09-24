package gory_moon.moarsigns.util;

import net.minecraft.util.StatCollector;

public final class Localization {

    private static String translateString(String key, String... vars) {
        String result = StatCollector.translateToLocal(key);

        for (int i = 0; i < vars.length; i++) {
            result = result.replace("[%" + (i + 1) + "]", vars[i]);
        }

        return result;
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
            TEXTSTYLE;

            private String key;

            private BUTTONS() {
                this.key = toString().toLowerCase();
            }

            public String translateTitles() {
                return Localization.translateString("gui.moarsigns:button.title." + key, "");
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

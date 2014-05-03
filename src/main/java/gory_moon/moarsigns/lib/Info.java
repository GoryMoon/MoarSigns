package gory_moon.moarsigns.lib;

public class Info {
    public static final String TEXTURE_LOCATION = "moarsigns";

    public static int SIGN_STAND_WOOD_ID;
    public static final int SIGN_STAND_WOOD_DEFAULT_ID = 771;
    public static final String SIGN_STAND_WOOD_KEY = "MoarSignsStandingWood";

    public static int SIGN_WALL_WOOD_ID;
    public static final int SIGN_WALL_WOOD_DEFAULT_ID = 772;
    public static final String SIGN_WALL_WOOD_KEY = "MoarSignsWallWood";


    public static int SIGN_STAND_METAL_ID;
    public static final int SIGN_STAND_METAL_DEFAULT_ID = 773;
    public static final String SIGN_STAND_METAL_KEY = "MoarSignsStandingMetal";

    public static int SIGN_WALL_METAL_ID;
    public static final int SIGN_WALL_METAL_DEFAULT_ID = 774;
    public static final String SIGN_WALL_METAL_KEY = "MoarSignsWallMetal";


    public static final String SIGN_TE_ID = "moarSignsTileEntity";


    
    public static int SIGN_ITEM_ID;
    public static final int SIGN_ITEM_DEFAULT_ID = 2270;
    public static final String SIGN_ITEM_KEY = "MoarSignsItem";

    public static int DEBUG_ITEM_ID;
    public static final int DEBUG_ITEM_DEFAULT_ID = 2271;
    public static final String DEBUG_ITEM_KEY = "DebugItem";

    public static int NUGGET_ITEM_ID;
    public static final int NUGGET_ITEM_DEFAULT_ID = 2272;
    public static final String NUGGET_ITEM_KEY = "NuggetItems";
    public static final String[] NUGGET_ITEM_NAMES = {"Diamond Nugget", "Iron Nugget", "Bronze Nugget", "Copper Nugget", "Tin Nugget", "Silver Nugget"};
    public static final String[] NUGGET_INGET_UNLOCS = {"item.diamond", "item.ingotIron", "itemIngotBronze", "itemIngotCopper", "itemIngotTin", "item.factorization:silver_ingot"};


    public static TextPos[][] textPostion = new TextPos[][] {
            {new TextPos(-36), new TextPos(-26), new TextPos(-16), new TextPos(-6)},
            {new TextPos(-31), new TextPos(-21), new TextPos(-11), new TextPos(-1)},
            {new TextPos(-28), new TextPos(-18), new TextPos(-8)},
            {new TextPos(-26), new TextPos(-16), new TextPos(-6)},
            {new TextPos(-23), new TextPos(-13), new TextPos(-3)},

            {new TextPos(-21), new TextPos(-11), new TextPos(-1)},
            {new TextPos(-20), new TextPos(-10)},
            {new TextPos(-18), new TextPos(-8)},
            {new TextPos(-17), new TextPos(-7)},
            {new TextPos(-16), new TextPos(-6)},
            {new TextPos(-15), new TextPos(-5)},
            {new TextPos(-14), new TextPos(-4)},
            {new TextPos(-13), new TextPos(-3)},
            {new TextPos(-11), new TextPos(-1)},
            {new TextPos(-10), new TextPos(0)},
            {new TextPos(-9)},
            {new TextPos(-9)},
            {new TextPos(-8)},
            {new TextPos(-8)},
            {new TextPos(-7)},
            {new TextPos(-7)},
    };

    public static class TextPos {
        public int offset;

        public TextPos( int offset) {
            this.offset = offset;
        }
    }

}
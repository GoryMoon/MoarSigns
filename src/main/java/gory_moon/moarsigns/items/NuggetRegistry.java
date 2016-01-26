package gory_moon.moarsigns.items;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class NuggetRegistry {

    private static ArrayList<NuggetInfo> nuggets = Lists.newArrayList();

    public static void init() {
        registerNugget("diamond_nugget", "nuggetDiamond", "item.diamond", true);
        registerNugget("iron_nugget", "nuggetIron", "item.ingotIron", true);
        registerNugget("emerald_nugget", "nuggetEmerald", "item.emerald", true);
        registerNugget("bronze_nugget_ic2", "nuggetBronze", "ic2.itemIngotBronze", false);
        registerNugget("copper_nugget_ic2", "nuggetCopper", "ic2.itemIngotCopper", false);
        registerNugget("tin_nugget_ic2", "nuggetTin", "ic2.itemIngotTin", false);
        registerNugget("refined_iron_nugget_ic2", "steelNugget", "ic2.itemIngotAdvIron", false);
        registerNugget("lead_nugget_ic2", "leadNugget", "ic2.itemIngotLead", false);
        registerNugget("tin_nugget_forestry", "tinNugget", "item.for.ingotTin", false);
        registerNugget("bronze_nugget_forestry", "bronzeNugget", "item.for.ingotBronze", false);
        registerNugget("copper_nugget_forestry", "copperNugget", "item.for.ingotCopper", false);
        registerNugget("silver_nugget_factorization", "nuggetSilver", "item.factorization:silver_ingot", false);
        registerNugget("lead_nugget_factorization", "nuggetLead", "item.factorization:lead_ingot", false);
        registerNugget("darkiron_nugget_factorization", "nuggetFzDarkIron", "item.factorization:dark_iron_ingot", false);
    }

    public static void registerNugget(String unlocName, String oreName, String ingotName, boolean needed) {
        nuggets.add(new NuggetInfo(unlocName, oreName, ingotName, needed));
    }

    public static void registerNugget(int id, String unlocName, String oreName, String ingotName, boolean needed) {
        nuggets.add(id, new NuggetInfo(unlocName, oreName, ingotName, needed));
    }

    public static ArrayList<NuggetInfo> getNuggets() {
        return nuggets;
    }

    public static void setNeeded(int i, boolean needed) {
        nuggets.get(i).needed = needed;
    }

    public static String getUnlocName(int i) {
        return nuggets.get(i).unlocName;
    }

    public static int size() {
        return nuggets.size();
    }

    public static boolean getNeeded(int i) {
        return nuggets.get(i).needed;
    }

    public static String getIngotName(int i) {
        return nuggets.get(i).ingotName;
    }

    public static String getOreName(int i) {
        return nuggets.get(i).oreName;
    }

    public static class NuggetInfo {

        public String unlocName;
        public String oreName;
        public String ingotName;
        public boolean needed;

        public NuggetInfo(String unlocName, String oreName, String ingotName, boolean needed) {
            this.unlocName = unlocName;
            this.oreName = oreName;
            this.ingotName = ingotName;
            this.needed = needed;
        }
    }

}

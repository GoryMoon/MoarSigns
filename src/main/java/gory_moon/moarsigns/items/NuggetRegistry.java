package gory_moon.moarsigns.items;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class NuggetRegistry {

    private static ArrayList<NuggetInfo> nuggets = Lists.newArrayList();

    public static void init() {
        registerNugget("diamond_nugget",                    "nuggetDiamond",        "item.diamond",                         true);
        registerNugget("iron_nugget",                       "nuggetIron",           "item.ingotIron",                       true);
        registerNugget("emerald_nugget",                    "nuggetEmerald",        "item.emerald",                         true);
        registerNugget("lapis_nugget",                      "nuggetLapis",          "item.dyePowder.blue",                  true);
        registerNugget("quartz_nugget",                     "nuggetQuartz",         "item.netherquartz",                    true);
        registerNugget("bronze_nugget_ic2",                 "nuggetBronze",         "ic2.ingot.bronze"                          );
        registerNugget("copper_nugget_ic2",                 "nuggetCopper",         "ic2.ingot.copper"                          );
        registerNugget("tin_nugget_ic2",                    "nuggetTin",            "ic2.ingot.tin"                             );
        registerNugget("steel_nugget_ic2",                  "nuggetSteel",          "ic2.ingot.steel"                           );
        registerNugget("lead_nugget_ic2",                   "nuggetLead",           "ic2.ingot.lead"                            );
        registerNugget("tin_nugget_forestry",               "nuggetTin",            "item.for.ingotTin"                         );
        registerNugget("bronze_nugget_forestry",            "nuggetBronze",         "item.for.ingotBronze"                      );
        registerNugget("copper_nugget_forestry",            "nuggetCopper",         "item.for.ingotCopper"                      );
        registerNugget("silver_nugget_factorization",       "nuggetSilver",         "item.factorization:silver_ingot"           );
        registerNugget("lead_nugget_factorization",         "nuggetLead",           "item.factorization:lead_ingot"             );
        registerNugget("darkiron_nugget_factorization",     "nuggetFzDarkIron",     "item.factorization:dark_iron_ingot"        );
        registerNugget("aluminium_nugget_techreborn",       "nuggetAluminium",      "item.techreborn.ingot.aluminum"            );
        registerNugget("brass_nugget_techreborn",           "nuggetBrass",          "item.techreborn.ingot.brass"               );
        registerNugget("chrome_nugget_techreborn",          "nuggetChrome",         "item.techreborn.ingot.chrome"              );
        registerNugget("copper_nugget_techreborn",          "nuggetCopper",         "item.techreborn.ingot.copper"              );
        registerNugget("electrum_nugget_techreborn",        "nuggetElectrum",       "item.techreborn.ingot.electrum"            );
        registerNugget("invar_nugget_techreborn",           "nuggetInvar",          "item.techreborn.ingot.invar"               );
        registerNugget("iridium_nugget_techreborn",         "nuggetIridium",        "item.techreborn.ingot.iridium"             );
        registerNugget("lead_nugget_techreborn",            "nuggetLead",           "item.techreborn.ingot.lead"                );
        registerNugget("nickel_nugget_techreborn",          "nuggetNickel",         "item.techreborn.ingot.nickel"              );
        registerNugget("platinum_nugget_techreborn",        "nuggetPlatinum",       "item.techreborn.ingot.platinum"            );
        registerNugget("silver_nugget_techreborn",          "nuggetSilver",         "item.techreborn.ingot.silver"              );
        registerNugget("steel_nugget_techreborn",           "nuggetSteel",          "item.techreborn.ingot.steel"               );
        registerNugget("tin_nugget_techreborn",             "nuggetTin",            "item.techreborn.ingot.tin"                 );
        registerNugget("titanium_nugget_techreborn",        "nuggetTitanium",       "item.techreborn.ingot.titanium"            );
        registerNugget("tungsten_nugget_techreborn",        "nuggetTungsten",       "item.techreborn.ingot.tungsten"            );
        registerNugget("tungstensteel_nugget_techreborn",   "nuggetTungstenSteel",  "item.techreborn.ingot.tungstensteel"       );
        registerNugget("zinc_nugget_techreborn",            "nuggetZinc",           "item.techreborn.ingot.zinc"                );
        registerNugget("refinediron_nugget_techreborn",     "nuggetRefinedIron",    "item.techreborn.ingot.refinedIron"         );

    }

    public static void registerNugget(String unlocName, String oreName, String ingotName, boolean needed) {
        nuggets.add(new NuggetInfo(unlocName, oreName, ingotName, needed));
    }

    public static void registerNugget(int id, String unlocName, String oreName, String ingotName, boolean needed) {
        nuggets.add(id, new NuggetInfo(unlocName, oreName, ingotName, needed));
    }

    public static void registerNugget(String unlocName, String oreName, String ingotName) {
        registerNugget(unlocName, oreName, ingotName, false);
    }

    public static void registerNugget(int id, String unlocName, String oreName, String ingotName) {
        registerNugget(id, unlocName, oreName, ingotName, false);
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

    public static ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (NuggetInfo nugget: nuggets)
            names.add("nuggets/" + nugget.unlocName);

        return names;
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

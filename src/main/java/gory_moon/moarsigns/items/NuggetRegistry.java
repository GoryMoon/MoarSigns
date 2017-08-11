package gory_moon.moarsigns.items;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class NuggetRegistry {

    private static HashMap<String, NuggetInfo> nuggets = Maps.newHashMap();
    private static HashMap<Integer, String> idMap = Maps.newHashMap();

    public static void init() {
        registerNugget(1, "diamond_nugget",                     "nuggetDiamond",        "",                 "item.diamond",             true);
        registerNugget(2, "emerald_nugget",                     "nuggetEmerald",        "",                 "item.emerald",             true);
        registerNugget(3, "lapis_nugget",                       "nuggetLapis",          "",                 "item.dyePowder.blue",      true);
        registerNugget(4, "quartz_nugget",                      "nuggetQuartz",         "",                 "item.netherquartz",        true);
        registerNugget(5, "bronze_nugget_ic2",                  "nuggetBronze",         "ic2/",             "ic2.ingot.bronze"                      );
        registerNugget(6, "copper_nugget_ic2",                  "nuggetCopper",         "ic2/",             "ic2.ingot.copper"                      );
        registerNugget(7, "tin_nugget_ic2",                     "nuggetTin",            "ic2/",             "ic2.ingot.tin"                         );
        registerNugget(8, "steel_nugget_ic2",                   "nuggetSteel",          "ic2/",             "ic2.ingot.steel"                       );
        registerNugget(9, "lead_nugget_ic2",                    "nuggetLead",           "ic2/",             "ic2.ingot.lead"                        );
        registerNugget(10, "tin_nugget_forestry",               "nuggetTin",            "for/",             "item.for.ingot_tin"                    );
        registerNugget(11, "bronze_nugget_forestry",            "nuggetBronze",         "for/",             "item.for.ingot_bronze"                 );
        registerNugget(12, "copper_nugget_forestry",            "nuggetCopper",         "for/",             "item.for.ingot_copper"                 );
        registerNugget(13, "silver_nugget_factorization",       "nuggetSilver",         "factorization/",   "item.factorization:silver_ingot"       );
        registerNugget(14, "lead_nugget_factorization",         "nuggetLead",           "factorization/",   "item.factorization:lead_ingot"         );
        registerNugget(15, "darkiron_nugget_factorization",     "nuggetFzDarkIron",     "factorization/",   "item.factorization:dark_iron_ingot"    );
        registerNugget(16, "psimetal_nugget_psi",               "nuggetPsiMetal",       "psi/",             "item.psi:psimetal"                     );
        registerNugget(17, "psigem_nugget_psi",                 "nuggetPsiGem",         "psi/",             "item.psi:psigem"                       );
        registerNugget(18, "ebonypsimetal_nugget_psi",          "nuggetEbonyPsiMetal",  "psi/",             "item.psi:ebony_psimetal"               );
        registerNugget(19, "ivorypsimetal_nugget_psi",          "nuggetIvoryPsiMetal",  "psi/",             "item.psi:ivory_psimetal"               );
        registerNugget(20, "yellorium_nugget_bigreactors",      "nuggetYellorium",      "bigreactors/",     "item.bigreactors:ingotmetals.yellorium");
        registerNugget(21, "cyanite_nugget_bigreactors",        "nuggetCyanite",        "bigreactors/",     "item.bigreactors:ingotmetals.cyanite"  );
        registerNugget(22, "graphite_nugget_bigreactors",       "nuggetGraphite",       "bigreactors/",     "item.bigreactors:ingotmetals.graphite" );
        registerNugget(23, "blutonium_nugget_bigreactors",      "nuggetBlutonium",      "bigreactors/",     "item.bigreactors:ingotmetals.blutonium");
        registerNugget(24, "ludicrite_nugget_bigreactors",      "nuggetLudicrite",      "bigreactors/",     "item.bigreactors:ingotmetals.ludicrite");
        registerNugget(25, "steel_nugget_bigreactors",          "nuggetSteel",          "bigreactors/",     "item.bigreactors:ingotmetals.steel"    );
        registerNugget(26, "electricalsteel_nugget_enderio",    "nuggetElectricalSteel","enderio/",         "enderio.electricalSteel"               );
        registerNugget(27, "energeticalloy_nugget_enderio",     "nuggetEnergeticAlloy", "enderio/",         "enderio.energeticAlloy"                );
        registerNugget(28, "redstonealloy_nugget_enderio",      "nuggetRedstoneAlloy",  "enderio/",         "enderio.redstoneAlloy"                 );
        registerNugget(29, "conductiveiron_nugget_enderio",     "nuggetConductiveIron", "enderio/",         "enderio.conductiveIron"                );
        registerNugget(30, "darksteel_nugget_enderio",          "nuggetDarkSteel",      "enderio/",         "enderio.darkSteel"                     );
        registerNugget(31, "soularium_nugget_enderio",          "nuggetSoularium",      "enderio/",         "enderio.soularium"                     );
    }

    public static void registerNugget(int id, String unlocName, String oreName, String modId, String ingotName, boolean needed) {
        nuggets.put(ingotName, new NuggetInfo(id, unlocName, oreName, modId, ingotName, needed));
        idMap.put(id, ingotName);
    }

    public static void registerNugget(int id, String unlocName, String oreName, String modId, String ingotName) {
        registerNugget(id, unlocName, oreName, modId, ingotName, false);
    }

    public static HashMap<String, NuggetInfo> getNuggets() {
        return nuggets;
    }

    public static String getUnlocName(int i) {
        return (idMap.get(i) != null) && nuggets.get(idMap.get(i)) != null ? nuggets.get(idMap.get(i)).unlocName: "error_" + i;
    }

    public static NuggetInfo getNuggetInfo(String ingotName) {
        return nuggets.get(ingotName);
    }

    public static class NuggetInfo implements Comparable<NuggetInfo> {

        public String modId;
        public String unlocName;
        public String oreName;
        public String ingotName;
        public boolean needed;
        public int id;

        public NuggetInfo(int id, String unlocName, String oreName, String modId, String ingotName, boolean needed) {
            this.id = id;
            this.unlocName = unlocName;
            this.oreName = oreName;
            this.modId = modId;
            this.ingotName = ingotName;
            this.needed = needed;
        }


        @Override
        public int compareTo(@Nonnull NuggetInfo o) {
            return Integer.compare(id, o.id);
        }
    }

}

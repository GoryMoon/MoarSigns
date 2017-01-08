package gory_moon.moarsigns.items;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class NuggetRegistry {

    private static HashMap<String, NuggetInfo> nuggets = Maps.newHashMap();
    private static HashMap<Integer, String> idMap = Maps.newHashMap();

    private static int i = 0;

    public static void init() {
        registerNugget("diamond_nugget",                    "nuggetDiamond",        "",                 "item.diamond",             true);
        registerNugget("iron_nugget",                       "nuggetIron",           "",                 "item.ingotIron",           true);
        registerNugget("emerald_nugget",                    "nuggetEmerald",        "",                 "item.emerald",             true);
        registerNugget("lapis_nugget",                      "nuggetLapis",          "",                 "item.dyePowder.blue",      true);
        registerNugget("quartz_nugget",                     "nuggetQuartz",         "",                 "item.netherquartz",        true);
        registerNugget("bronze_nugget_ic2",                 "nuggetBronze",         "ic2/",             "ic2.ingot.bronze"                      );
        registerNugget("copper_nugget_ic2",                 "nuggetCopper",         "ic2/",             "ic2.ingot.copper"                      );
        registerNugget("tin_nugget_ic2",                    "nuggetTin",            "ic2/",             "ic2.ingot.tin"                         );
        registerNugget("steel_nugget_ic2",                  "nuggetSteel",          "ic2/",             "ic2.ingot.steel"                       );
        registerNugget("lead_nugget_ic2",                   "nuggetLead",           "ic2/",             "ic2.ingot.lead"                        );
        registerNugget("tin_nugget_forestry",               "nuggetTin",            "for/",             "item.for.ingotTin"                     );
        registerNugget("bronze_nugget_forestry",            "nuggetBronze",         "for/",             "item.for.ingotBronze"                  );
        registerNugget("copper_nugget_forestry",            "nuggetCopper",         "for/",             "item.for.ingotCopper"                  );
        registerNugget("silver_nugget_factorization",       "nuggetSilver",         "factorization/",   "item.factorization:silver_ingot"       );
        registerNugget("lead_nugget_factorization",         "nuggetLead",           "factorization/",   "item.factorization:lead_ingot"         );
        registerNugget("darkiron_nugget_factorization",     "nuggetFzDarkIron",     "factorization/",   "item.factorization:dark_iron_ingot"    );
        registerNugget("aluminium_nugget_techreborn",       "nuggetAluminium",      "techreborn/",      "item.techreborn.ingot.aluminum"        );
        registerNugget("brass_nugget_techreborn",           "nuggetBrass",          "techreborn/",      "item.techreborn.ingot.brass"           );
        registerNugget("chrome_nugget_techreborn",          "nuggetChrome",         "techreborn/",      "item.techreborn.ingot.chrome"          );
        registerNugget("copper_nugget_techreborn",          "nuggetCopper",         "techreborn/",      "item.techreborn.ingot.copper"          );
        registerNugget("electrum_nugget_techreborn",        "nuggetElectrum",       "techreborn/",      "item.techreborn.ingot.electrum"        );
        registerNugget("invar_nugget_techreborn",           "nuggetInvar",          "techreborn/",      "item.techreborn.ingot.invar"           );
        registerNugget("iridium_nugget_techreborn",         "nuggetIridium",        "techreborn/",      "item.techreborn.ingot.iridium"         );
        registerNugget("lead_nugget_techreborn",            "nuggetLead",           "techreborn/",      "item.techreborn.ingot.lead"            );
        registerNugget("nickel_nugget_techreborn",          "nuggetNickel",         "techreborn/",      "item.techreborn.ingot.nickel"          );
        registerNugget("platinum_nugget_techreborn",        "nuggetPlatinum",       "techreborn/",      "item.techreborn.ingot.platinum"        );
        registerNugget("silver_nugget_techreborn",          "nuggetSilver",         "techreborn/",      "item.techreborn.ingot.silver"          );
        registerNugget("steel_nugget_techreborn",           "nuggetSteel",          "techreborn/",      "item.techreborn.ingot.steel"           );
        registerNugget("tin_nugget_techreborn",             "nuggetTin",            "techreborn/",      "item.techreborn.ingot.tin"             );
        registerNugget("titanium_nugget_techreborn",        "nuggetTitanium",       "techreborn/",      "item.techreborn.ingot.titanium"        );
        registerNugget("tungsten_nugget_techreborn",        "nuggetTungsten",       "techreborn/",      "item.techreborn.ingot.tungsten"        );
        registerNugget("tungstensteel_nugget_techreborn",   "nuggetTungstenSteel",  "techreborn/",      "item.techreborn.ingot.tungstensteel"   );
        registerNugget("zinc_nugget_techreborn",            "nuggetZinc",           "techreborn/",      "item.techreborn.ingot.zinc"            );
        registerNugget("refinediron_nugget_techreborn",     "nuggetRefinedIron",    "techreborn/",      "item.techreborn.ingot.refinedIron"     );
        registerNugget("psimetal_nugget_psi",               "nuggetPsiMetal",       "psi/",             "item.psi:psimetal"                     );
        registerNugget("psigem_nugget_psi",                 "nuggetPsiGem",         "psi/",             "item.psi:psigem"                       );
        registerNugget("ebonypsimetal_nugget_psi",          "nuggetEbonyPsiMetal",  "psi/",             "item.psi:ebonyPsimetal"                );
        registerNugget("ivorypsimetal_nugget_psi",          "nuggetIvoryPsiMetal",  "psi/",             "item.psi:ivoryPsimetal"                );
        registerNugget("yellorium_nugget_bigreactors",      "nuggetYellorium",      "bigreactors/",     "item.bigreactors:ingotMetals.yellorium");
        registerNugget("cyanite_nugget_bigreactors",        "nuggetCyanite",        "bigreactors/",     "item.bigreactors:ingotMetals.cyanite"  );
        registerNugget("graphite_nugget_bigreactors",       "nuggetGraphite",       "bigreactors/",     "item.bigreactors:ingotMetals.graphite" );
        registerNugget("blutonium_nugget_bigreactors",      "nuggetBlutonium",      "bigreactors/",     "item.bigreactors:ingotMetals.blutonium");
        registerNugget("ludicrite_nugget_bigreactors",      "nuggetLudicrite",      "bigreactors/",     "item.bigreactors:ingotMetals.ludicrite");
        registerNugget("steel_nugget_bigreactors",          "nuggetSteel",          "bigreactors/",     "item.bigreactors:ingotMetals.steel"    );
        registerNugget("electricalsteel_nugget_enderio",    "nuggetElectricalSteel","enderio/",         "enderio.electricalSteel"               );
        registerNugget("energeticalloy_nugget_enderio",     "nuggetEnergeticAlloy", "enderio/",         "enderio.energeticAlloy"                );
        registerNugget("redstonealloy_nugget_enderio",      "nuggetRedstoneAlloy",  "enderio/",         "enderio.redstoneAlloy"                 );
        registerNugget("conductiveiron_nugget_enderio",     "nuggetConductiveIron", "enderio/",         "enderio.conductiveIron"                );
        registerNugget("darksteel_nugget_enderio",          "nuggetDarkSteel",      "enderio/",         "enderio.darkSteel"                     );
        registerNugget("soularium_nugget_enderio",          "nuggetSoularium",      "enderio/",         "enderio.soularium"                     );
    }

    public static void registerNugget(String unlocName, String oreName, String modId, String ingotName, boolean needed) {
        nuggets.put(ingotName, new NuggetInfo(i, unlocName, oreName, modId, ingotName, needed));
        idMap.put(i++, ingotName);
    }

    public static void registerNugget(String unlocName, String oreName, String modId, String ingotName) {
        registerNugget(unlocName, oreName, modId, ingotName, false);
    }

    public static HashMap<String, NuggetInfo> getNuggets() {
        return nuggets;
    }

    public static String getUnlocName(int i) {
        return nuggets.get(idMap.get(i)).unlocName;
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
            return id < o.id ? -1: id > o.id ? 1: 0;
        }
    }

}

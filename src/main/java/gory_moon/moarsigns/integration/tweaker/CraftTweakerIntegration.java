package gory_moon.moarsigns.integration.tweaker;

import crafttweaker.CraftTweakerAPI;
import gory_moon.moarsigns.MoarSigns;

public class CraftTweakerIntegration {

    public static void register() {
        CraftTweakerAPI.registerClass(Signs.class);
        CraftTweakerAPI.registerBracketHandler(new MatchTypeBracket());
        CraftTweakerAPI.registerBracketHandler(new MaterialBracket());
        MoarSigns.logger.info("Loaded CraftTweaker 3 Integration");
    }
}

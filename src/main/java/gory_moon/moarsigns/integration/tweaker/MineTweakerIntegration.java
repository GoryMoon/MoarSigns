package gory_moon.moarsigns.integration.tweaker;

import gory_moon.moarsigns.MoarSigns;
import minetweaker.MineTweakerAPI;

public class MineTweakerIntegration {

    public static void register() {
        MineTweakerAPI.registerClass(Signs.class);
        MineTweakerAPI.registerBracketHandler(new MatchTypeBracket());
        MineTweakerAPI.registerBracketHandler(new MaterialBracket());
        MoarSigns.logger.info("Loaded CraftTweaker 3 Integration");
    }
}

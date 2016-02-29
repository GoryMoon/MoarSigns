package gory_moon.moarsigns;

import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.lib.ModInfo;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Config extends Configuration {

    public static final String CATEGORY_GENERAL = "general";
    public static final String REPLACE_KEY = "replaceRecipes";

    public Config(File file) {
        super(file);
    }

    public Config loadConfig() {
        load();
        syncConfigs();
        return this;
    }

/*
    public void saveConfig() {
        ConfigCategory cat = getCategory(CATEGORY_GENERAL);
        Property prop = cat.get(REPLACE_KEY);
        prop.setValue(ModItems.replaceRecipes);
        cat.put(REPLACE_KEY, prop);
        if (hasChanged())
            save();
    }
*/

    private void syncConfigs() {
        ModItems.replaceRecipes = get(CATEGORY_GENERAL, REPLACE_KEY, true, "Replaces the vanilla sign in recipes with signs from MoarSigns").getBoolean();

        if (hasChanged())
            save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(ModInfo.ID))
            syncConfigs();
    }

}

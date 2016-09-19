package gory_moon.moarsigns;

import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.lib.ModInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static gory_moon.moarsigns.lib.Constants.REPLACE_KEY;
import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class ConfigHandler {

    /* SINGLETON */
    private static ConfigHandler _instance = null;
    private ConfigHandler() {
        _instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static ConfigHandler instance(){	return _instance == null ? new ConfigHandler() : _instance;	}
	/* === */

    public  Configuration config = null;

    public void loadDefaultConfig(FMLPreInitializationEvent event){
        config = new Configuration(event.getSuggestedConfigurationFile());

        syncConfigs();
    }

    public void saveConfig() {
        ConfigCategory cat = config.getCategory(CATEGORY_GENERAL);

        Property prop = cat.get(REPLACE_KEY);
        prop.setValue(ModItems.replaceRecipes);
        cat.put(REPLACE_KEY, prop);

        if (config.hasChanged())
            config.save();
    }

    private void syncConfigs() {
        ModItems.replaceRecipes = config.get(CATEGORY_GENERAL, REPLACE_KEY, true, "Replaces the vanilla sign in recipes with signs from MoarSigns").getBoolean();

        if (config.hasChanged())
            config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ModInfo.ID))
            syncConfigs();
    }

}

package gory_moon.moarsigns;

import gory_moon.moarsigns.items.ModItems;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static final String CATEGORY_GENERAL = "general";

    public ConfigHandler(File file) {

        Configuration config = new Configuration(file);

        config.load();

        ModItems.replaceRecipes = config.get(CATEGORY_GENERAL, "replaceRecipes", true, "Replaces the vanilla sign in recipes with signs from MoarSigns").getBoolean();

        config.save();


    }
}

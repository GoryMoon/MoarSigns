package gory_moon.moarsigns.client.interfaces.config;

import gory_moon.moarsigns.ConfigHandler;
import gory_moon.moarsigns.lib.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModGuiCoinfig extends GuiConfig {

    public ModGuiCoinfig(GuiScreen guiScreen) {
        super(guiScreen, new ConfigElement(ConfigHandler.instance().config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MODID, true, true, GuiConfig.getAbridgedConfigPath(ConfigHandler.instance().config.toString()));
    }
}

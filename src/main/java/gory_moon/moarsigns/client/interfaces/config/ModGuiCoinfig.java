package gory_moon.moarsigns.client.interfaces.config;

import gory_moon.moarsigns.Config;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.lib.ModInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ModGuiCoinfig extends GuiConfig {

    public ModGuiCoinfig(GuiScreen guiScreen) {
        super(guiScreen,
                new ConfigElement(MoarSigns.instance.config.getCategory(Config.CATEGORY_GENERAL)).getChildElements(),
                ModInfo.ID, true, true, GuiConfig.getAbridgedConfigPath(MoarSigns.instance.config.toString()));
    }
}

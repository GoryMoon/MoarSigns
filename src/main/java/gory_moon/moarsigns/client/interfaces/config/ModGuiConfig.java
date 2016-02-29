package gory_moon.moarsigns.client.interfaces.config;

import cpw.mods.fml.client.config.GuiConfig;
import gory_moon.moarsigns.Config;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.lib.ModInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class ModGuiConfig extends GuiConfig {

    public ModGuiConfig(GuiScreen guiScreen) {
        super(guiScreen,
                new ConfigElement(MoarSigns.instance.config.getCategory(Config.CATEGORY_GENERAL)).getChildElements(),
                ModInfo.ID, true, true, GuiConfig.getAbridgedConfigPath(MoarSigns.instance.config.toString()));
    }

}

package gory_moon.moarsigns;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.integration.tweaker.MineTweakerIntegration;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.proxy.CommonProxy;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, certificateFingerprint = "@FINGERPRINT@", dependencies = "before:MineTweaker")
public class MoarSigns {

    private static final String LINK = "https://raw.githubusercontent.com/GoryMoon/MoarSigns/master/version.json";

    @Instance(ModInfo.ID)
    public static MoarSigns instance;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger("MoarSigns");
    public static HashMap<String, IIcon> icons = new HashMap<String, IIcon>();
    private static HashMap<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        new ConfigHandler(event.getSuggestedConfigurationFile());
        FMLInterModComms.sendRuntimeMessage(ModInfo.ID, "VersionChecker", "addVersionCheck", LINK);

        PacketHandler.init();

        Blocks.init();
        ModItems.init();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void load(FMLInitializationEvent event) {
        proxy.initRenderers();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void modsLoaded(FMLPostInitializationEvent event) {

        new IntegrationHandler().setupSigns();
        ModItems.registerRecipes();

        if (Loader.isModLoaded("MineTweaker3")) {
            MineTweakerIntegration.register();
        }
    }

    public ResourceLocation getResourceLocation(String s, boolean isMetal) {
        ResourceLocation location = textures.get(s);

        if (location == null) {
            SignInfo info = SignRegistry.get(s);

            if (info == null) return null;

            location = new ResourceLocation(info.modId.toLowerCase(), "textures/signs/" + (isMetal ? "metal/" : "wood/") + s + ".png");
            textures.put(s, location);
        }

        return location;
    }
}

package gory_moon.moarsigns;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.integration.tweaker.MineTweakerIntegration;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.items.NuggetRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.proxy.CommonProxy;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, certificateFingerprint = ModInfo.FINGERPRINT, guiFactory = ModInfo.GUI_FACTORY_CLASS,
        dependencies =
                "after:BiomesOPlenty;" +
                        "after:Forestry;" +
                        "after:Nature;" +
                        "after:IC2;" +
                        "after:TConstruct;" +
                        "after:Railcraft;" +
                        "after:ThermalFoundation;" +
                        "after:factorization;" +
                        "after:NotEnoughItems;" +
                        "after:Waila;" +
                        "after:MineTweaker3;")
public class MoarSigns {

    private static final String LINK = "https://raw.githubusercontent.com/GoryMoon/MoarSigns/master/version.json";

    @Instance(ModInfo.ID)
    public static MoarSigns instance;
    public Config config;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger("MoarSigns");
    public static HashMap<String, IIcon> icons = new HashMap<String, IIcon>();
    private static HashMap<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        config =  new Config(event.getSuggestedConfigurationFile()).loadConfig();
        FMLCommonHandler.instance().bus().register(config);
        FMLInterModComms.sendRuntimeMessage(ModInfo.ID, "VersionChecker", "addVersionCheck", LINK);

        PacketHandler.init();

        NuggetRegistry.init();
        Blocks.init();
        ModItems.init();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void load(FMLInitializationEvent event) {
        proxy.initRenderers();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        if (FMLInterModComms.sendMessage("Waila", "register", "gory_moon.moarsigns.integration.waila.Provider.callbackRegister")) {
            MoarSigns.logger.info("Loaded Waila Integration");
        }
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

    @EventHandler
    public void invalidFingerprint(FMLFingerprintViolationEvent event) {
        if (ModInfo.FINGERPRINT.equals("@FINGERPRINT@")) {
            logger.info("The copy of MoarSigns that you are running is a development version of the mod, and as such may be unstable and/or incomplete.");
        } else {
            logger.warn("The copy of MoarSigns that you are running has been modified from the original, and unpredictable things may happen. Please consider re-downloading the original version of the mod.");
        }
    }

    public ResourceLocation getResourceLocation(String s, boolean isMetal) {
        ResourceLocation location = textures.get(isMetal + s);

        if (location == null) {
            SignInfo info = SignRegistry.get(s);

            if (info == null) return null;

            location = new ResourceLocation(info.modId.toLowerCase(), "textures/signs/" + (isMetal ? "metal/" : "wood/") + s + ".png");
            textures.put(s, location);
        }

        return location;
    }
}

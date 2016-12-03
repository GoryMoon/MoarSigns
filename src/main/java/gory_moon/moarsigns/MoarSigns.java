package gory_moon.moarsigns;

import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.items.NuggetRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.proxy.CommonProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, certificateFingerprint = ModInfo.FINGERPRINT, acceptedMinecraftVersions = "[1.10]", guiFactory = ModInfo.GUI_FACTORY_CLASS, updateJSON = MoarSigns.FORGE_PROMO,
        dependencies =
                "after:BiomesOPlenty;" +
                "after:Forestry;" +
                "after:Nature;" +
                "after:IC2;" +
                "after:TConstruct;" +
                "after:Railcraft;" +
                "after:ThermalFoundation;" +
                "after:factorization;" +
                "after:basemetals;" +
                "after:techreborn;" +
                "after:NotEnoughItems;" +
                "after:Waila;" +
                "after:MineTweaker3;")
public class MoarSigns {

    private static final String WAILA_PROVIDER = "gory_moon.moarsigns.integration.waila.Provider.callbackRegister";
    private static final String LINK = "https://raw.githubusercontent.com/GoryMoon/MoarSigns/master/version.json";
    static final String FORGE_PROMO = "https://raw.githubusercontent.com/GoryMoon/MoarSigns/master/version_promo.json";

    @Instance(ModInfo.ID)
    public static MoarSigns instance;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger("MoarSigns");

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.instance().loadDefaultConfig(event);
        PacketHandler.init();

        FMLInterModComms.sendMessage("VersionChecker", "addVersionCheck", LINK);
        if (FMLInterModComms.sendMessage("Waila", "register", WAILA_PROVIDER)) {
            MoarSigns.logger.info("Loaded Waila Integration");
        }

        proxy.preInit();
        NuggetRegistry.init();
        Blocks.init();
        ModItems.init();

        proxy.registerModels();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void load(FMLInitializationEvent event) {
        proxy.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void modsLoaded(FMLPostInitializationEvent event) {
        new IntegrationHandler().setupSigns();
        ModItems.registerRecipes();

        if (Loader.isModLoaded("Quark")) {
            MoarSigns.logger.warn("Quark is loaded, MoarSigns sign editing might not work as intended");
        }

        /*if (Loader.isModLoaded("MineTweaker3")) {
            MineTweakerIntegration.register();
        }*/
    }

}

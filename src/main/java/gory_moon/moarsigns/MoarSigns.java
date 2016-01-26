package gory_moon.moarsigns;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.items.NuggetRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;
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

import java.util.HashMap;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, certificateFingerprint = ModInfo.FINGERPRINT,
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

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger("MoarSigns");
    private static HashMap<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        new ConfigHandler(event.getSuggestedConfigurationFile());
        FMLInterModComms.sendRuntimeMessage(ModInfo.ID, "VersionChecker", "addVersionCheck", LINK);

        PacketHandler.init();

        proxy.init();
        NuggetRegistry.init();
        Blocks.init();
        ModItems.init();

        proxy.postInit();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void load(FMLInitializationEvent event) {

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

        //TODO MineTweaker3
        /*if (Loader.isModLoaded("MineTweaker3")) {
            MineTweakerIntegration.register();
        }*/
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

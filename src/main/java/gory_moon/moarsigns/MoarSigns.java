package gory_moon.moarsigns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gory_moon.moarsigns.api.IntegrationRegistry;
import gory_moon.moarsigns.blocks.ModBlocks;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.integration.tweaker.MineTweakerIntegration;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.proxy.CommonProxy;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, certificateFingerprint = Reference.FINGERPRINT, acceptedMinecraftVersions = "[1.10]", guiFactory = Reference.GUI_FACTORY_CLASS, updateJSON = MoarSigns.FORGE_PROMO, dependencies = "after:BiomesOPlenty;" + "after:Forestry;" + "after:Nature;" + "after:IC2;" + "after:TConstruct;" + "after:Railcraft;" + "after:ThermalFoundation;" + "after:factorization;" + "after:basemetals;" + "after:techreborn;" + "after:psi;" + "after:NotEnoughItems;" + "after:Waila;" + "after:MineTweaker3;")
public class MoarSigns {

    private static final String WAILA_PROVIDER = "gory_moon.moarsigns.integration.waila.Provider.callbackRegister";
    private static final String LINK = "https://raw.githubusercontent.com/GoryMoon/MoarSigns/master/version.json";
    static final String FORGE_PROMO = "https://raw.githubusercontent.com/GoryMoon/MoarSigns/master/version_promo.json";

    @Instance(Reference.MODID)
    public static MoarSigns instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
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
        ModBlocks.registerTileEntities();
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

        JsonArray array = new JsonArray();

        List<ItemStack> list = OreDictionary.getOres("plankWood");
        for (ItemStack stack: list) {
            JsonObject obj = new JsonObject();
            obj.addProperty(stack.getItem().getRegistryName().getResourceDomain(), stack.getUnlocalizedName());
            array.add(obj);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(array);

        Path path = Paths.get(Loader.instance().getConfigDir().getAbsolutePath(), "/planks.json");
        /*try {
            Files.write(path, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        array = new JsonArray();

        list = IntegrationHandler.getOres(IntegrationRegistry.getMetalNames());
        for (ItemStack stack: list) {
            JsonObject obj = new JsonObject();
            obj.addProperty(stack.getItem().getRegistryName().getResourceDomain(), stack.getUnlocalizedName());
            array.add(obj);
        }

        s = gson.toJson(array);

        path = Paths.get(Loader.instance().getConfigDir().getAbsolutePath(), "/ingots.json");
        try {
            Files.write(path, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Loader.isModLoaded("MineTweaker3")) {
            MineTweakerIntegration.register();
        }
    }

}

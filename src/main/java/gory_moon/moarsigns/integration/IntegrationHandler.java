package gory_moon.moarsigns.integration;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.integration.basemetals.BasemetalsIntegration;
import gory_moon.moarsigns.integration.bigrectors.BigReactorsIntegration;
import gory_moon.moarsigns.integration.bop.BiomesOPlentyIntegration;
import gory_moon.moarsigns.integration.draconicevolution.DraconicEvolutionIntegration;
import gory_moon.moarsigns.integration.enderio.EnderIOIntegration;
import gory_moon.moarsigns.integration.forestry.ForestryIntegration;
import gory_moon.moarsigns.integration.ic2.IndustrialCraft2Integration;
import gory_moon.moarsigns.integration.id.IntegratedDynamicsIntegration;
import gory_moon.moarsigns.integration.ie.ImmersiveEngineeringIntegration;
import gory_moon.moarsigns.integration.natura.NaturaIntegration;
import gory_moon.moarsigns.integration.psi.PsiIntegration;
import gory_moon.moarsigns.integration.railcraft.RailcraftIntegration;
import gory_moon.moarsigns.integration.randomthings.RandomThingsIntegration;
import gory_moon.moarsigns.integration.tconstruct.TinkersConstructIntegration;
import gory_moon.moarsigns.integration.techreborn.TechRebornIntegration;
import gory_moon.moarsigns.integration.thermalfoundation.ThermalFoundationIntegration;
import gory_moon.moarsigns.integration.vanilla.MinecraftIntegration;
import gory_moon.moarsigns.util.IntegrationException;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static gory_moon.moarsigns.api.IntegrationRegistry.*;

public class IntegrationHandler {

    private static boolean preSetup = true;

    static {
        registerIntegration(MinecraftIntegration.class);
        registerIntegration(ForestryIntegration.class);
        registerIntegration(BiomesOPlentyIntegration.class);
        registerIntegration(IndustrialCraft2Integration.class);
        registerIntegration(TinkersConstructIntegration.class);
        registerIntegration(BasemetalsIntegration.class);
        registerIntegration(TechRebornIntegration.class);
        registerIntegration(NaturaIntegration.class);
        //registerIntegration(RailcraftIntegration.class);
        registerIntegration(PsiIntegration.class);
        registerIntegration(BigReactorsIntegration.class);
        registerIntegration(ImmersiveEngineeringIntegration.class);
        registerIntegration(IntegratedDynamicsIntegration.class);
        registerIntegration(DraconicEvolutionIntegration.class);
        //registerIntegration(EnderIOIntegration.class);
        registerIntegration(RandomThingsIntegration.class);
        registerIntegration(ThermalFoundationIntegration.class);

        /* Not updated mods */
        //registerIntegration(FactorizationIntegration.class);


        registerPlankOreName("plankWood");
        registerPlankOreName("plankTreatedWood");

        registerMetalGemOreName("ingotAdamantine",          "blockAdamantine");
        registerMetalGemOreName("ingotAluminum",            "blockAluminum");
        registerMetalGemOreName("ingotAluminumBrass",       "blockAluminumBrass");
        registerMetalGemOreName("ingotAlumite",             "blockAlumite");
        registerMetalGemOreName("ingotArdite",              "blockArdite");
        registerMetalGemOreName("ingotAquarium",            "blockAquarium");
        registerMetalGemOreName("ingotBrass",               "blockBrass");
        registerMetalGemOreName("ingotBronze",              "blockBronze");
        registerMetalGemOreName("ingotCobalt",              "blockCobalt");
        registerMetalGemOreName("ingotColdiron",            "blockColdiron");
        registerMetalGemOreName("ingotCopper",              "blockCopper");
        registerMetalGemOreName("ingotCupronickel",         "blockCupronickel");
        registerMetalGemOreName("ingotElectrum",            "blockElectrum");
        registerMetalGemOreName("ingotEnderium",            "blockEnderium");
        registerMetalGemOreName("ingotFzDarkIron",          "blockFzDarkIron");
        registerMetalGemOreName("ingotInvar",               "blockInvar");
        registerMetalGemOreName("ingotLead",                "blockLead");
        registerMetalGemOreName("ingotLumium",              "blockLumium");
        registerMetalGemOreName("ingotManyullyn",           "blockManyullyn");
        registerMetalGemOreName("ingotMithril",             "blockMithril");
        registerMetalGemOreName("ingotNickel",              "blockNickel");
        registerMetalGemOreName("ingotPlatinum",            "blockPlatinum");
        registerMetalGemOreName("ingotSignalum",            "blockSignalum");
        registerMetalGemOreName("ingotSilver",              "blockSilver");
        registerMetalGemOreName("ingotStarsteel",           "blockStarsteel");
        registerMetalGemOreName("ingotSteel",               "blockSteel");
        registerMetalGemOreName("ingotTin",                 "blockTin");
        registerMetalGemOreName("ingotZinc",                "blockZinc");
        registerMetalGemOreName("ingotPsi",                 "blockPsiMetal");
        registerMetalGemOreName("gemPsi",                   "blockPsiGem");
        registerMetalGemOreName("ingotYellorium",           "blockYellorium");
        registerMetalGemOreName("ingotUranium",             "blockUranium");
        registerMetalGemOreName("ingotCyanite",             "blockCyanite");
        registerMetalGemOreName("ingotGraphite",            "blockGraphite");
        registerMetalGemOreName("ingotBlutonium",           "blockBlutonium");
        registerMetalGemOreName("ingotPlutonium",           "blockPlutonium");
        registerMetalGemOreName("ingotLudicrite",           "blockLudicrite");
        registerMetalGemOreName("ingotConstantan",          "blockConstantan");
        registerMetalGemOreName("ingotDraconium",           "blockDraconium");
        registerMetalGemOreName("ingotDraconiumAwakened",   "blockDraconiumAwakened");
        registerMetalGemOreName("ingotElectricalSteel",     "blockElectricalSteel");
        registerMetalGemOreName("ingotEnergeticAlloy",      "blockEnergeticAlloy");
        registerMetalGemOreName("ingotVibrantAlloy",        "blockVibrantAlloy");
        registerMetalGemOreName("ingotRedstoneAlloy",       "blockRedstoneAlloy");
        registerMetalGemOreName("ingotConductiveIron",      "blockConductiveIron");
        registerMetalGemOreName("ingotPulsatingIron",       "blockPulsatingIron");
        registerMetalGemOreName("ingotDarkSteel",           "blockDarkSteel");
        registerMetalGemOreName("ingotSoularium",           "blockSoularium");
    }

    private static void registerSigns(ArrayList<ItemStack> planks, ArrayList<ItemStack> ingots, boolean log) {
        if (log)
            MoarSigns.logger.info("Starting sign integrations");

        ArrayList<ISignRegistration> signReg = getSignReg();

        for (ISignRegistration reg : signReg) {
            try {
                reg.registerWoodenSigns(planks);
                reg.registerMetalSigns(ingots);
            } catch (IntegrationException e) {
                if (Loader.isModLoaded(reg.getActivateTag())&& log)
                    MoarSigns.logger.error("Failed " + reg.getIntegrationName() + " SignIntegration" + e.getMessage());
                continue;
            }
            if (Loader.isModLoaded(reg.getActivateTag())) {
                SignRegistry.activateTag(reg.getActivateTag());
                if (log)
                    MoarSigns.logger.info("Loaded " + reg.getIntegrationName() + " SignIntegration");
            }
        }

        if (log)
            MoarSigns.logger.info("Finished " + (SignRegistry.getActiveTagsAmount()) + " sign integrations with " + SignRegistry.getActivatedSignRegistry().size() + " signs registered");

        SignRegistry.sortRegistry();
    }

    public static ArrayList<ItemStack> getOres(ArrayList<String> names) {
        ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
        for (String name : names)
            ores.addAll(OreDictionary.getOres(name));
        return ores;
    }

    public void preSetupSigns() {

        ArrayList<String> names = getWoodNames();
        ArrayList<ItemStack> planks = getOres(names);

        names = getMetalNames();
        ArrayList<ItemStack> ingots = getOres(names);

        registerSigns(planks, ingots, false);
        preSetup = false;
    }

    public void setupSigns() {

        ArrayList<String> names = getWoodNames();
        ArrayList<ItemStack> planks = getOres(names);

        names = getMetalNames();
        ArrayList<ItemStack> ingots = getOres(names);

        registerSigns(planks, ingots, true);
    }

    public static boolean donePreSetup() {
        return !preSetup;
    }

    /*IntegrationHandler.genModelFile("metal", "enderio", "electricalsteel_sign");
        IntegrationHandler.genModelFile("metal", "enderio", "energeticalloy_sign");
        IntegrationHandler.genModelFile("metal", "enderio", "vibrantalloy_sign");
        IntegrationHandler.genModelFile("metal", "enderio", "redstonealloy_sign");
        IntegrationHandler.genModelFile("metal", "enderio", "conductiveiron_sign");
        IntegrationHandler.genModelFile("metal", "enderio", "pulsatingiron_sign");
        IntegrationHandler.genModelFile("metal", "enderio", "darksteel_sign");
        IntegrationHandler.genModelFile("metal", "enderio", "soularium_sign");

        IntegrationHandler.genNuggetFile("enderio", "electricalsteel_nugget_enderio");
        IntegrationHandler.genNuggetFile("enderio", "energeticalloy_nugget_enderio");
        IntegrationHandler.genNuggetFile("enderio", "redstonealloy_nugget_enderio");
        IntegrationHandler.genNuggetFile("enderio", "conductiveiron_nugget_enderio");
        IntegrationHandler.genNuggetFile("enderio", "darksteel_nugget_enderio");
        IntegrationHandler.genNuggetFile("enderio", "soularium_nugget_enderio");*/

    public static void genModelFile(String type, String mod, String signName) {
        String base = "../src/main/resources/assets/moarsigns/models/ingot/";
        try {
            Path path1 = Paths.get(base + "mod_template_sign.json");
            String template = new String(Files.readAllBytes(path1), StandardCharsets.UTF_8);
            String s = template.replaceAll("TYPE", type);
            s = s.replaceAll("MOD", mod);
            s = s.replaceAll("SIGN", signName);
            Path path2 = Paths.get(base + "signs/" + mod + "/" + signName + ".json");
            Files.write(path2, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genNuggetFile(String mod, String nuggetName) {
        String base = "../src/main/resources/assets/moarsigns/models/ingot/";
        try {
            Path path1 = Paths.get(base + "mod_template_nugget.json");
            String template = new String(Files.readAllBytes(path1), StandardCharsets.UTF_8);
            String s = template.replaceAll("MOD", mod);
            s = s.replaceAll("NUGGET", nuggetName);
            Path path2 = Paths.get(base + "nuggets/" + mod + "/" + nuggetName + ".json");
            Files.write(path2, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

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
import gory_moon.moarsigns.integration.randomthings.RandomThingsIntegration;
import gory_moon.moarsigns.integration.tconstruct.TinkersConstructIntegration;
import gory_moon.moarsigns.integration.techreborn.TechRebornIntegration;
import gory_moon.moarsigns.integration.thermalfoundation.ThermalFoundationIntegration;
import gory_moon.moarsigns.integration.vanilla.MinecraftIntegration;
import gory_moon.moarsigns.util.IntegrationException;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;

import static gory_moon.moarsigns.api.IntegrationRegistry.getSignReg;
import static gory_moon.moarsigns.api.IntegrationRegistry.registerIntegration;

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
        registerIntegration(PsiIntegration.class);
        registerIntegration(BigReactorsIntegration.class);
        registerIntegration(ImmersiveEngineeringIntegration.class);
        registerIntegration(IntegratedDynamicsIntegration.class);
        registerIntegration(DraconicEvolutionIntegration.class);
        registerIntegration(EnderIOIntegration.class);
        registerIntegration(RandomThingsIntegration.class);
        registerIntegration(ThermalFoundationIntegration.class);

        /* Not updated mods */
        //registerIntegration(FactorizationIntegration.class);
        //registerIntegration(RailcraftIntegration.class);
    }

    private static void registerSigns(boolean log) {
        if (log)
            MoarSigns.logger.info("Starting sign integrations");

        ArrayList<ISignRegistration> signReg = getSignReg();

        for (ISignRegistration reg : signReg) {
            try {
                reg.registerSigns();
            } catch (IntegrationException e) {
                if (Loader.isModLoaded(reg.getActivateTag()) && log)
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

    public void preSetupSigns() {
        registerSigns(false);
        preSetup = false;
    }

    public void setupSigns() {
        registerSigns(true);
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
        IntegrationHandler.genNuggetFile("enderio", "soularium_nugget_enderio");

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
    }*/

}

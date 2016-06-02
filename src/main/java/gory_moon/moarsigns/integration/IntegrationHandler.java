package gory_moon.moarsigns.integration;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.IntegrationRegistry;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.integration.basemetals.BasemetalsIntegration;
import gory_moon.moarsigns.integration.bop.BiomesOPlentyIntegration;
import gory_moon.moarsigns.integration.tconstruct.TinkersConstructIntegration;
import gory_moon.moarsigns.integration.vanilla.MinecraftIntegration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

import static gory_moon.moarsigns.api.IntegrationRegistry.*;

public class IntegrationHandler {

    static {
        registerIntegration(MinecraftIntegration.class);
        //registerIntegration(NaturaIntegration.class);
        //registerIntegration(ForestryIntegration.class);
        registerIntegration(BiomesOPlentyIntegration.class);
        //registerIntegration(IndustrialCraft2Integration.class);
        registerIntegration(TinkersConstructIntegration.class);
        registerIntegration(BasemetalsIntegration.class);
        //registerIntegration(FactorizationIntegration.class);
        //registerIntegration(RailcraftIntegration.class);
        //registerIntegration(ThermalFoundationIntegration.class);

        registerPlankOreName("plankWood");

        String[] ingotNames = {"ingotCopper", "ingotTin", "ingotSilver", "ingotBronze", "ingotSteel", "ingotLead", "ingotZinc", "ingotBrass", "ingotCobalt", "ingotArdite", "ingotManyullyn", "ingotAluminum", "ingotAluminumBrass", "ingotAlumite",
                "ingotNickel", "ingotPlatinum", "ingotMithril", "ingotElectrum", "ingotColdiron", "ingotCupronickel", "ingotMithril", "ingotAdamantine", "ingotStarsteel", "ingotAquarium", "ingotInvar", "ingotSignalum", "ingotLumium", "ingotEnderium", "ingotFzDarkIron"};
        String[] blockNames = {"blockCopper", "blockTin", "blockSilver", "blockBronze", "blockSteel", "blockLead", "blockZinc", "blockBrass", "blockCobalt", "blockArdite", "blockManyullyn", "blockAluminum", "blockAluminumBrass", "blockAlumite",
                "blockNickel", "blockPlatinum", "blockMithril", "blockElectrum", "blockColdiron", "blockCupronickel", "blockMithril", "blockAdamantine", "blockStarsteel", "blockAquarium", "blockInvar", "blockSignalum", "blockLumium", "blockEnderium", "blockFzDarkIron"};

        for (String name : ingotNames) registerMetalGemOreName(name);
        for (String name : blockNames) registerMetalGemOreName(name);
    }

    private static void registerSigns(ArrayList<ItemStack> planks, ArrayList<ItemStack> ingots, boolean log) {
        if (log) MoarSigns.logger.info("Starting sign integrations");

        ArrayList<ISignRegistration> signReg = IntegrationRegistry.getSignReg();

        for (ISignRegistration reg : signReg) {
            reg.registerWoodenSigns(planks);
            reg.registerMetalSigns(ingots);
        }

        for (ISignRegistration reg : signReg) {
            if (reg.getActivateTag() != null && reg.getIntegrationName() != null && Loader.isModLoaded(reg.getActivateTag())) {
                SignRegistry.activateTag(reg.getActivateTag());
                if (log) MoarSigns.logger.info("Loaded " + reg.getIntegrationName() + " SignIntegration");
            }
        }

        if (log) MoarSigns.logger.info("Finished " + (SignRegistry.getActiveTagsAmount()) + " sign integrations with " + SignRegistry.getActivatedSignRegistry().size() + " signs registered");

        SignRegistry.sortRegistry();
    }

    private ArrayList<ItemStack> getOres(ArrayList<String> names) {
        ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
        for (String name : names)
            ores.addAll(OreDictionary.getOres(name));
        return ores;
    }

    public void preSetupSigns() {

        ArrayList<String> names = IntegrationRegistry.getWoodNames();
        ArrayList<ItemStack> planks = getOres(names);

        names = IntegrationRegistry.getMetalNames();
        ArrayList<ItemStack> ingots = getOres(names);

        registerSigns(planks, ingots, false);
    }

    public void setupSigns() {

        ArrayList<String> names = IntegrationRegistry.getWoodNames();
        ArrayList<ItemStack> planks = getOres(names);

        names = IntegrationRegistry.getMetalNames();
        ArrayList<ItemStack> ingots = getOres(names);

        registerSigns(planks, ingots, true);
    }

}

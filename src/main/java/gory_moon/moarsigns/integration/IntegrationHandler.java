package gory_moon.moarsigns.integration;

import cpw.mods.fml.common.Loader;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.IntegrationRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.integration.bop.BiomesOPlentyIntegration;
import gory_moon.moarsigns.integration.factorization.FactorizationIntegration;
import gory_moon.moarsigns.integration.forestry.ForestryIntegration;
import gory_moon.moarsigns.integration.ic2.IndustrialCraft2Integration;
import gory_moon.moarsigns.integration.natura.NaturaIntegration;
import gory_moon.moarsigns.integration.railcraft.RailcraftIntegration;
import gory_moon.moarsigns.integration.tconstruct.TinkersConstructIntegration;
import gory_moon.moarsigns.integration.vanilla.MinecraftIntegration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static gory_moon.moarsigns.api.IntegrationRegistry.*;

public class IntegrationHandler {

    static {
        registerIntegration(MinecraftIntegration.class);
        registerIntegration(NaturaIntegration.class);
        registerIntegration(ForestryIntegration.class);
        registerIntegration(BiomesOPlentyIntegration.class);
        registerIntegration(IndustrialCraft2Integration.class);
        registerIntegration(TinkersConstructIntegration.class);
        registerIntegration(FactorizationIntegration.class);
        registerIntegration(RailcraftIntegration.class);

        registerPlankOreName("plankWood");

        String[] ingotNames = {"ingotCopper", "ingotTin", "ingotSilver", "ingotBronze", "ingotSteel", "ingotLead"};
        String[] blockNames = {"blockCopper", "blockTin", "blockSilver", "blockBronze", "blockSteel", "blockLead"};
        for (String name : ingotNames) registerMetalGemOreName(name);
        for (String name : blockNames) registerMetalGemOreName(name);
    }

    public static void registerSigns(ArrayList<ItemStack> planks, ArrayList<ItemStack> ingots) {
        MoarSigns.logger.info("Starting sign integrations");

        ArrayList<ISignRegistration> signReg = IntegrationRegistry.getSignReg();

        for (ISignRegistration reg : signReg) {
            reg.registerWoodenSigns(planks);
            reg.registerMetalSigns(ingots);
        }

        for (ISignRegistration reg : signReg) {
            if (reg.getActivateTag() != null && reg.getIntegrationName() != null && Loader.isModLoaded(reg.getActivateTag())) {
                SignRegistry.activateTag(reg.getActivateTag());
                MoarSigns.logger.info("Loaded " + reg.getIntegrationName() + " SignIntegration");
            }
        }

        MoarSigns.logger.info("Finished " + (SignRegistry.getActiveTagsAmount() - 1) + " sign integrations");
    }

    private ArrayList<ItemStack> getOres(ArrayList<String> names) {
        ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
        for (String name : names)
            ores.addAll(OreDictionary.getOres(name));
        return ores;
    }

    public void setupSigns() {

        ArrayList<String> names = IntegrationRegistry.getWoodNames();
        ArrayList<ItemStack> planks = getOres(names);

        names = IntegrationRegistry.getMetalNames();
        ArrayList<ItemStack> ingots = getOres(names);

        registerSigns(planks, ingots);

        Collections.sort(SignRegistry.getActivatedSignRegistry(), new Comparator<SignInfo>() {
            @Override
            public int compare(SignInfo o1, SignInfo o2) {
                return (o1.isMetal && !o2.isMetal) ? 1 : ((o1.isMetal) ? 0 : (o2.isMetal ? -1 : (o1.material.path.equals("") && o1.material.path.equals(o2.material.path) ? 0 : (o1.material.path.equals(o2.material.path) ? (o1.itemName.compareToIgnoreCase(o2.itemName)) : (o1.material.path.compareTo(o2.material.path))))));
            }
        });
    }

}

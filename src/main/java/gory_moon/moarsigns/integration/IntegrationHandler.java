package gory_moon.moarsigns.integration;

import cpw.mods.fml.common.Loader;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.api.*;
import gory_moon.moarsigns.integration.bop.BiomesOPlentyIntegration;
import gory_moon.moarsigns.integration.factorization.FactorizationIntegration;
import gory_moon.moarsigns.integration.forestry.ForestryIntegration;
import gory_moon.moarsigns.integration.ic2.IndustrialCraft2Integration;
import gory_moon.moarsigns.integration.natura.NaturaIntegration;
import gory_moon.moarsigns.integration.tconstruct.TinkersConstructIntegration;
import gory_moon.moarsigns.integration.vanilla.MinecraftIntegration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

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

        registerPlankOreName("plankWood");

        String[] names = {"ingotCopper", "ingotTin", "ingotSilver", "ingotBronze", "ingotSteel", "ingotLead"};
        for (String name : names) registerMetalGemOreName(name);

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

        MoarSigns.logger.info("Ingots");
        for (ItemStack stack : ingots)
            MoarSigns.logger.info("Stack: " + stack + ", Unloc: " + stack.getUnlocalizedName());

        registerSigns(planks, ingots);

        Collections.sort(SignRegistry.getActivatedSignRegistry(), new Comparator<SignInfo>() {
            @Override
            public int compare(SignInfo o1, SignInfo o2) {
                return (o1.isMetal && !o2.isMetal) ? 1 : ((o1.isMetal) ? 0 : (o2.isMetal ? -1 : (o1.material.path.equals("") && o1.material.path.equals(o2.material.path) ? 0 : (o1.material.path.equals(o2.material.path) ? (o1.itemName.compareToIgnoreCase(o2.itemName)) : (o1.material.path.compareTo(o2.material.path))))));
            }
        });

        Container dummyContainer = new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer entityplayer) {
                return false;
            }

            @Override
            public void onCraftMatrixChanged(IInventory par1IInventory) {
            }
        };
        InventoryCrafting crafting = new InventoryCrafting(dummyContainer, 3, 3);

        for (Map.Entry<String, HashSet<MaterialInfo>> materialList : MaterialRegistry.materialRegistry.entrySet()) {
            for (MaterialInfo material : materialList.getValue()) {
                ItemStack stack = material.material;

                if (stack.getItem() != null && !(stack.getItem() instanceof ItemBlock)) {
                    for (int i = 0; i < 9; i++) {
                        crafting.setInventorySlotContents(i, stack);
                    }
                    ItemStack stack1 = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
                    if (stack1 != null) {
                        material.materialBlock = stack1;
                    }
                    for (int i = 0; i < 9; i++) {
                        crafting.setInventorySlotContents(i, null);
                    }
                }

            }
        }
    }

}

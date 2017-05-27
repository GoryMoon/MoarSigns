package gory_moon.moarsigns.integration.thermalfoundation;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ThermalFoundationIntegration implements ISignRegistration {

    private static final String TF_KEY = "ThermalFoundation";
    private static final String PATH = "tf/";
    private Item item;
    private Item itemBlock;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("item.thermalfoundation.material.ingotTin") && item == null) {
                item = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.thermalfoundation.storage.copper.name") && itemBlock == null) {
                itemBlock = stack.getItem();
            }

            if (item != null && itemBlock != null)
                break;
        }

        SignRegistry.register("copper_sign",    null, "copper",     PATH, true, new ItemStack(item, 0, 64), new ItemStack(itemBlock, 0, 0), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("tin_sign",       null, "tin",        PATH, true, new ItemStack(item, 0, 65), new ItemStack(itemBlock, 0, 1), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("silver_sign",    null, "silver",     PATH, true, new ItemStack(item, 0, 66), new ItemStack(itemBlock, 0, 2), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("lead_sign",      null, "lead",       PATH, true, new ItemStack(item, 0, 67), new ItemStack(itemBlock, 0, 3), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("nickel_sign",    null, "nickel",     PATH, true, new ItemStack(item, 0, 68), new ItemStack(itemBlock, 0, 4), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("platinum_sign",  null, "platinum",   PATH, true, new ItemStack(item, 0, 69), new ItemStack(itemBlock, 0, 5), Reference.MODID, TF_KEY).setMetal().setRarity(1);
        SignRegistry.register("mithril_sign",   null, "mithril",    PATH, true, new ItemStack(item, 0, 70), new ItemStack(itemBlock, 0, 6), Reference.MODID, TF_KEY).setMetal().setRarity(2);
        SignRegistry.register("electrum_sign",  null, "electrum",   PATH, true, new ItemStack(item, 0, 71), new ItemStack(itemBlock, 0, 7), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("invar_sign",     null, "invar",      PATH, true, new ItemStack(item, 0, 72), new ItemStack(itemBlock, 0, 8), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("bronze_sign",    null, "bronze",     PATH, true, new ItemStack(item, 0, 73), new ItemStack(itemBlock, 0, 9), Reference.MODID, TF_KEY).setMetal();
        SignRegistry.register("signalum_sign",  null, "signalum",   PATH, true, new ItemStack(item, 0, 74), new ItemStack(itemBlock, 0, 10), Reference.MODID, TF_KEY).setMetal().setRarity(1);
        SignRegistry.register("lumium_sign",    null, "lumium",     PATH, true, new ItemStack(item, 0, 75), new ItemStack(itemBlock, 0, 11), Reference.MODID, TF_KEY).setMetal().setRarity(1);
        SignRegistry.register("enderium_sign",  null, "enderium",   PATH, true, new ItemStack(item, 0, 76), new ItemStack(itemBlock, 0, 12), Reference.MODID, TF_KEY).setMetal().setRarity(2);

    }

    @Override
    public String getActivateTag() {
        return TF_KEY;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(TF_KEY);
    }

    @Override
    public String getModName() {
        return "Thermal Foundation";
    }
}

package gory_moon.moarsigns.integration.thermalfoundation;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.ArrayList;

public class ThermalFoundationIntegration implements ISignRegistration {

    private static final String TF_KEY = "thermalfoundation";
    private static final String PATH = "tf/";
    private Item item;
    private Item itemBlock;

    @ObjectHolder("thermalfoundation:material")
    public static Item material = null;

    @ObjectHolder("thermalfoundation:storage")
    public static Block block = null;

    @ObjectHolder("thermalfoundation:storage_alloy")
    public static Block block2 = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        registerMetal("copper_sign",    "copper",     material, 192, material, 128, block, 0);
        registerMetal("tin_sign",       "tin",        material, 193, material, 129, block, 1);
        registerMetal("silver_sign",    "silver",     material, 194, material, 130, block, 2);
        registerMetal("lead_sign",      "lead",       material, 195, material, 131, block, 3);
        //TODO aluminium
        //registerMetal("aluminum_sign", "aluminium",  material, 196, material,132, block,4);
        registerMetal("nickel_sign",    "nickel",     material, 197, material, 133, block, 5);
        registerMetal("platinum_sign",  "platinum",   material, 198, material, 134, block, 6).setRarity(1);
        //TODO iridium
        //registerMetal("iridium_sign",   "iridium",    material, 199, material,135, block,7).setRarity(1);
        registerMetal("mithril_sign",   "mithril",    material, 200, material, 136, block, 8).setRarity(2);
        //TODO steel
        //registerMetal("steel_sign",     "steel",      material, 224, material,160, block2,0);
        registerMetal("electrum_sign",  "electrum",   material, 225, material, 161, block2 ,1);
        registerMetal("invar_sign",     "invar",      material, 226, material, 162, block2, 2);
        registerMetal("bronze_sign",    "bronze",     material, 227, material, 163, block2, 3);
        //TODO constantan
        //registerMetal("constantan_sign","constantan", material, 228, material,164, block2,4);
        registerMetal("signalum_sign",  "signalum",   material, 229, material, 165, block2, 5).setRarity(1);
        registerMetal("lumium_sign",    "lumium",     material, 230, material, 166, block2, 6).setRarity(1);
        registerMetal("enderium_sign",  "enderium",   material, 231, material, 167, block2, 7).setRarity(2);
    }

    private SignInfo registerMetal(String name, String materialName, Item nugget, int nMeta, Item ingot, int iMeta, Block block, int bMeta) throws IntegrationException {
        return SignRegistry.register(name, null, materialName, PATH, true, new ItemStack(nugget, 1, nMeta), new ItemStack(ingot, 1, iMeta), new ItemStack(block, 1, bMeta), Reference.MODID, TF_KEY).setMetal();
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

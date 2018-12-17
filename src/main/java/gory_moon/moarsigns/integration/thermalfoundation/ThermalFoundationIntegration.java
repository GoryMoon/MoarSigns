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

import javax.annotation.Nonnull;

public class ThermalFoundationIntegration implements ISignRegistration {

    private static final String TF_KEY = "thermalfoundation";
    private static final String PATH = "tf/";

    @ObjectHolder("thermalfoundation:material")
    public static Item material = null;

    @ObjectHolder("thermalfoundation:storage")
    public static Block block = null;

    @ObjectHolder("thermalfoundation:storage_alloy")
    public static Block block2 = null;

    @Override
    public void registerSigns() throws IntegrationException {
        registerMetal("copper_sign",     "copper",     192, 128, block,  0);
        registerMetal("tin_sign",        "tin",        193, 129, block,  1);
        registerMetal("silver_sign",     "silver",     194, 130, block,  2);
        registerMetal("lead_sign",       "lead",       195, 131, block,  3);
        registerMetal("aluminum_sign",   "aluminium",  196, 132, block,  4);
        registerMetal("nickel_sign",     "nickel",     197, 133, block,  5);
        registerMetal("platinum_sign",   "platinum",   198, 134, block,  6).setRarity(1);
        registerMetal("iridium_sign",    "iridium",    199, 135, block,  7).setRarity(1);
        registerMetal("mana_metal_sign", "mana_metal", 200, 136, block,  8).setRarity(2);
        registerMetal("steel_sign",      "steel",      224, 160, block2, 0);
        registerMetal("electrum_sign",   "electrum",   225, 161, block2, 1);
        registerMetal("invar_sign",      "invar",      226, 162, block2, 2);
        registerMetal("bronze_sign",     "bronze",     227, 163, block2, 3);
        registerMetal("constantan_sign", "constantan", 228, 164, block2, 4);
        registerMetal("signalum_sign",   "signalum",   229, 165, block2, 5).setRarity(1);
        registerMetal("lumium_sign",     "lumium",     230, 166, block2, 6).setRarity(1);
        registerMetal("enderium_sign",   "enderium",   231, 167, block2, 7).setRarity(2);
    }

    private SignInfo registerMetal(String name, String materialName, int nMeta, int iMeta, Block block, int bMeta) throws IntegrationException {
        return SignRegistry.register(name, null, materialName, PATH, true, new ItemStack(material, 1, nMeta), new ItemStack(material, 1, iMeta), new ItemStack(block, 1, bMeta), Reference.MODID, TF_KEY).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return TF_KEY;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(TF_KEY);
    }

    @Override
    public String getModName() {
        return "Thermal Foundation";
    }
}

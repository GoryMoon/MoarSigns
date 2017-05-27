package gory_moon.moarsigns.integration.natura;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class NaturaIntegration implements ISignRegistration {

    private static final String NATURA_TAG = "natura";
    private static final String PATH = "natura/";
    private Item naturaOver = null;
    private Item naturaNeth = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        for (ItemStack plank : planks) {
            if (plank.getUnlocalizedName().startsWith("tile.natura.overworld_planks") && naturaOver == null) {
                naturaOver = plank.getItem();
            } else if (plank.getUnlocalizedName().startsWith("tile.natura.nether_planks") && naturaNeth == null) {
                naturaNeth = plank.getItem();
            }

            if (naturaOver != null && naturaNeth != null)
                break;
        }

        registerWood("maple_sign",      null, "maple",      new ItemStack(naturaOver, 1, 0));
        registerWood("silverbell_sign", null, "silverbell", new ItemStack(naturaOver, 1, 1));
        registerWood("amaranth_sign",   null, "amaranth",   new ItemStack(naturaOver, 1, 2));
        registerWood("tigerwood_sign",  null, "tigerwood",  new ItemStack(naturaOver, 1, 3));
        registerWood("willow_sign",     null, "willow",     new ItemStack(naturaOver, 1, 4));
        registerWood("eucalyptus_sign", null, "eucalyptus", new ItemStack(naturaOver, 1, 5));
        registerWood("hopseed_sign",    null, "hopseed",    new ItemStack(naturaOver, 1, 6));
        registerWood("sakura_sign",     null, "sakura",     new ItemStack(naturaOver, 1, 7));
        registerWood("redwood_sign",    null, "redwood",    new ItemStack(naturaOver, 1, 8));

        registerWood("ghostwood_sign",  null, "ghostwood",  new ItemStack(naturaNeth, 1, 0));
        registerWood("bloodwood_sign",  null, "bloodwood",  new ItemStack(naturaNeth, 1, 1));
        registerWood("darkwood_sign",   null, "darkwood",   new ItemStack(naturaNeth, 1, 2));
        registerWood("fusewood_sign",   null, "fusewood",   new ItemStack(naturaNeth, 1, 3));

    }

    private void registerWood(String name, SignSpecialProperty property, String materialName, ItemStack material) throws IntegrationException {
        SignRegistry.register(name, property, materialName, PATH, false, material, Reference.MODID, NATURA_TAG);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        // No metal to register
    }

    @Override
    @Nonnull
    public String getActivateTag() {
        return NATURA_TAG;
    }

    @Override
    @Nonnull
    public String getIntegrationName() {
        return Utils.getModName(NATURA_TAG);
    }

    @Override
    public String getModName() {
        return StringUtils.capitalize(NATURA_TAG);
    }
}

package gory_moon.moarsigns.integration.natura;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

public class NaturaIntegration implements ISignRegistration {

    private static final String NATURA_TAG = "natura";
    private static final String PATH = "natura/";

    @ObjectHolder("natura:overworld_planks")
    public static Item naturaOver = null;

    @ObjectHolder("natura:nether_planks")
    public static Item naturaNeth = null;

    @Override
    public void registerSigns() throws IntegrationException {
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
        SignRegistry.register(name, property, materialName, PATH, false, ItemStack.EMPTY, material, Reference.MODID, NATURA_TAG);
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

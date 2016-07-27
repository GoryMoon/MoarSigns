package gory_moon.moarsigns.integration.natura;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class NaturaIntegration implements ISignRegistration {

    private static final String NATURA_TAG = "Natura";
    private static final String PATH = "natura/";
    private Item naturaItem = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        for (ItemStack plank : planks) {
            if (plank.getUnlocalizedName().equals("block.eucalyptus.NPlanks")) {
                naturaItem = plank.getItem();
                break;
            }
        }

        registerWood("eucalyptus_sign",    null, "eucalyptus",     new ItemStack(naturaItem, 1, 0));
        registerWood("sakura_sign",        null, "sakura",         new ItemStack(naturaItem, 1, 1));
        registerWood("ghostwood_sign",     null, "ghostwood",      new ItemStack(naturaItem, 1, 2));
        registerWood("redwood_sign",       null, "redwood",        new ItemStack(naturaItem, 1, 3));
        registerWood("bloodwood_sign",     null, "bloodwood",      new ItemStack(naturaItem, 1, 4));
        registerWood("hopseed_sign",       null, "hopseed",        new ItemStack(naturaItem, 1, 5));
        registerWood("maple_sign",         null, "maple",          new ItemStack(naturaItem, 1, 6));
        registerWood("silverbell_sign",    null, "silverbell",     new ItemStack(naturaItem, 1, 7));
        registerWood("purpleheart_sign",   null, "purpleheart",    new ItemStack(naturaItem, 1, 8));
        registerWood("tiger_sign",         null, "tiger",          new ItemStack(naturaItem, 1, 9));
        registerWood("willow_sign",        null, "willow",         new ItemStack(naturaItem, 1, 10));
        registerWood("darkwood_sign",      null, "darkwood",       new ItemStack(naturaItem, 1, 11));
        registerWood("fusewood_sign",      null, "fusewood",       new ItemStack(naturaItem, 1, 12));

    }

    private void registerWood(String name, SignSpecialProperty property, String materialName, ItemStack material) throws IntegrationException {
        SignRegistry.register(name, property, materialName, PATH, false, material, ModInfo.ID, NATURA_TAG);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {

    }

    @Override
    public String getActivateTag() {
        return NATURA_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(NATURA_TAG);
    }

    @Override
    public String getModName() {
        return NATURA_TAG;
    }
}

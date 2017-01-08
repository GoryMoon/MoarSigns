package gory_moon.moarsigns.integration.bigrectors;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.api.SignSpecialProperty;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class BigReactorsIntegration implements ISignRegistration {

    private static final String BIGREACTORS_ID = "bigreactors";
    private static final String BIGREACTORS_NAME = "Extreame Reactors";
    private static final String PATH = "bigreactors/";
    private Item item = null;
    private Item itemBlock = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("item.bigreactors:ingotMetals.yellorium") && item == null) {
                item = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.bigreactors:blockMetals.yellorium") && itemBlock == null) {
                itemBlock = stack.getItem();
            }

            if (item != null && itemBlock != null)
                break;
        }

        registerMetal("yellorium_sign", null, "yellorium",  new ItemStack(item, 1, 0), new ItemStack(itemBlock, 1, 0));
        registerMetal("cyanite_sign",   null, "cyanite",    new ItemStack(item, 1, 1), new ItemStack(itemBlock, 1, 1));
        registerMetal("graphite_sign",  null, "graphite",   new ItemStack(item, 1, 2), new ItemStack(itemBlock, 1, 2));
        registerMetal("blutonium_sign", null, "blutonium",  new ItemStack(item, 1, 3), new ItemStack(itemBlock, 1, 3));
        registerMetal("ludicrite_sign", null, "ludicrite",  new ItemStack(item, 1, 4), new ItemStack(itemBlock, 1, 4));
        registerMetal("steel_sign",     null, "steel",      new ItemStack(item, 1, 5), new ItemStack(itemBlock, 1, 5));
    }

    private void registerMetal(String name, SignSpecialProperty property, String materialName, ItemStack material, ItemStack block) throws IntegrationException {
        SignRegistry.register(name, property, materialName, PATH, false, material, block, Reference.MODID, BIGREACTORS_ID).setMetal();
    }


    @Nonnull
    @Override
    public String getActivateTag() {
        return BIGREACTORS_ID;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(BIGREACTORS_ID);
    }

    @Nullable
    @Override
    public String getModName() {
        return BIGREACTORS_NAME;
    }
}

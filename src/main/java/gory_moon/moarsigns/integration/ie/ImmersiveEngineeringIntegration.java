package gory_moon.moarsigns.integration.ie;

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

public class ImmersiveEngineeringIntegration implements ISignRegistration {

    private static final String IE_ID = "immersiveengineering";
    private static final String IE_NAME = "Immersive Engineering";
    private static final String PATH = "ie/";
    private Item item;
    private Item itemWood;
    private Item itemBlock;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        for (ItemStack stack: planks) {
            if (stack.getUnlocalizedName().startsWith("tile.immersiveengineering.treatedWood")) {
                itemWood = stack.getItem();
                break;
            }
        }

        SignRegistry.register("treatedwood_sign", null, "treatedwood", PATH, false, new ItemStack(itemWood, 0, 0), Reference.MODID, IE_ID);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().startsWith("item.immersiveengineering.metal.") && item == null) {
                item = stack.getItem();
            }

            if (stack.getUnlocalizedName().startsWith("tile.immersiveengineering.storage.") && itemBlock == null) {
                itemBlock = stack.getItem();
            }

            if (item != null && itemBlock != null)
                break;
        }

        registerMetal("copper_sign",        null, "copper",     item, 0, itemBlock, 0);
        registerMetal("aluminium_sign",     null, "aluminium",  item, 1, itemBlock, 1);
        registerMetal("lead_sign",          null, "lead",       item, 2, itemBlock, 2);
        registerMetal("silver_sign",        null, "silver",     item, 3, itemBlock, 3);
        registerMetal("nickel_sign",        null, "nickel",     item, 4, itemBlock, 4);
        registerMetal("uranium_sign",       null, "uranium",    item, 5, itemBlock, 5);
        registerMetal("constantan_sign",    null, "constantan", item, 6, itemBlock, 6);
        registerMetal("electrum_sign",      null, "electrum",   item, 7, itemBlock, 7);
        registerMetal("steel_sign",         null, "steel",      item, 8, itemBlock, 8);
    }

    private void registerMetal(String name, SignSpecialProperty property, String materialName, Item item, int meta, Item block, int blockMeta) throws IntegrationException {
        SignRegistry.register(name, property, materialName, PATH, true, new ItemStack(item, 1, meta), new ItemStack(block, 1, blockMeta), Reference.MODID, IE_ID).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return IE_ID;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(IE_ID);
    }

    @Nullable
    @Override
    public String getModName() {
        return IE_NAME;
    }
}

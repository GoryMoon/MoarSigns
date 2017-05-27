package gory_moon.moarsigns.integration.draconicevolution;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class DraconicEvolutionIntegration implements ISignRegistration {

    private static final String DR_ID = "draconicevolution";
    private static final String DR_NAME = "Draconic Evolution";
    private static final String PATH = "draconicevolution/";
    private Item item1;
    private Item item2;
    private Item itemBlock1;
    private Item itemBlock2;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        for (ItemStack stack: metals) {
            if (stack.getUnlocalizedName().equals("item.draconicevolution:draconium_ingot") && item1 == null) {
                item1 = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("item.draconicevolution:draconic_ingot") && item2 == null) {
                item2 = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.draconicevolution:draconium_block.charged.false") && itemBlock1 == null) {
                itemBlock1 = stack.getItem();
            }

            if (stack.getUnlocalizedName().equals("tile.draconicevolution:draconic_block") && itemBlock2 == null) {
                itemBlock2 = stack.getItem();
            }

            if (item1 != null && item2 != null && itemBlock1 != null && itemBlock2 != null)
                break;
        }

        SignRegistry.register("draconium_sign", null, "draconium", PATH, true, new ItemStack(item1, 1, 0), new ItemStack(itemBlock1, 1, 0), Reference.MODID, DR_ID).setMetal();
        SignRegistry.register("draconic_sign", null, "draconic", PATH, true, new ItemStack(item2, 1, 0), new ItemStack(itemBlock2, 1, 0), Reference.MODID, DR_ID).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return DR_ID;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(DR_ID);
    }

    @Nullable
    @Override
    public String getModName() {
        return DR_NAME;
    }
}

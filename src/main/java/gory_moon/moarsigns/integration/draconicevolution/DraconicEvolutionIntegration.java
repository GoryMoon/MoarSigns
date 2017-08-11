package gory_moon.moarsigns.integration.draconicevolution;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class DraconicEvolutionIntegration implements ISignRegistration {

    private static final String DR_ID = "draconicevolution";
    private static final String DR_NAME = "Draconic Evolution";
    private static final String PATH = "draconicevolution/";

    @ObjectHolder("draconicevolution:nugget")
    public static Item nugget;

    @ObjectHolder("draconicevolution:draconium_ingot")
    public static Item ingot1;

    @ObjectHolder("draconicevolution:draconic_ingot")
    public static Item ingot2;

    @ObjectHolder("draconicevolution:draconium_block")
    public static Block itemBlock1;

    @ObjectHolder("draconicevolution:draconic_block")
    public static Block itemBlock2;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        SignRegistry.register("draconium_sign", null, "draconium", PATH, true, new ItemStack(nugget, 1, 0), new ItemStack(ingot1, 1, 0), new ItemStack(itemBlock1, 1, 0), Reference.MODID, DR_ID).setMetal();
        SignRegistry.register("draconic_sign", null, "draconic", PATH, true, new ItemStack(nugget, 1, 1), new ItemStack(ingot2, 1, 0), new ItemStack(itemBlock2, 1, 0), Reference.MODID, DR_ID).setMetal();
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

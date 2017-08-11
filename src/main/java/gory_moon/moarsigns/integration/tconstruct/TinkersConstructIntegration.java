package gory_moon.moarsigns.integration.tconstruct;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TinkersConstructIntegration implements ISignRegistration {

    private static final String TCONSTRUCT_TAG = "tconstruct";
    private static final String TCONSTRUCT_NAME = "Tinkers' Construct";

    @ObjectHolder("tconstruct:ingots")
    public static Item item = null;

    @ObjectHolder("tconstruct:metal")
    public static Item itemBlock = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        SignRegistry.register("cobalt_sign",        null, "cobalt",         "tconstruct/", true, ItemStack.EMPTY, new ItemStack(item, 1, 0), new ItemStack(itemBlock, 1, 0), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("ardite_sign",        null, "ardite",         "tconstruct/", true, ItemStack.EMPTY, new ItemStack(item, 1, 1), new ItemStack(itemBlock, 1, 1), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("manyullyn_sign",     null, "manyullyn",      "tconstruct/", true, ItemStack.EMPTY, new ItemStack(item, 1, 2), new ItemStack(itemBlock, 1, 2), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("knightslime_sign",   null, "knightslime",    "tconstruct/", true, ItemStack.EMPTY, new ItemStack(item, 1, 3), new ItemStack(itemBlock, 1, 3), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("pigiron_sign",       null, "pigiron",        "tconstruct/", true, ItemStack.EMPTY, new ItemStack(item, 1, 4), new ItemStack(itemBlock, 1, 4), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("aluminumbrass_sign", null, "aluminum_brass", "tconstruct/", true, ItemStack.EMPTY, new ItemStack(item, 1, 5), new ItemStack(itemBlock, 1, 5), Reference.MODID, TCONSTRUCT_TAG).setMetal();


        //SignRegistry.register("copper_sign", null, "copper", "tconstruct/", true, new ItemStack(ingot, 1, 9), new ItemStack(itemBlock, 1, 3), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        //SignRegistry.register("tin_sign", null, "tin", "tconstruct/", true, new ItemStack(ingot, 1, 10), new ItemStack(itemBlock, 1, 5), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        //SignRegistry.register("aluminum_sign", null, "aluminum", "tconstruct/", true, new ItemStack(ingot, 1, 11), new ItemStack(itemBlock, 1, 6), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        //SignRegistry.register("bronze_sign", null, "bronze", "tconstruct/", true, new ItemStack(ingot, 1, 13), new ItemStack(itemBlock, 1, 4), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        //SignRegistry.register("alumite_sign", null, "alumite", "tconstruct/", true, new ItemStack(ingot, 1, 15), new ItemStack(itemBlock, 1, 8), Reference.MODID, TCONSTRUCT_TAG).setMetal();
        //SignRegistry.register("steel_sign", null, "steel", "tconstruct/", true, new ItemStack(ingot, 1, 16), new ItemStack(itemBlock, 1, 9), Reference.MODID, TCONSTRUCT_TAG).setMetal();
    }

    @Override
    @Nonnull
    public String getActivateTag() {
        return TCONSTRUCT_TAG;
    }

    @Override
    @Nonnull
    public String getIntegrationName() {
        return Utils.getModName(TCONSTRUCT_TAG);
    }

    @Override
    public String getModName() {
        return TCONSTRUCT_NAME;
    }
}

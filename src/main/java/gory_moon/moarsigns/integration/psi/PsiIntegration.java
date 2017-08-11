package gory_moon.moarsigns.integration.psi;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class PsiIntegration implements ISignRegistration {

    private static final String PSI_TAG = "psi";
    private static final String PATH = "psi/";

    @ObjectHolder("psi:material")
    public static Item item = null;

    @ObjectHolder("psi:psi_decorative")
    public static Item itemBlock = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {
        // No wood to register
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        registerMetal("psimetal_sign",      "psimetal",       new ItemStack(item, 1, 1), new ItemStack(itemBlock, 1, 1));
        registerMetal("psigem_sign",        "psigem",         new ItemStack(item, 1, 2), new ItemStack(itemBlock, 1, 2));
        registerMetal("ebonypsimetal_sign", "ebonypsimetal",  new ItemStack(item, 1, 3), new ItemStack(itemBlock, 1, 7));
        registerMetal("ivorypsimetal_sign", "ivotypsimetal",  new ItemStack(item, 1, 4), new ItemStack(itemBlock, 1, 8));
    }

    private void registerMetal(String name, String materialName, ItemStack material, ItemStack block) throws IntegrationException {
        SignRegistry.register(name, null, materialName, PATH, false, ItemStack.EMPTY, material, block, Reference.MODID, PSI_TAG).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return PSI_TAG;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return Utils.getModName(PSI_TAG);
    }

    @Nullable
    @Override
    public String getModName() {
        return PSI_TAG;
    }
}

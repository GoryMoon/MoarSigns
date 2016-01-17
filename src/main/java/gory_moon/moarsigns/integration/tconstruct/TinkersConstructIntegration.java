package gory_moon.moarsigns.integration.tconstruct;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TinkersConstructIntegration implements ISignRegistration {

    private static final String TCONSTRUCT_TAG = "TConstruct";
    private Item item = null;
    private Item itemBlock = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {

    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {
        for (ItemStack stack : metals) {
            if (stack.getUnlocalizedName().equals("item.tconstruct.Materials.CopperIngot") && item == null) {
                item = stack.copy().getItem();
            }

            if (stack.getUnlocalizedName().equals("StorageMetals.Cobalt") && itemBlock == null) {
                itemBlock = stack.copy().getItem();
            }

            if (item != null && itemBlock != null) break;
        }

        SignRegistry.register("cobalt_sign", null, "cobalt", "tconstruct/", true, new ItemStack(item, 1, 3), new ItemStack(itemBlock, 1, 0), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("ardite_sign", null, "ardite", "tconstruct/", true, new ItemStack(item, 1, 4), new ItemStack(itemBlock, 1, 1), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("manyullyn_sign", null, "manyullyn", "tconstruct/", true, new ItemStack(item, 1, 5), new ItemStack(itemBlock, 1, 2), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("copper_sign", null, "copper", "tconstruct/", true, new ItemStack(item, 1, 9), new ItemStack(itemBlock, 1, 3), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("tin_sign", null, "tin", "tconstruct/", true, new ItemStack(item, 1, 10), new ItemStack(itemBlock, 1, 5), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("aluminum_sign", null, "aluminum", "tconstruct/", true, new ItemStack(item, 1, 11), new ItemStack(itemBlock, 1, 6), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("bronze_sign", null, "bronze", "tconstruct/", true, new ItemStack(item, 1, 13), new ItemStack(itemBlock, 1, 4), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("aluminumbrass_sign", null, "aluminum_brass", "tconstruct/", true, new ItemStack(item, 1, 14), new ItemStack(itemBlock, 1, 7), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("alumite_sign", null, "alumite", "tconstruct/", true, new ItemStack(item, 1, 15), new ItemStack(itemBlock, 1, 8), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
        SignRegistry.register("steel_sign", null, "steel", "tconstruct/", true, new ItemStack(item, 1, 16), new ItemStack(itemBlock, 1, 9), ModInfo.ID, TCONSTRUCT_TAG).setMetal();
    }

    @Override
    public String getActivateTag() {
        return TCONSTRUCT_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(TCONSTRUCT_TAG);
    }
}

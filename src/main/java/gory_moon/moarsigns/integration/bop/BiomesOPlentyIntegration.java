package gory_moon.moarsigns.integration.bop;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class BiomesOPlentyIntegration implements ISignRegistration {

    private static final String BOP_TAG = "BiomesOPlenty";
    private static final String BOP_NAME = "Biomes O' Plenty";
    private Item bopItem = null;

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {
        for (ItemStack plank : planks) {
            if (plank.getUnlocalizedName().equals("tile.planks.sacredoakPlank")) {
                bopItem = plank.getItem();
                break;
            }
        }

        SignRegistry.register("sacred_oak_sign", null, "sacred_oak", "bop/", false, new ItemStack(bopItem, 1, 0), ModInfo.ID, BOP_TAG);
        SignRegistry.register("cherry_sign", null, "cherry", "bop/", false, new ItemStack(bopItem, 1, 1), ModInfo.ID, BOP_TAG);
        SignRegistry.register("dark_sign", null, "dark", "bop/", false, new ItemStack(bopItem, 1, 2), ModInfo.ID, BOP_TAG);
        SignRegistry.register("fir_sign", null, "fir", "bop/", false, new ItemStack(bopItem, 1, 3), ModInfo.ID, BOP_TAG);
        SignRegistry.register("holy_sign", null, "holy", "bop/", false, new ItemStack(bopItem, 1, 4), ModInfo.ID, BOP_TAG);
        SignRegistry.register("magic_sign", null, "magic", "bop/", false, new ItemStack(bopItem, 1, 5), ModInfo.ID, BOP_TAG);
        SignRegistry.register("mangrove_sign", null, "mangrove", "bop/", false, new ItemStack(bopItem, 1, 6), ModInfo.ID, BOP_TAG);
        SignRegistry.register("palm_sign", null, "palm", "bop/", false, new ItemStack(bopItem, 1, 7), ModInfo.ID, BOP_TAG);
        SignRegistry.register("redwood_sign", null, "redwood", "bop/", false, new ItemStack(bopItem, 1, 8), ModInfo.ID, BOP_TAG);
        SignRegistry.register("willow_sign", null, "willow", "bop/", false, new ItemStack(bopItem, 1, 9), ModInfo.ID, BOP_TAG);
        SignRegistry.register("bamboo_sign", null, "bamboo", "bop/", false, new ItemStack(bopItem, 1, 10), ModInfo.ID, BOP_TAG);
        SignRegistry.register("pine_sign", null, "pine", "bop/", false, new ItemStack(bopItem, 1, 11), ModInfo.ID, BOP_TAG);
        SignRegistry.register("hellbark_sign", null, "hellbark", "bop/", false, new ItemStack(bopItem, 1, 12), ModInfo.ID, BOP_TAG);
        SignRegistry.register("jacaranda_sign", null, "jacaranda", "bop/", false, new ItemStack(bopItem, 1, 13), ModInfo.ID, BOP_TAG);
        SignRegistry.register("mahogany_sign", null, "mahogany", "bop/", false, new ItemStack(bopItem, 1, 14), ModInfo.ID, BOP_TAG);

    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {

    }

    @Override
    public String getActivateTag() {
        return BOP_TAG;
    }

    @Override
    public String getIntegrationName() {
        return Utils.getModName(BOP_TAG);
    }

    @Override
    public String getModName() {
        return BOP_NAME;
    }
}

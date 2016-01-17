package gory_moon.moarsigns.integration.vanilla;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.ModInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class MinecraftIntegration implements ISignRegistration {

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) {

        Item vanillaItem = null;
        for (ItemStack plank : planks) {
            if (plank.getUnlocalizedName().equals("tile.wood.oak")) {
                vanillaItem = plank.copy().getItem();
                break;
            }
        }

        SignRegistry.register("oak_sign", null, "oak", "", false, new ItemStack(vanillaItem, 1, 0), ModInfo.ID);
        SignRegistry.register("spruce_sign", null, "spruce", "", false, new ItemStack(vanillaItem, 1, 1), ModInfo.ID);
        SignRegistry.register("birch_sign", null, "birch", "", false, new ItemStack(vanillaItem, 1, 2), ModInfo.ID);
        SignRegistry.register("jungle_sign", null, "jungle", "", false, new ItemStack(vanillaItem, 1, 3), ModInfo.ID);
        SignRegistry.register("acacia_sign", null, "acacia", "", false, new ItemStack(vanillaItem, 1, 4), ModInfo.ID);
        SignRegistry.register("big_oak_sign", null, "big_oak", "", false, new ItemStack(vanillaItem, 1, 5), ModInfo.ID);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) {
        ItemStack iron = new ItemStack(Items.iron_ingot);
        ItemStack gold = new ItemStack(Items.gold_ingot);
        ItemStack diamond = new ItemStack(Items.diamond);
        ItemStack emerald = new ItemStack(Items.emerald);

        SignRegistry.register("iron_sign", null, "iron", "", false, iron, ModInfo.ID).setMetal();
        SignRegistry.register("gold_sign", null, "gold", "", true, gold, ModInfo.ID).setMetal();
        SignRegistry.register("diamond_sign", null, "diamond", "", false, diamond, ModInfo.ID).setMetal();
        SignRegistry.register("emerald_sign", null, "emerald", "", false, emerald, ModInfo.ID).setMetal();
    }

    @Override
    public String getActivateTag() {
        return null;
    }

    @Override
    public String getIntegrationName() {
        return null;
    }
}

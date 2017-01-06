package gory_moon.moarsigns.integration.vanilla;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class MinecraftIntegration implements ISignRegistration {

    @Override
    public void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException {

        Item vanillaItem = null;
        for (ItemStack plank : planks) {
            if (plank.getUnlocalizedName().equals("tile.wood.oak")) {
                vanillaItem = plank.copy().getItem();
                break;
            }
        }

        SignRegistry.register("oak_sign",       null, "oak",        "", false, new ItemStack(vanillaItem, 1, 0), Reference.MODID);
        SignRegistry.register("spruce_sign",    null, "spruce",     "", false, new ItemStack(vanillaItem, 1, 1), Reference.MODID);
        SignRegistry.register("birch_sign",     null, "birch",      "", false, new ItemStack(vanillaItem, 1, 2), Reference.MODID);
        SignRegistry.register("jungle_sign",    null, "jungle",     "", false, new ItemStack(vanillaItem, 1, 3), Reference.MODID);
        SignRegistry.register("acacia_sign",    null, "acacia",     "", false, new ItemStack(vanillaItem, 1, 4), Reference.MODID);
        SignRegistry.register("big_oak_sign",   null, "big_oak",    "", false, new ItemStack(vanillaItem, 1, 5), Reference.MODID);
    }

    @Override
    public void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException {
        SignRegistry.register("iron_sign",      null, "iron",       "", false, new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.IRON_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("gold_sign",      null, "gold",       "", true,  new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.GOLD_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("diamond_sign",   null, "diamond",    "", false, new ItemStack(Items.DIAMOND), new ItemStack(Blocks.DIAMOND_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("emerald_sign",   null, "emerald",    "", false, new ItemStack(Items.EMERALD), new ItemStack(Blocks.EMERALD_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("lapis_sign",     null, "lapis",      "", false, new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Blocks.LAPIS_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("quartz_sign",    null, "quartz",     "", false, new ItemStack(Items.QUARTZ), new ItemStack(Blocks.QUARTZ_BLOCK), Reference.MODID).setMetal();
    }

    @Override
    public String getActivateTag() {
        return SignRegistry.ALWAYS_ACTIVE_TAG;
    }

    @Override
    public String getIntegrationName() {
        return "Minecraft";
    }

    @Override
    public String getModName() {
        return null;
    }
}

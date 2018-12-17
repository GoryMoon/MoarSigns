package gory_moon.moarsigns.integration.vanilla;

import gory_moon.moarsigns.api.ISignRegistration;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.util.IntegrationException;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MinecraftIntegration implements ISignRegistration {

    @Override
    public void registerSigns() throws IntegrationException {
        SignRegistry.register("oak_sign",       null, "oak",        "", false, ItemStack.EMPTY, new ItemStack(Blocks.PLANKS, 1, 0), Reference.MODID);
        SignRegistry.register("spruce_sign",    null, "spruce",     "", false, ItemStack.EMPTY, new ItemStack(Blocks.PLANKS, 1, 1), Reference.MODID);
        SignRegistry.register("birch_sign",     null, "birch",      "", false, ItemStack.EMPTY, new ItemStack(Blocks.PLANKS, 1, 2), Reference.MODID);
        SignRegistry.register("jungle_sign",    null, "jungle",     "", false, ItemStack.EMPTY, new ItemStack(Blocks.PLANKS, 1, 3), Reference.MODID);
        SignRegistry.register("acacia_sign",    null, "acacia",     "", false, ItemStack.EMPTY, new ItemStack(Blocks.PLANKS, 1, 4), Reference.MODID);
        SignRegistry.register("big_oak_sign",   null, "big_oak",    "", false, ItemStack.EMPTY, new ItemStack(Blocks.PLANKS, 1, 5), Reference.MODID);
        SignRegistry.register("iron_sign",      null, "iron",       "", true, new ItemStack(Items.IRON_NUGGET, 1, 0), new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.IRON_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("gold_sign",      null, "gold",       "", true, new ItemStack(Items.GOLD_NUGGET, 1, 0), new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.GOLD_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("diamond_sign",   null, "diamond",    "", false, ItemStack.EMPTY, new ItemStack(Items.DIAMOND), new ItemStack(Blocks.DIAMOND_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("emerald_sign",   null, "emerald",    "", false, ItemStack.EMPTY, new ItemStack(Items.EMERALD), new ItemStack(Blocks.EMERALD_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("lapis_sign",     null, "lapis",      "", false, ItemStack.EMPTY, new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Blocks.LAPIS_BLOCK), Reference.MODID).setMetal();
        SignRegistry.register("quartz_sign",    null, "quartz",     "", false, ItemStack.EMPTY, new ItemStack(Items.QUARTZ), new ItemStack(Blocks.QUARTZ_BLOCK), Reference.MODID).setMetal();
    }

    @Nonnull
    @Override
    public String getActivateTag() {
        return SignRegistry.ALWAYS_ACTIVE_TAG;
    }

    @Nonnull
    @Override
    public String getIntegrationName() {
        return "Minecraft";
    }

    @Override
    public String getModName() {
        return null;
    }
}

package gory_moon.moarsigns.api;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface ISignRegistration extends IIntegrationInfo {

    /**
     * Called to register signs of wood
     *
     * @param planks List of planks from {@link net.minecraftforge.oredict.OreDictionary}
     */
    void registerWoodenSigns(ArrayList<ItemStack> planks);

    /**
     * Called to register signs of metal
     *
     * @param ingots List of ingots from {@link net.minecraftforge.oredict.OreDictionary}
     */
    void registerMetalSigns(ArrayList<ItemStack> ingots);

}

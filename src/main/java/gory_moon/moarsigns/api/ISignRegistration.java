package gory_moon.moarsigns.api;

import gory_moon.moarsigns.util.IntegrationException;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface ISignRegistration extends IIntegrationInfo {

    /**
     * Called to register signs of wood
     *
     * @param planks List of planks from {@link net.minecraftforge.oredict.OreDictionary}
     */
    void registerWoodenSigns(ArrayList<ItemStack> planks) throws IntegrationException;

    /**
     * Called to register signs of metal
     *
     * @param metals List of ingots and metal blocks from {@link net.minecraftforge.oredict.OreDictionary}
     */
    void registerMetalSigns(ArrayList<ItemStack> metals) throws IntegrationException;

}

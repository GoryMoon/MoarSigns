package gory_moon.moarsigns.api;

import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.IntegrationException;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.ArrayList;

public class Api {

    /**
     * Helper method for {@link SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, String)}
     *
     * @see SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, String)
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, String modId) throws IntegrationException {
        return SignRegistry.register(itemName, property, materialName, path, gotNugget, materialItemStack, modId);
    }

    /**
     * Helper method for {@link SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, String, String)}
     *
     * @see SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, String, String)
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, String modId, String activateTag) throws IntegrationException {
        return SignRegistry.register(itemName, property, materialName, path, gotNugget, materialItemStack, modId, activateTag);
    }

    /**
     * Helper method for {@link SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, ItemStack, String)}
     *
     * @see SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, ItemStack, String)
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock, String modId) throws IntegrationException {
        return SignRegistry.register(itemName, property, materialName, path, gotNugget, materialItemStack, materialBlock, modId);
    }

    /**
     * Helper method for {@link SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, ItemStack, String, String)}
     *
     * @see SignRegistry#register(String, SignSpecialProperty, String, String, boolean, ItemStack, ItemStack, String, String)
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock, String modId, String activateTag) throws IntegrationException {
        return SignRegistry.register(itemName, property, materialName, path, gotNugget, materialItemStack, materialBlock, modId, activateTag);
    }

    /**
     * Helper method for {@link SignRegistry#registerAlternativeMaterial(SignInfo, String, String, boolean, ItemStack)}
     *
     * @see SignRegistry#registerAlternativeMaterial(SignInfo, String, String, boolean, ItemStack)
     */
    public static MaterialInfo registerAlternativeMaterial(SignInfo sInfo, String materialName, String path, boolean gotNugget, ItemStack materialItemStack) throws IntegrationException {
        return SignRegistry.registerAlternativeMaterial(sInfo, materialName, path, gotNugget, materialItemStack);
    }

    /**
     * Helper method for {@link SignRegistry#registerAlternativeMaterial(SignInfo, String, String, boolean, ItemStack, ItemStack)}
     *
     * @see SignRegistry#registerAlternativeMaterial(SignInfo, String, String, boolean, ItemStack, ItemStack)
     */
    public static MaterialInfo registerAlternativeMaterial(SignInfo sInfo, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock) throws IntegrationException {
        return SignRegistry.registerAlternativeMaterial(sInfo, materialName, path, gotNugget, materialItemStack, materialBlock);
    }

    /**
     * Helper method for {@link SignRegistry#registerAlternativeMaterial(SignInfo, MaterialInfo)}
     *
     * @see SignRegistry#registerAlternativeMaterial(SignInfo, MaterialInfo)
     */
    public static MaterialInfo registerAlternativeMaterial(SignInfo sInfo, MaterialInfo mInfo) throws IntegrationException {
        return SignRegistry.registerAlternativeMaterial(sInfo, mInfo);
    }

    /**
     * Rotates the sign by one step
     *
     * @param tileEntity The tile entity of the sign that should setRotation
     * @param reverse    If the rotation would be in reverse
     */
    public static void rotateSign(TileEntity tileEntity, boolean reverse) {
        if (tileEntity instanceof TileEntityMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) tileEntity, reverse);
        }
    }

    /**
     * Rotates the sign by a given amount
     *
     * @param tileEntity The tile entity of the sign that should setRotation
     * @param rotations  The amount of rotations to perform
     */
    public static void rotateSign(TileEntity tileEntity, int rotations) {
        if (tileEntity instanceof TileEntityMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) tileEntity, rotations);
        }
    }

    /**
     * Sets a specific rotation of a sign
     * Valid values are 0-15
     *
     * @param tileEntity The tile entity of the sign that should setRotation
     * @param rotation   The rotation to set
     */
    public static void setRotationSign(TileEntity tileEntity, int rotation) {
        if (tileEntity instanceof TileEntityMoarSign) {
            RotationHandler.setRotation((TileEntityMoarSign) tileEntity, rotation);
        }
    }

    /**
     * Registers a class that implements {@link ISignRegistration}
     * It needs to be registered before {@link FMLPostInitializationEvent}
     *
     * @param clazz The class
     */
    public static <T extends ISignRegistration> void registerSignIntegration(Class<T> clazz) {
        IntegrationRegistry.registerIntegration(clazz);
    }

    /**
     * Registers a class that implements {@link ISignRegistration}
     * It needs to be registered before {@link FMLPostInitializationEvent}
     *
     * @param registration The instance of the class
     */
    public static void registerSignIntegration(ISignRegistration registration) {
        IntegrationRegistry.registerIntegration(registration);
    }

    /**
     * Adds a name of wood to list that is given in {@link ISignRegistration#registerWoodenSigns(ArrayList)}
     *
     * @param name Name in ore dictionary
     */
    public static void registerPlankOreName(String name) {
        IntegrationRegistry.registerPlankOreName(name);
    }

    /**
     * Adds a name of metal, MetalBlock or gem to list that is given in {@link ISignRegistration#registerMetalSigns(ArrayList)}
     *
     * @param name Name in ore dictionary
     */
    public static void registerMetalGemOreName(String name) {
        IntegrationRegistry.registerMetalGemOreName(name);
    }
}

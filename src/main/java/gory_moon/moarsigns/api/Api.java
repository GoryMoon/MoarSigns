package gory_moon.moarsigns.api;

import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.tileentity.TileEntity;

public class Api {

    /**
     * Rotates the sign by one step
     *
     * @param tileEntity The tile entity of the sign that should setRotation
     */
    public static void rotateSign(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) tileEntity);
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
     * Registers a class that implements {@link gory_moon.moarsigns.api.ISignRegistration}
     * It needs to be registered before {@link cpw.mods.fml.common.event.FMLPostInitializationEvent}
     *
     * @param clazz The class
     */
    public static <T extends ISignRegistration> void registerSignIntegration(Class<T> clazz) {
        IntegrationRegistry.registerIntegration(clazz);
    }


    /**
     * Registers a class that implements {@link gory_moon.moarsigns.api.ISignRegistration}
     * It needs to be registered before {@link cpw.mods.fml.common.event.FMLPostInitializationEvent}
     *
     * @param registration The instance of the class
     */
    public static void registerSignIntegration(ISignRegistration registration) {
        IntegrationRegistry.registerIntegration(registration);
    }
}

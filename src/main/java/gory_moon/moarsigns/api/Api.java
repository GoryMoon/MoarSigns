package gory_moon.moarsigns.api;

import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.tileentity.TileEntity;

public class Api {

    public static void rotateSign(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) tileEntity);
        }
    }

    public static void rotateSign(TileEntity tileEntity, int rotation) {
        if (tileEntity instanceof TileEntityMoarSign) {
            RotationHandler.rotate((TileEntityMoarSign) tileEntity, rotation);
        }
    }

}

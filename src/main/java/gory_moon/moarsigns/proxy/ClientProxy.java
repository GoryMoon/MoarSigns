package gory_moon.moarsigns.proxy;

import gory_moon.moarsigns.client.layer.LayerMoarSign;
import gory_moon.moarsigns.client.renderers.MoarSignRenderer;
import gory_moon.moarsigns.integration.tumat.TumatIntegration;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoarSign.class, new MoarSignRenderer());
    }

    @Override
    public void init() {
        LayerMoarSign layer = new LayerMoarSign();
        Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default").addLayer(layer);
        Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim").addLayer(layer);

        if (Loader.isModLoaded("tumat")) {
            TumatIntegration.register();
        }
    }
}

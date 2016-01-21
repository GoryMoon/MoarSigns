package gory_moon.moarsigns.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import gory_moon.moarsigns.client.renderers.MoarSignRenderer;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;

public class ClientProxy extends CommonProxy {

    @Override
    public void initRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoarSign.class, new MoarSignRenderer());
    }
}

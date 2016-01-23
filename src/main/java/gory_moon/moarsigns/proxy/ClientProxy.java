package gory_moon.moarsigns.proxy;

import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.client.ModelsMoarSigns;
import gory_moon.moarsigns.client.renderers.MoarSignRenderer;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoarSign.class, new MoarSignRenderer());
        new IntegrationHandler().preSetupSigns();

        ModelsMoarSigns.prepareModels();
        ModelsMoarSigns.registerModels();
    }

    @Override
    public void preInit() {
        SignRegistry.clear();
    }
}

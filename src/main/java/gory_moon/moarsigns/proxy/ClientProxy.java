package gory_moon.moarsigns.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.client.renderers.MoarSignRenderer;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy {

    @Override
    public void initRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoarSign.class, new MoarSignRenderer());
    }

    @Override
    public void readSigns() {
        ResourceLocation location = new ResourceLocation("moarsigns", "info/signs.items");
        Resource resource = Minecraft.getMinecraft().getResourceManager().getResource(location);
        MoarSigns.instance.loadFile(resource.getInputStream());
    }

}

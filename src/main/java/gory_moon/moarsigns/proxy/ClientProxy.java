package gory_moon.moarsigns.proxy;

import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.BlockMoarSignStanding;
import gory_moon.moarsigns.blocks.BlockMoarSignWall;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.ModelsMoarSigns;
import gory_moon.moarsigns.client.layer.LayerMoarSign;
import gory_moon.moarsigns.client.renderers.MoarSignRenderer;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.items.MoarSignsItemMeshDefenition;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoarSign.class, new MoarSignRenderer());
        new IntegrationHandler().preSetupSigns();
    }

    @Override
    public void registerModels() {
        ModelsMoarSigns.prepareModels();
        ModelsMoarSigns.registerModels();
        SignRegistry.clear();

        //ModelLoader.addCustomBuiltInBlocks(Blocks.signStandingMetal, Blocks.signStandingWood, Blocks.signWallMetal, Blocks.signWallWood);
        ModelLoader.setCustomMeshDefinition(ModItems.SIGN, new MoarSignsItemMeshDefenition());

        ModelLoader.setCustomStateMapper(Blocks.signStandingWood, (new StateMap.Builder()).ignore(new IProperty[] {BlockMoarSignStanding.ROTATION}).build());
        ModelLoader.setCustomStateMapper(Blocks.signStandingMetal, (new StateMap.Builder()).ignore(new IProperty[] {BlockMoarSignStanding.ROTATION}).build());
        ModelLoader.setCustomStateMapper(Blocks.signWallWood, (new StateMap.Builder()).ignore(new IProperty[] {BlockMoarSignWall.ROTATION, BlockMoarSignWall.FACING}).build());
        ModelLoader.setCustomStateMapper(Blocks.signWallMetal, (new StateMap.Builder()).ignore(new IProperty[] {BlockMoarSignWall.ROTATION, BlockMoarSignWall.FACING}).build());
    }

    @Override
    public void init() {
        LayerMoarSign layer = new LayerMoarSign();
        ((RenderPlayer) Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default")).addLayer(layer);
        ((RenderPlayer) Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim")).addLayer(layer);
    }
}

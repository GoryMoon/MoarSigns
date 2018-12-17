package gory_moon.moarsigns.integration.tumat;

import de.canitzp.tumat.InfoUtil;
import de.canitzp.tumat.api.IWorldRenderer;
import de.canitzp.tumat.api.TUMATApi;
import de.canitzp.tumat.api.TooltipComponent;
import de.canitzp.tumat.api.components.TextComponent;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TumatIntegration implements IWorldRenderer {

    public static void register() {
        TUMATApi.registerRenderComponent(TumatIntegration.class);
    }

    @Override
    public TooltipComponent renderTileEntity(WorldClient world, EntityPlayerSP player, TileEntity tileEntity, EnumFacing side, TooltipComponent component, boolean shouldCalculate) {
        if (tileEntity instanceof TileEntityMoarSign) {
            SignInfo info = BlockMoarSign.getSignInfo(world, tileEntity.getPos());
            if (info != null) {
                String s = InfoUtil.getItemName(ModItems.SIGN.createMoarItemStack(info.material.path + info.itemName, info.isMetal));
                component.setName(new TextComponent(s));
            }
        }
        return component;
    }
}

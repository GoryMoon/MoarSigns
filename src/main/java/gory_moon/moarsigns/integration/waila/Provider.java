package gory_moon.moarsigns.integration.waila;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.lib.Reference;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.Utils;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class Provider implements IWailaDataProvider {

    public static void callbackRegister(IWailaRegistrar registrar) {
        Provider provider = new Provider();

        registrar.registerStackProvider(provider, BlockMoarSign.class);
        registrar.registerBodyProvider(provider, BlockMoarSign.class);
        registrar.addConfig(Reference.NAME, "showOrigin", Localization.INTEGRATION.WAILA.SHOW_ORIGIN.translate());
        registrar.addConfig(Reference.NAME, "showMaterial", Localization.INTEGRATION.WAILA.SHOW_MATERIAL.translate(), false);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te != null && ((TileEntityMoarSign) te).texture_name != null) {
            return ModItems.SIGN.createMoarItemStack(((TileEntityMoarSign) te).texture_name, ((TileEntityMoarSign) te).isMetal);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (itemStack != null) {
            SignInfo info = ItemMoarSign.getInfo(itemStack.getTagCompound());
            if (info != null) {
                String modName = info.activateTag.equals(SignRegistry.ALWAYS_ACTIVE_TAG) ? "Minecraft" : info.activateTag;
                if (config.getConfig("showOrigin"))
                    currenttip.add(Localization.ITEM.SIGN.MATERIAL_ORIGIN.translate(Colors.WHITE + Utils.getModName(modName)));
                if (config.getConfig("showMaterial"))
                    currenttip.add(Localization.ITEM.SIGN.MATERIAL.translate(Colors.WHITE + info.material.materialName));
            } else {
                currenttip.add(Colors.RED + Localization.ITEM.SIGN.ERROR.translate());
            }
        }
        return currenttip;
    }

}

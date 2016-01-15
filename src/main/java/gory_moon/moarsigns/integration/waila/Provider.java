package gory_moon.moarsigns.integration.waila;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.Utils;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class Provider implements IWailaDataProvider {

    public static void callbackRegister(IWailaRegistrar registrar) {
        Provider provider = new Provider();

        registrar.registerStackProvider(provider, BlockMoarSign.class);
        registrar.registerBodyProvider(provider, BlockMoarSign.class);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te != null) {
            return ModItems.sign.createMoarItemStack(((TileEntityMoarSign) te).texture_name, ((TileEntityMoarSign) te).isMetal);
        }
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (itemStack != null) {
            SignInfo info = ItemMoarSign.getInfo(itemStack.getTagCompound());
            String modName = info.activateTag.equals(SignRegistry.ALWAYS_ACTIVE_TAG) ? "Minecraft" : info.activateTag;
            currenttip.add(Localization.ITEM.SIGN.MATERIAL_ORIGIN.translate(Colors.WHITE + Utils.getModName(modName)));
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return null;
    }
}

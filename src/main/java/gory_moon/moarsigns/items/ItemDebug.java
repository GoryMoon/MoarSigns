package gory_moon.moarsigns.items;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.lib.Info;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemDebug extends Item {

    public ItemDebug() {
        setCreativeTab(MoarSigns.instance.tabMS);
        setUnlocalizedName("moarsign.debug");
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(Info.TEXTURE_LOCATION + ":" + "debug_item");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {

            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null) {
                FMLNetworkHandler.openGui(player, MoarSigns.instance, 1, world, 0, 0, 0);
                return stack;
            }
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitX, float hitY, float hitZ, float par10) {
        if (world.isRemote) return false;

        FMLNetworkHandler.openGui(player, MoarSigns.instance, 0, world, x, y, z);

        return true;
    }
}

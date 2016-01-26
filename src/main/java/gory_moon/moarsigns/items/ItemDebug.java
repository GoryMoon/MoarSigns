package gory_moon.moarsigns.items;

import gory_moon.moarsigns.MoarSigns;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class ItemDebug extends Item {

    public ItemDebug() {
        setUnlocalizedName("moarsigns.debug");
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
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return false;

        FMLNetworkHandler.openGui(player, MoarSigns.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }
}

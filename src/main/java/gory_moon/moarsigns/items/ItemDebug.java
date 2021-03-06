package gory_moon.moarsigns.items;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.lib.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class ItemDebug extends Item {

    public ItemDebug() {
        setRegistryName(Constants.DEBUG_ITEM_KEY);
        setTranslationKey("moarsigns.debug");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (!world.isRemote) {

            RayTraceResult movingobjectposition = this.rayTrace(world, player, true);

            if (movingobjectposition == null || movingobjectposition.typeOfHit == RayTraceResult.Type.MISS) {
                FMLNetworkHandler.openGui(player, MoarSigns.instance, 1, world, 0, 0, 0);
                return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
            }
        }
        return ActionResult.newResult(EnumActionResult.PASS, itemStack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return EnumActionResult.PASS;

        FMLNetworkHandler.openGui(player, MoarSigns.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return EnumActionResult.SUCCESS;
    }

}

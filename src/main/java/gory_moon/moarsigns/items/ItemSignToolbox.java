package gory_moon.moarsigns.items;

import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSignToolbox extends Item {

    public ItemSignToolbox() {
        setUnlocalizedName("moarsign.signtoolbox");
        setCreativeTab(MoarSignsCreativeTab.tabMS);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && (world.getBlock(x, y, z) instanceof BlockMoarSign)) {
            RotationHandler.rotate((TileEntityMoarSign) world.getTileEntity(x, y, z));
        }
        return false;
    }
}

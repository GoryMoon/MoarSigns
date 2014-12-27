package gory_moon.moarsigns.items;

import cpw.mods.fml.client.FMLClientHandler;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.blocks.BlockMoarSign;
import gory_moon.moarsigns.client.interfaces.GuiColor;
import gory_moon.moarsigns.lib.ToolBoxModes;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Localization;
import gory_moon.moarsigns.util.RotationHandler;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class ItemSignToolbox extends Item {

    public ItemSignToolbox() {
        setUnlocalizedName("moarsign.signtoolbox");
        setCreativeTab(MoarSignsCreativeTab.tabMS);
        setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
       if (!world.isRemote) {
           MovingObjectPosition.MovingObjectType hit = FMLClientHandler.instance().getClient().objectMouseOver.typeOfHit;

           if (hit == MovingObjectPosition.MovingObjectType.MISS && player.isSneaking()) {
               return rotateModes(stack);
           }
       }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote){
            if (player.isSneaking()) {

            } else {
                if (world.getBlock(x, y, z) instanceof BlockMoarSign) {
                    RotationHandler.rotate((TileEntityMoarSign) world.getTileEntity(x, y, z));
                }
            }
        }
        return false;
    }

    private ItemStack rotateModes(ItemStack stack) {
        int mode = stack.getItemDamage();
        mode = mode + 1 >= 4 ? 0: mode + 1;
        stack.setItemDamage(mode);
        MoarSigns.logger.info(stack);
        return stack;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() > 3) stack.setItemDamage(0);
        return super.getUnlocalizedName(stack) + "." + ToolBoxModes.values()[stack.getItemDamage()].toString();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean extraInfo) {
        list.add(GuiColor.WHITE + Localization.ITEM.SIGNTOOLBOX.CHANGE.translate(GuiColor.LIGHTGRAY + "[" + GameSettings.getKeyDisplayString(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode()) + "]" + GuiColor.WHITE));
    }
}

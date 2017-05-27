package gory_moon.moarsigns.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class SignSpecialProperty {

    /**
     * Updates when the tileEntity is updated
     */
    public void onUpdate() {
        // Add update code
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block).
     *
     * @param pos        The position of the sign
     * @param world      The world the sign is in
     * @param blockState The blockstate of the block
     * @param entity     The entity colliding with the sign
     */
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState blockState, Entity entity) {
        // Add collision code
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     *
     * @param blockState The blockstate of the block
     * @param world      The world the sign is in
     * @param pos        The position of the sign
     * @param random     An instance of Random
     */
    public void randomDisplayTick(IBlockState blockState, World world, BlockPos pos, Random random) {
        // Add display code
    }

    /**
     * Called when a player is right clicking the sign
     *
     * @param world      The world the sign is in
     * @param pos        The position of the sign
     * @param blockState The blockstate of the block
     * @param player     The player that right clicked the sign
     * @param hand       The hand that is clicking the sign
     * @param heldItem   The currently held item
     * @param side       The side that was right clicked
     * @param hitX       The x location on the side
     * @param hitY       The y location on the side
     * @param hitZ       The z location on the side
     * @return False if nothing happened otherwise True
     */
    public boolean onRightClick(World world, BlockPos pos, IBlockState blockState, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        // Add code to handle right click
        return false;
    }


}

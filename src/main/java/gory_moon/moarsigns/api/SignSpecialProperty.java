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
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block).
     * @param pos   The position of the sign
     * @param world  The world the sign is in
     * @param state
     * @param entity The enity colliding with the sign
     */
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     * @param blockState
     * @param world  The world the sign is in
     * @param pos   The position of the sign
     * @param random An instance of Random
     */
    public void randomDisplayTick(IBlockState blockState, World world, BlockPos pos, Random random) {
    }

    /**
     * Called when a player is right clicking the sign
     *  @param world  The world the sign is in
     * @param pos   The position of the sign
     * @param state
     * @param player The player that right clicked the sign
     * @param hand
     * @param heldItem
     * @param side   The side that was right clicked
     * @param hitX   The x location on the side
     * @param hitY   The y location on the side
     * @param hitZ   The z location on the side      @return False if nothing happened otherwise True
     * */
    public boolean onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        return false;
    }


}

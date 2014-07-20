package gory_moon.moarsigns.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
     *
     * @param world  The world the sign is in
     * @param x      The x coord of the sign
     * @param y      The y coord of the sign
     * @param z      The z coord of the sign
     * @param entity The enity colliding with the sign
     */
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     *
     * @param world  The world the sign is in
     * @param x      The x coord of the sign
     * @param y      The y coord of the sign
     * @param z      The z coord of the sign
     * @param random An instance of Random
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
    }

    /**
     * Called when a player is right clicking the sign
     *
     * @param world  The world the sign is in
     * @param x      The x coord of the sign
     * @param y      The y coord of the sign
     * @param z      The z coord of the sign
     * @param player The player that right clicked the sign
     * @param side   The side that was right clicked
     * @param hitX   The x location on the side
     * @param hitY   The y location on the side
     * @param hitZ   The z location on the side
     * @return False if nothing happened otherwise True
     */
    public boolean onRightClick(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return false;
    }


}

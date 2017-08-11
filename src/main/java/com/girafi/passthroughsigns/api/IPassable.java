package com.girafi.passthroughsigns.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPassable {

    /**
     * Return true if this block/entity should be passable.
     *
     * @param world the world
     * @param pos   the position of the block/entity
     * @param type  the EnumPassableType for the block/entity
     * @return whether the block/entity can be passed or not
     */
    boolean canBePassed(World world, BlockPos pos, EnumPassableType type);

    enum EnumPassableType {

        /**
         * For blocks similiar to wall signs & banners.
         */
        WALL_BLOCK,

        /**
         * For entities similar to ingot frames, paintings etc.
         */
        HANGING_ENTITY
    }
}
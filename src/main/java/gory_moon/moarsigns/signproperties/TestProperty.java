package gory_moon.moarsigns.signproperties;

import gory_moon.moarsigns.api.SignSpecialProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TestProperty extends SignSpecialProperty {

    //Test effects

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 5000, 5));
        }
    }

    @Override
    public void randomDisplayTick(IBlockState blockState, World world, BlockPos pos, Random random) {
        if (world.isRemote) {
            float pX = pos.getX() + random.nextFloat();
            float pY = pos.getY() + random.nextFloat();
            float pZ = pos.getZ() + random.nextFloat();
            float mX = -0.5F + random.nextFloat();
            float mY = -0.5F + random.nextFloat();
            float mZ = -0.5F + random.nextFloat();

            world.spawnParticle(EnumParticleTypes.SPELL_WITCH, pX, pY, pZ, mX, mY, mZ);
        }

    }
}

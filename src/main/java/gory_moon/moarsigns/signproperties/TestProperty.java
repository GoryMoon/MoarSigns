package gory_moon.moarsigns.signproperties;

import gory_moon.moarsigns.api.SignSpecialProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.Random;

public class TestProperty extends SignSpecialProperty {

    //Test effects

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 5000, 5));
        }
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (world.isRemote) {
            float pX = x + random.nextFloat();
            float pY = y + random.nextFloat();
            float pZ = z + random.nextFloat();
            float mX = -0.5F + random.nextFloat();
            float mY = -0.5F + random.nextFloat();
            float mZ = -0.5F + random.nextFloat();

            world.spawnParticle("witchMagic", pX, pY, pZ, mX, mY, mZ);
        }

    }
}

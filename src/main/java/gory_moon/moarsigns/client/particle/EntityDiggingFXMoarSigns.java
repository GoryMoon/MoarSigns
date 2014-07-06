package gory_moon.moarsigns.client.particle;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityDiggingFXMoarSigns extends EntityDiggingFX {
    public EntityDiggingFXMoarSigns(World world, double particleX, double particleY, double particleZ, double motionX, double motionY, double motionZ, int x, int y, int z, Block block, int meta, int side) {
        super(world, particleX, particleY, particleZ, motionX, motionY, motionZ, block, meta, side);

        TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(x, y, z);
        MaterialInfo info = SignRegistry.get(tileEntity.texture_name).material;

        Block Mblock = Block.getBlockFromItem(info.material.getItem());

        if (Mblock != null && !Mblock.getUnlocalizedName().equals("tile.ForgeFiller")) {
            setParticleIcon(Mblock.getIcon(3, tileEntity.getBlockMetadata()));
        } else {
            Item item = info.material.getItem();
            if (item != null) {
                setParticleIcon(item.getIcon(info.material, 0));
            } else {
                setParticleIcon(tileEntity.isMetal ? Blocks.iron_block.getIcon(side, meta) : Blocks.planks.getIcon(side, meta));
            }
        }
    }
}

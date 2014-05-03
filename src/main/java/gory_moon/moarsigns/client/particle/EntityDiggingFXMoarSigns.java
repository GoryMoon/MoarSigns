package gory_moon.moarsigns.client.particle;

import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityDiggingFXMoarSigns extends EntityDiggingFX {
    public EntityDiggingFXMoarSigns(World world, double particleX, double particleY, double particleZ, double motionX, double motionY, double motionZ, int x, int y, int z, Block block, int meta, int side) {
        super(world, particleX, particleY, particleZ, motionX, motionY, motionZ, block, meta, side);
        TileEntityMoarSign sign = (TileEntityMoarSign) world.getBlockTileEntity(x, y, z);
        int Mid = sign.materialId;
        int Mmeta = sign.materialMeta;
        Block Mblock = Block.blocksList[Mid];

        if (Mblock != null && !Mblock.getUnlocalizedName().equals("tile.ForgeFiller")) {
            setParticleIcon(Mblock.getIcon(3, Mmeta));
        } else {
            Item item = Item.itemsList[Mid];
            if (item != null) {
                setParticleIcon(item.getIcon(new ItemStack(item, 0, Mmeta), 0));
            } else {
                setParticleIcon(sign.isMetal ? Block.blockIron.getIcon(side, meta): Block.planks.getIcon(side, meta));
            }
        }
    }
}

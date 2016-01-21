package gory_moon.moarsigns.client.particle;
/*
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDiggingFXMoarSigns extends EntityDiggingFX {
    public EntityDiggingFXMoarSigns(World world, double particleX, double particleY, double particleZ, double motionX, double motionY, double motionZ, BlockPos pos, IBlockState state) {
        super(world, particleX, particleY, particleZ, motionX, motionY, motionZ, state);

        TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(pos);
        if (tileEntity != null && tileEntity.texture_name != null) {
            SignInfo info = SignRegistry.get(tileEntity.texture_name);

            if (info != null && info.material != null && info.material.material.getItem() != null) {
                Block Mblock = Block.getBlockFromItem(info.isMetal ? info.material.materialBlock.getItem() : info.material.material.getItem());

                if (Mblock != null && !Mblock.getUnlocalizedName().equals("tile.air") && !Mblock.getUnlocalizedName().equals("tile.ForgeFiller")) {
                    setParticleIcon(Mblock.getIcon(3, info.isMetal ? info.material.materialBlock.getItemDamage() : info.material.material.getItemDamage()));
                } else {
                    Item item = info.material.material.getItem();
                    if (item != null) {
                        setParticleIcon(item.getIconFromDamage(info.material.material.getItemDamage()));
                    } else {
                        setParticleIcon(tileEntity.isMetal ? Blocks.iron_block.getIcon(side, meta) : Blocks.planks.getIcon(side, meta));
                    }
                }
            }
        }
    }
}
*/
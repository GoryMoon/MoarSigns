package gory_moon.moarsigns.client.particle;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDiggingFXMoarSigns extends ParticleDigging {
    public EntityDiggingFXMoarSigns(World world, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, BlockPos pos, IBlockState state) {
        super(world, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, state);

        TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(pos);
        if (tileEntity != null && tileEntity.texture_name != null) {
            SignInfo info = SignRegistry.get(tileEntity.texture_name);

            if (info != null && info.material != null && info.material.material.getItem() != null && (!info.isMetal || info.material.materialBlock != null)) {
                Block Mblock = Block.getBlockFromItem(info.isMetal ? info.material.materialBlock.getItem() : info.material.material.getItem());

                if (Mblock != null && !Mblock.getUnlocalizedName().equals("tile.air") && !Mblock.getUnlocalizedName().equals("tile.ForgeFiller")) {
                    IBlockState state1 = ((ItemBlock) (info.isMetal ? info.material.materialBlock.getItem() : info.material.material.getItem())).block.getStateFromMeta(info.isMetal ? info.material.materialBlock.getItemDamage() : info.material.material.getItemDamage());
                    setParticleTexture(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state1));
                } else {
                    Item item = info.material.material.getItem();
                    if (item != null) {
                        setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item, info.material.material.getItemDamage()));
                    } else {
                        IBlockState state1 = ((info.isMetal ? Blocks.IRON_BLOCK : Blocks.PLANKS)).getStateFromMeta(0);
                        setParticleTexture(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state1));
                    }
                }
            }
        }
    }
}

package gory_moon.moarsigns.items;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.network.ServerPacketHandler;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Signs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemMoarSign extends Item {

    public ItemMoarSign(int par1) {
        super(par1);
        maxStackSize = 16;
        setCreativeTab(MoarSigns.instance.tabMS);
        setUnlocalizedName("moarsign");
        hasSubtypes = true;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + getTextureFromNBT(stack.getTagCompound());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerIcons(IconRegister register) {
        ArrayList<Signs> signs = (ArrayList<Signs>) MoarSigns.instance.getTempWoodSigns().clone();
        signs.addAll((ArrayList<Signs>) MoarSigns.instance.getTempMetalSigns().clone());

        for (Signs s : signs) {
            for (Signs.Material material : s.material) {
                String path = material.path;
                String loc = s.isMetal ? "metal/" : "wood/";
                Icon icon = register.registerIcon(Info.TEXTURE_LOCATION + ":" + loc + (path.equals("") ? "" : path.replace("\\", "/")) + s.itemTexture);
                MoarSigns.icons.put((path.equals("") ? "" : path) + s.signName, icon);
            }
        }
    }

    @Override
    public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
        getSubItemStacks(list);
    }

    @SuppressWarnings("unchecked")
    public void getSubItemStacks(List list) {
        ArrayList<Signs> signs = (ArrayList<Signs>) MoarSigns.instance.getSignsWood().clone();
        signs.addAll((ArrayList<Signs>) MoarSigns.instance.getSignsMetal().clone());

        for (Signs s : signs) {
            String path = s.material[s.activeMaterialIndex].path;
            list.add(createMoarItemStack((path.equals("") ? "" : path) + s.signName, s.isMetal));
        }
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        Signs sign = getSignFromNBT(stack.getTagCompound());
        return sign != null ? sign.itemName : "MoarSign";
    }

    @Override
    public Icon getIcon(ItemStack stack, int pass) {
        Signs sign = getSignFromNBT(stack.getTagCompound());
        if (sign == null) return MoarSigns.icons.get("oak_sign");

        String path = sign.material[sign.activeMaterialIndex].path;
        return MoarSigns.icons.get((path.equals("") ? "" : path) + sign.signName);
    }

    @Override
    public Icon getIconIndex(ItemStack stack) {
        return getIcon(stack, 0);
    }

    public ItemStack createMoarItemStack(String signName, boolean isMetal) {
        ItemStack itemStack = new ItemStack(this.itemID, 1, (isMetal ? 1 : 0));
        NBTTagCompound compound = new NBTTagCompound("tag");
        compound.setString("SignTexture", signName.replace("\\", "/"));
        itemStack.setTagCompound(compound);
        return itemStack;
    }

    @SuppressWarnings("unchecked")
    public Signs getSignFromNBT(NBTTagCompound nbt) {
        ArrayList<Signs> signs = (ArrayList<Signs>) MoarSigns.instance.getSignsWood().clone();
        signs.addAll((ArrayList<Signs>) MoarSigns.instance.getSignsMetal().clone());
        String texture = getTextureFromNBTFull(nbt);

        for (Signs s : signs) {
            if ((s.material[s.activeMaterialIndex].path.replace("\\", "/") + s.signName).equals(texture)) {
                return s;
            }
        }
        return null;
    }

    public String getTextureFromNBTFull(NBTTagCompound compound) {
        String texture = compound.hasKey("SignTexture") ? compound.getString("SignTexture") : "";
        return texture;
    }

    public String getTextureFromNBT(NBTTagCompound compound) {
        String texture = compound.hasKey("SignTexture") ? compound.getString("SignTexture") : "";
        if (texture.contains("\\")) texture = texture.split("\\\\")[1];
        if (texture.contains("/")) texture = texture.split("/")[1];
        return texture;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (side == 0 || !world.getBlockMaterial(x, y, z).isSolid()) {
            return false;
        } else {

            switch (side) {
                case 1:
                    y++;
                    break;
                case 2:
                    z--;
                    break;
                case 3:
                    z++;
                    break;
                case 4:
                    x--;
                    break;
                case 5:
                    x++;
                    break;
            }

            if (!player.canPlayerEdit(x, y, z, side, stack) || !Blocks.signStandingWood.canPlaceBlockAt(world, x, y, z) || world.isRemote) {
                return false;
            } else {
                if (side == 1) {
                    int rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    if (stack.getItemDamage() == 0)
                        world.setBlock(x, y, z, Blocks.signStandingWood.blockID, rotation, 3);
                    else if (stack.getItemDamage() == 1)
                        world.setBlock(x, y, z, Blocks.signStandingMetal.blockID, rotation, 3);

                } else {
                    if (stack.getItemDamage() == 0) world.setBlock(x, y, z, Blocks.signWallWood.blockID, side, 3);
                    else if (stack.getItemDamage() == 1) world.setBlock(x, y, z, Blocks.signWallMetal.blockID, side, 3);
                }

                if (!player.capabilities.isCreativeMode) --stack.stackSize;
                TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getBlockTileEntity(x, y, z);

                if (tileEntity != null) {
                    tileEntity.setResourceLocation(getTextureFromNBTFull(stack.getTagCompound()));
                    Signs sign = Items.sign.getSignFromNBT(stack.getTagCompound());
                    if (sign == null) return false;

                    tileEntity.isMetal = sign.isMetal;
                    tileEntity.materialId = sign.material[sign.activeMaterialIndex].matId;
                    tileEntity.materialMeta = sign.material[sign.activeMaterialIndex].matMeta;

                    ServerPacketHandler.openMoarSignGui(player, tileEntity);
                }

                return true;
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean extraInfo) {
        //list.add("Sign texture: " + getTextureFromNBT(stack.getTagCompound()));
    }
}

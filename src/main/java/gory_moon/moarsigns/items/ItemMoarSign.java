package gory_moon.moarsigns.items;

import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.MoarSignsCreativeTab;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.network.message.MessageSignMainInfo;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemMoarSign extends Item {

    public ItemMoarSign() {
        maxStackSize = 16;
        setCreativeTab(MoarSignsCreativeTab.tabMS);
        setUnlocalizedName("moarsign");
        hasSubtypes = true;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        SignInfo info = SignRegistry.get(getTextureFromNBTFull(stack.getTagCompound()));
        if (info == null) return super.getUnlocalizedName() + ".sign.dummy";
        return super.getUnlocalizedName() + ".sign." + (info.material.path.equals("") ? "" : info.material.path.replace("/", "") + ".") + getTextureFromNBT(stack.getTagCompound());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerIcons(IIconRegister register) {
        List<SignInfo> signRegistry = SignRegistry.getSignRegistry();

        for (SignInfo info : signRegistry) {
            String path = info.material.path;
            String loc = info.isMetal ? "metal/" : "wood/";
            IIcon icon = register.registerIcon(info.modId.toLowerCase() + ":" + loc + (path.equals("") ? "" : path.replace("\\", "/")) + info.itemName);
            MoarSigns.icons.put((path.equals("") ? "" : path) + info.itemName, icon);
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        getSubItemStacks(list);
    }

    @SuppressWarnings("unchecked")
    public void getSubItemStacks(List list) {
        List<SignInfo> signRegistry = SignRegistry.getActivatedSignRegistry();

        for (SignInfo info : signRegistry) {
            String path = info.material.path;
            list.add(createMoarItemStack(path + info.itemName, info.isMetal));
        }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        SignInfo info = SignRegistry.get(getTextureFromNBTFull(stack.getTagCompound()));
        if (info == null) return MoarSigns.icons.get("oak_sign");

        String path = info.material.path;
        return MoarSigns.icons.get((path.equals("") ? "" : path) + info.itemName);
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return getIcon(stack, 0);
    }

    public ItemStack createMoarItemStack(String signName, boolean isMetal) {
        ItemStack itemStack = new ItemStack(this, 1, (isMetal ? 1 : 0));
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("SignTexture", signName.replace("\\", "/"));
        itemStack.setTagCompound(compound);
        return itemStack;
    }

    public String getTextureFromNBTFull(NBTTagCompound compound) {
        return compound != null && compound.hasKey("SignTexture") ? compound.getString("SignTexture") : "";
    }

    public String getTextureFromNBT(NBTTagCompound compound) {
        String texture = compound != null && compound.hasKey("SignTexture") ? compound.getString("SignTexture") : "";
        if (texture.contains("\\")) texture = texture.split("\\\\")[1];
        if (texture.contains("/")) texture = texture.split("/")[1];
        return texture;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.getBlock(x, y, z).getMaterial().isSolid()) {
            return false;
        } else {

            switch (side) {
                case 0:
                    y--;
                    break;
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
                if (side == 1 && !player.isSneaking()) {
                    int rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    if (stack.getItemDamage() == 0) world.setBlock(x, y, z, Blocks.signStandingWood, rotation, 3);
                    else if (stack.getItemDamage() == 1) world.setBlock(x, y, z, Blocks.signStandingMetal, rotation, 3);

                } else {
                    if (side == 0 || side == 1) {
                        int rotation = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                        side += (rotation << 1);
                        side += 8;
                    }
                    if (stack.getItemDamage() == 0) world.setBlock(x, y, z, Blocks.signWallWood, side, 3);
                    else if (stack.getItemDamage() == 1) world.setBlock(x, y, z, Blocks.signWallMetal, side, 3);
                }

                if (!player.capabilities.isCreativeMode) --stack.stackSize;
                TileEntityMoarSign tileEntity = (TileEntityMoarSign) world.getTileEntity(x, y, z);

                if (tileEntity != null) {
                    String texture = getTextureFromNBTFull(stack.getTagCompound());

                    SignInfo info = SignRegistry.get(texture);

                    if (info == null) return false;

                    tileEntity.setResourceLocation(texture);
                    tileEntity.isMetal = info.isMetal;
                    tileEntity.func_145912_a(player);

                    PacketHandler.INSTANCE.sendTo(new MessageSignOpenGui(tileEntity), (EntityPlayerMP) player);
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

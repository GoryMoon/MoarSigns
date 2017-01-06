package gory_moon.moarsigns.client;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.items.ItemMoarSign;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class MoarSignsItemMeshDefenition implements ItemMeshDefinition {
    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        SignInfo info = ItemMoarSign.getInfo(stack.getTagCompound());
        if (info == null)
            return null;
        String resourcePath = info.material.path + info.itemName;
        return new ModelResourceLocation(info.modId + ":" + "signs/" + resourcePath, "inventory");
    }
}

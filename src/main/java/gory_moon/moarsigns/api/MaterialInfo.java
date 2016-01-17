package gory_moon.moarsigns.api;

import net.minecraft.item.ItemStack;

public class MaterialInfo {

    public String materialName;

    public String path;
    public boolean gotNugget;

    public ItemStack material;
    public ItemStack materialBlock;

    public MaterialInfo(String materialName, String path, boolean gotNugget, ItemStack material, ItemStack materialBlock) {
        this.materialName = materialName;
        this.path = path;
        this.gotNugget = gotNugget;
        this.material = material;
        this.materialBlock = materialBlock;
    }


    /**
     * Only to be used when making recipes
     *
     * @param materialName Name of material
     */
    public MaterialInfo(String materialName) {
        this(materialName, null, false, null, null);
    }

}

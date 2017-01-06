package gory_moon.moarsigns.api;

import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public class MaterialRegistry {

    public static HashMap<String, HashSet<MaterialInfo>> materialRegistry = new HashMap<String, HashSet<MaterialInfo>>();

    /**
     * Registers the material used for the signs
     *
     * @param materialName      The name of the material that it's registered with
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @return The {@link gory_moon.moarsigns.api.MaterialInfo} that is registered
     */
    public static MaterialInfo register(String materialName, String path, boolean gotNugget, ItemStack materialItemStack) {
        return register(new MaterialInfo(materialName, path, gotNugget, materialItemStack, null));
    }

    /**
     * Registers the material used for the signs
     *
     * @param materialName      The name of the material that it's registered with
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @return The {@link gory_moon.moarsigns.api.MaterialInfo} that is registered
     */
    public static MaterialInfo register(String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock) {
        return register(new MaterialInfo(materialName, path, gotNugget, materialItemStack, materialBlock));
    }

    /**
     * Registers the material used for the signs
     *
     * @param info The {@link gory_moon.moarsigns.api.MaterialInfo} that should be registered
     * @return The {@link gory_moon.moarsigns.api.MaterialInfo} that is registered
     */
    public static MaterialInfo register(MaterialInfo info) {
        if (!materialRegistry.containsKey(info.materialName)) {
            materialRegistry.put(info.materialName, Sets.newHashSet(info));
        } else {
            materialRegistry.get(info.materialName).add(info);
        }
        return info;
    }

    public static HashSet<MaterialInfo> get(String materialName) {
        return materialRegistry.containsKey(materialName) ? materialRegistry.get(materialName) : null;
    }

    public static boolean contains(MaterialInfo info) {
        return materialRegistry.containsKey(info.materialName) && materialRegistry.get(info.materialName).contains(info);
    }

    public static MaterialInfo get(String materialName, String materialPath) {
        for (MaterialInfo info : get(materialName)) {
            if (info.path.equals(materialPath))
                return info;
        }
        return null;
    }
}

package gory_moon.moarsigns.api;

import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Set;

public class MaterialRegistry {

    public static HashMap<String, Set<MaterialInfo>> materialRegistry = new HashMap<String, Set<MaterialInfo>>();

    public static MaterialInfo register(String materialName, String path, boolean gotNugget, ItemStack stack) {
        return register(new MaterialInfo(materialName, path, gotNugget, stack));
    }

    public static MaterialInfo register(MaterialInfo info) {
        if (!materialRegistry.containsKey(info.materialName)) {
            materialRegistry.put(info.materialName, Sets.newHashSet(info));
        } else {
            materialRegistry.get(info.materialName).add(info);
        }
        return info;
    }

    public static Set<MaterialInfo> get(String materialName) {
        return materialRegistry.containsKey(materialName) ? materialRegistry.get(materialName) : null;
    }

    public static boolean contains(MaterialInfo info) {
        return materialRegistry.containsKey(info.materialName) && materialRegistry.get(info.materialName).contains(info);
    }

    public static MaterialInfo get(String materialName, String materialPath) {
        for (MaterialInfo info : get(materialName)) {
            if (info.path.equals(materialPath)) return info;
        }
        return null;
    }
}

package gory_moon.moarsigns.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SignRegistry {

    private static List<SignInfo> signRegistry = Lists.newArrayList();

    public static SignInfo register(String itemName, ISignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack stack)
    {
        MaterialInfo info = MaterialRegistry.register(materialName, path, gotNugget, stack);
        return register(itemName, property, info);
    }

    public static SignInfo register(String itemName, ISignSpecialProperty property, MaterialInfo material)
    {
        if (!MaterialRegistry.contains(material))
            MaterialRegistry.register(material);

        SignInfo signInfo = new SignInfo(itemName, material, property);
        signRegistry.add(signInfo);
        return signInfo;
    }

    public static SignInfo get(String s)
    {
        for (SignInfo info : signRegistry) {
            if ((info.material.path.replace("\\", "/") + info.itemName).equals(s)) {
                return info;
            }
        }
        return null;
    }

    public static List<SignInfo> getSignRegistry() {
        return signRegistry;
    }
}

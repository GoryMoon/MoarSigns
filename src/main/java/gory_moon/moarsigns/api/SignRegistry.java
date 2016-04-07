package gory_moon.moarsigns.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.*;

public class SignRegistry {

    public static final String ALWAYS_ACTIVE_TAG = "activeALL";
    private static ArrayList<SignInfo> signRegistry;
    private static ArrayList<SignInfo> activatedSignRegistry;
    private static HashMap<String, Boolean> activeTags;

    static {
        activeTags = Maps.newHashMap();
        signRegistry = Lists.newArrayList();
        activatedSignRegistry = Lists.newArrayList();

        activateTag(ALWAYS_ACTIVE_TAG);
    }

    /**
     * Registers a sign
     * <br>
     * Used by metal signs that need special blocks as material particle icons
     * <br>
     * The Sign is activate by default
     * <br><br>
     * <p/>
     * The sign item texture needs to go into the "@MODID@/textures/item/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     * <br><br>
     * <p/>
     * The sign texture needs  to go into the "@MODID@/textures/signs/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     *
     * @param itemName          The name of the texture for the sign and the item texture
     * @param property          The special property that the sign have
     * @param materialName      The name of the material
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @param modId             The modId that registers the sign, used when getting the textures.
     * @return returns the {@link gory_moon.moarsigns.api.SignInfo} that is registered
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock, String modId) {
        MaterialInfo info = MaterialRegistry.register(materialName, path, gotNugget, materialItemStack, materialBlock);
        return register(itemName, property, info, modId, ALWAYS_ACTIVE_TAG);
    }

    /**
     * Registers a sign
     * <br>
     * Used by metal signs that need special blocks as material particle icons
     * <br>
     * The Sign needs to be activated with the tag that is given it with {@link #activateTag(String)}
     * <br>
     * Should always be registered but not activated if for example the material isn't available
     * <br><br>
     * <p/>
     * The sign item texture needs to go into the "@MODID@/textures/item/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     * <br><br>
     * <p/>
     * The sign texture needs  to go into the "@MODID@/textures/signs/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     *
     * @param itemName          The name of the texture for the sign and the item texture
     * @param property          The special property that the sign have
     * @param materialName      The name of the material
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @param materialBlock     An itemstack of the block for the material
     * @param modId             The modId that registers the sign, used when getting the textures.
     * @param activateTag       The tag to active the sign
     * @return returns the {@link gory_moon.moarsigns.api.SignInfo} that is registered
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock, String modId, String activateTag) {
        MaterialInfo info = MaterialRegistry.register(materialName, path, gotNugget, materialItemStack, materialBlock);
        return register(itemName, property, info, modId, activateTag);
    }

    /**
     * Registers a sign
     * The Sign is activate by default
     * <br><br>
     * <p/>
     * The sign item texture needs to go into the "@MODID@/textures/item/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     * <br><br>
     * <p/>
     * The sign texture needs  to go into the "@MODID@/textures/signs/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     *
     * @param itemName          The name of the texture for the sign and the item texture
     * @param property          The special property that the sign have
     * @param materialName      The name of the material
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @param modId             The modId that registers the sign, used when getting the textures.
     * @return returns the {@link gory_moon.moarsigns.api.SignInfo} that is registered
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, String modId) {
        MaterialInfo info = MaterialRegistry.register(materialName, path, gotNugget, materialItemStack);
        return register(itemName, property, info, modId, ALWAYS_ACTIVE_TAG);
    }

    /**
     * Registers a sign
     * <br>
     * The Sign needs to be activated with the tag that is given it with {@link #activateTag(String)}
     * <br>
     * Should always be registered but not activated if for example the material isn't available
     * <br><br>
     * <p/>
     * The sign item texture needs to go into the "@MODID@/textures/item/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     * <br><br>
     * <p/>
     * The sign texture needs  to go into the "@MODID@/textures/signs/" then depending on if it's metal or not
     * it needs too go into either "/metal" or "/wood"
     *
     * @param itemName          The name of the texture for the sign and the item texture
     * @param property          The special property that the sign have
     * @param materialName      The name of the material
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @param modId             The modId that registers the sign, used when getting the textures.
     * @param activateTag       The tag to active the sign
     * @return returns the {@link gory_moon.moarsigns.api.SignInfo} that is registered
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, String modId, String activateTag) {
        MaterialInfo info = MaterialRegistry.register(materialName, path, gotNugget, materialItemStack);
        return register(itemName, property, info, modId, activateTag);
    }

    private static SignInfo register(String itemName, SignSpecialProperty property, MaterialInfo material, String modId, String activateTag) {
        if (!MaterialRegistry.contains(material))
            MaterialRegistry.register(material);

        SignInfo signInfo = new SignInfo(itemName, material, property, modId, activateTag);
        signRegistry.add(signInfo);

        if (!activeTags.containsKey(activateTag)) {
            activeTags.put(activateTag, false);
        } else if (activeTags.get(activateTag)) {
            activatedSignRegistry.add(signInfo);
        }

        return signInfo;
    }

    /**
     * Activates all signs registered with the tag and all further signs register with that tag
     *
     * @param tag The tag to register
     */
    public static void activateTag(String tag) {
        activeTags.put(tag, true);

        for (SignInfo info : signRegistry) {
            if (info.activateTag.equals(tag)) {
                activatedSignRegistry.add(info);
            }
        }

    }

    /**
     * Deactivates all signs with the tag and won't register any further ones with that tag
     *
     * @param tag The tag to deactivate
     */
    @SuppressWarnings("unused")
    public static void decativateTag(String tag) {
        activeTags.put(tag, false);

        for (SignInfo info : activatedSignRegistry) {
            if (info.activateTag.equals(tag)) {
                activatedSignRegistry.remove(info);
            }
        }
    }

    /**
     * Gets the {@link gory_moon.moarsigns.api.SignInfo} of a sign composed by the path and the itemname
     *
     * @param s The string composed by the path and the itemname that is used for the search
     * @return The found {@link gory_moon.moarsigns.api.SignInfo} or if not found it returns null
     */
    public static SignInfo get(String s) {
        for (SignInfo info : signRegistry) {
            if ((info.material.path.replace("\\", "/") + info.itemName).equals(s)) {
                return info;
            }
        }
        return null;
    }


    /**
     * @param materials Materials to search with
     * @return A list of {@link gory_moon.moarsigns.api.SignInfo}
     */
    public static ArrayList<SignInfo> getSignInfoFromMaterials(HashSet<MaterialInfo> materials) {
        ArrayList<SignInfo> signInfos = Lists.newArrayList();

        for (SignInfo info : activatedSignRegistry) {
            for (MaterialInfo materialInfo : materials) {
                if (info.material.materialName.equals(materialInfo.materialName)) {
                    signInfos.add(info);
                    break;
                }
            }
        }

        return signInfos;
    }

    /**
     * Gets a clone of the register with all signs
     *
     * @return List of {@link gory_moon.moarsigns.api.SignInfo}
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<SignInfo> getSignRegistry() {
        return (ArrayList<SignInfo>) signRegistry.clone();
    }

    /**
     * Gets a clone of the current active signs
     *
     * @return List of {@link gory_moon.moarsigns.api.SignInfo}
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<SignInfo> getActivatedSignRegistry() {
        return (ArrayList<SignInfo>) activatedSignRegistry.clone();
    }

    public static ArrayList<String> getTextureLocations(boolean includeModID) {
        ArrayList<String> list = getTextureLocations(false, includeModID);
        list.addAll(getTextureLocations(true, includeModID));
        return list;
    }

    public static ArrayList<String> getTextureLocations(boolean isMetal, boolean includeModID) {
        ArrayList<String> list = Lists.newArrayList();
        for (SignInfo info: signRegistry) {
            if (info.isMetal == isMetal) {
                list.add((includeModID ? info.modId + ":": "") + "signs/" + (info.material.path.replace("/", "_") + info.itemName));
            }
        }
        return list;
    }

    /**
     * Gets a clone of the map of tags that are registered
     *
     * @return Map of tags with boolean values
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Boolean> getActiveTags() {
        return (HashMap<String, Boolean>) activeTags.clone();
    }

    /**
     * Gets the amount of tags that are active
     *
     * @return Amount of active tags
     */
    public static int getActiveTagsAmount() {
        int i = 0;
        for (boolean b : activeTags.values()) {
            if (b) i++;
        }
        return i;
    }

    public static void clear() {
        signRegistry.clear();
        activatedSignRegistry.clear();
        activeTags.clear();
        activateTag(ALWAYS_ACTIVE_TAG);
    }

    public static void sortRegistry() {
        Collections.sort(activatedSignRegistry, new Comparator<SignInfo>() {
            @Override
            public int compare(SignInfo o1, SignInfo o2) {
                return (o1.isMetal && !o2.isMetal) ?
                        1 :
                        (!o1.isMetal && o2.isMetal) ?
                                -1 :
                                (o1.material.path.equals("") && o1.material.path.equals(o2.material.path) ?
                                        0 :
                                        (o1.material.path.equals(o2.material.path) ?
                                                (o1.itemName.compareToIgnoreCase(o2.itemName)) :
                                                (o1.material.path.compareTo(o2.material.path))
                                        )
                                );
            }
        });
    }
}

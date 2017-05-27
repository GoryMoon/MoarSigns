package gory_moon.moarsigns.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gory_moon.moarsigns.integration.IntegrationHandler;
import gory_moon.moarsigns.util.IntegrationException;
import net.minecraft.item.ItemStack;

import java.util.*;

public class SignRegistry {

    public static final String ALWAYS_ACTIVE_TAG = "activeALL";
    private static ArrayList<SignInfo> signRegistry;
    private static ArrayList<SignInfo> activatedSignRegistry;
    private static HashMap<String, Boolean> activeTags;
    private static HashMap<SignInfo, ArrayList<MaterialInfo>> alternativeRecipeMaterial;

    static {
        activeTags = Maps.newHashMap();
        signRegistry = Lists.newArrayList();
        activatedSignRegistry = Lists.newArrayList();
        alternativeRecipeMaterial = Maps.newHashMap();

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
     * @throws IntegrationException if anything goes wrong the exception is thrown
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock, String modId) throws IntegrationException {
        if ((materialItemStack == null || materialItemStack.getItem() == null) && IntegrationHandler.donePreSetup())
            throw new IntegrationException("Material " + materialName + " is null for sign " + itemName);

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
     * @throws IntegrationException if anything goes wrong the exception is thrown
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock, String modId, String activateTag) throws IntegrationException {
        if ((materialItemStack == null || materialItemStack.getItem() == null) && IntegrationHandler.donePreSetup())
            throw new IntegrationException("Material " + materialName + " is null for sign " + itemName);

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
     * @throws IntegrationException if anything goes wrong the exception is thrown
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, String modId) throws IntegrationException {
        if ((materialItemStack == null || materialItemStack.getItem() == null) && IntegrationHandler.donePreSetup())
            throw new IntegrationException("Material " + materialName + " is null for sign " + itemName);

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
     * @throws IntegrationException if anything goes wrong the exception is thrown
     */
    public static SignInfo register(String itemName, SignSpecialProperty property, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, String modId, String activateTag) throws IntegrationException {
        if ((materialItemStack == null || materialItemStack.getItem() == null) && IntegrationHandler.donePreSetup())
            throw new IntegrationException("Material " + materialName + " is null for sign " + itemName);

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
     * Registers a new material and register it to be used as a alternative material in crafting
     *
     * @param sInfo             {@link gory_moon.moarsigns.api.SignInfo} to give an alternative material
     * @param materialName      The name of the material
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @return The {@link gory_moon.moarsigns.api.MaterialInfo} that was registered
     * @throws IntegrationException if anything goes wrong the exception is thrown
     */
    public static MaterialInfo registerAlternativeMaterial(SignInfo sInfo, String materialName, String path, boolean gotNugget, ItemStack materialItemStack) throws IntegrationException {
        if ((materialItemStack == null || materialItemStack.getItem() == null) && IntegrationHandler.donePreSetup())
            throw new IntegrationException("Material " + materialName + " is null for sign " + sInfo.itemName);

        MaterialInfo info = MaterialRegistry.register(materialName, path, gotNugget, materialItemStack);
        return registerAlternativeMaterial(sInfo, info);
    }

    /**
     * Registers a new material and register it to be used as a alternative material in crafting
     *
     * @param sInfo             {@link gory_moon.moarsigns.api.SignInfo} to give an alternative material
     * @param materialName      The name of the material
     * @param path              Path to the folder that contains the sign texture in the "/metal" or "/wood" folder
     * @param gotNugget         True if the metal have a nugget, nugget should be obtainable trough 1 metal = nugget in a normal crafting table
     * @param materialItemStack An itemstack of the material
     * @param materialBlock     An itemstack of the block for the material
     * @return The {@link gory_moon.moarsigns.api.MaterialInfo} that was registered
     * @throws IntegrationException if anything goes wrong the exception is thrown
     */
    public static MaterialInfo registerAlternativeMaterial(SignInfo sInfo, String materialName, String path, boolean gotNugget, ItemStack materialItemStack, ItemStack materialBlock) throws IntegrationException {
        if ((materialItemStack == null || materialItemStack.getItem() == null) && IntegrationHandler.donePreSetup())
            throw new IntegrationException("Material " + materialName + " is null for sign " + sInfo.itemName);

        MaterialInfo info = MaterialRegistry.register(materialName, path, gotNugget, materialItemStack, materialBlock);
        return registerAlternativeMaterial(sInfo, info);
    }

    /**
     * Registers a material to be used as a alternative material in crafting
     *
     * @param sInfo {@link gory_moon.moarsigns.api.SignInfo} to give an alternative material
     * @param mInfo {@link gory_moon.moarsigns.api.MaterialInfo} to use as an alternative
     * @return The {@link gory_moon.moarsigns.api.MaterialInfo} used to register
     * @throws IntegrationException if anything goes wrong the exception is thrown
     */
    public static MaterialInfo registerAlternativeMaterial(SignInfo sInfo, MaterialInfo mInfo) throws IntegrationException {
        if ((sInfo == null || mInfo == null) && IntegrationHandler.donePreSetup())
            throw new IntegrationException("Material " + mInfo.materialName + " is null for sign " + sInfo.itemName);

        if (alternativeRecipeMaterial.containsKey(sInfo)) {
            alternativeRecipeMaterial.get(sInfo).add(mInfo);
        } else {
            ArrayList<MaterialInfo> i = new ArrayList<>();
            i.add(mInfo);
            alternativeRecipeMaterial.put(sInfo, i);
        }

        return mInfo;
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
    public static void deactivateTag(String tag) {
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
     * Gets a list of alternative materials for a sign if it exists
     *
     * @param info The {@link gory_moon.moarsigns.api.SignInfo} to get the materials from
     * @return null if no alternatives exist otherwise a list of {@link gory_moon.moarsigns.api.MaterialInfo}
     */
    public static List<MaterialInfo> getAlternativeMaterials(SignInfo info) {
        if (alternativeRecipeMaterial.containsKey(info)) {
            return alternativeRecipeMaterial.get(info);
        }
        return Lists.newArrayList();
    }


    /**
     * Gets the sign from material
     *
     * @param materials Materials to search with
     * @return A list of {@link gory_moon.moarsigns.api.SignInfo}
     */
    public static ArrayList<SignInfo> getSignInfoFromMaterials(HashSet<MaterialInfo> materials) {
        Set<SignInfo> signInfos = Sets.newHashSet();

        for (SignInfo info : activatedSignRegistry) {
            for (MaterialInfo materialInfo : materials) {
                if (info.material.materialName.equals(materialInfo.materialName)) {
                    signInfos.add(info);
                    break;
                }
                for (Map.Entry<SignInfo, ArrayList<MaterialInfo>> entry : alternativeRecipeMaterial.entrySet()) {
                    for (MaterialInfo info1 : entry.getValue()) {
                        if (info1.equals(materialInfo)) {
                            signInfos.add(entry.getKey());
                        }
                    }
                }
            }
        }

        return new ArrayList<>(signInfos);
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

    /**
     * Gets all texture locations of all signs
     *
     * @param includeModID If the modid should be included in the path
     * @return List of texture locations
     */
    public static ArrayList<String> getTextureLocations(boolean includeModID) {
        ArrayList<String> list = getTextureLocations(false, includeModID);
        list.addAll(getTextureLocations(true, includeModID));
        return list;
    }

    /**
     * Gets all texture of all signs that's either wood or metal
     *
     * @param isMetal      If signs made of metal or not should be returned
     * @param includeModID If the modid should be included in the path
     * @return List of texture locations
     */
    public static ArrayList<String> getTextureLocations(boolean isMetal, boolean includeModID) {
        ArrayList<String> list = Lists.newArrayList();
        for (SignInfo info : signRegistry) {
            if (info.isMetal == isMetal) {
                list.add((includeModID ? info.modId + ":" : "") + "signs/" + (info.material.path + info.itemName));
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
            if (b)
                i++;
        }
        return i;
    }

    /**
     * Clears the registers, only usable by moarsigns
     */
    public static void clear() {
        signRegistry.clear();
        activatedSignRegistry.clear();
        activeTags.clear();
        alternativeRecipeMaterial.clear();
        activateTag(ALWAYS_ACTIVE_TAG);
    }

    /**
     * Sorts the activated signs in order of wood and metal, then in order of mod owner
     */
    public static void sortRegistry() {
        activatedSignRegistry.sort(
                (o1, o2) -> (o1.isMetal && !o2.isMetal) ?
                        1 :
                        (!o1.isMetal && o2.isMetal) ?
                            -1 :
                            ("".equals(o1.material.path) && o1.material.path.equals(o2.material.path) ?
                                0 : (o1.material.path.equals(o2.material.path) ?
                                (o1.itemName.compareToIgnoreCase(o2.itemName)) : (o1.material.path.compareTo(o2.material.path)))));
    }
}

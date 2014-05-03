package gory_moon.moarsigns;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.items.Items;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.ClientPacketHandler;
import gory_moon.moarsigns.network.ServerPacketHandler;
import gory_moon.moarsigns.proxy.CommonProxy;
import gory_moon.moarsigns.util.SignInitialization;
import gory_moon.moarsigns.util.Signs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {ModInfo.CHANNEL_C}, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @NetworkMod.SidedPacketHandler(channels = {ModInfo.CHANNEL_S}, packetHandler = ServerPacketHandler.class))
public class MoarSigns {

    @Instance(ModInfo.ID)
    public static MoarSigns instance;

    public MoarSignsCreativeTab tabMS = new MoarSignsCreativeTab("moarSigns");

    @SidedProxy(clientSide = ModInfo.CLIENTPROXY, serverSide = ModInfo.COMMONPROXY)
    public static CommonProxy proxy;

    private ArrayList<Signs> tempWoodSigns;
    private ArrayList<Signs> tempMetalSigns;
    public ArrayList<Signs> signsWood;
    public ArrayList<Signs> signsMetal;
    private static HashMap<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();
    public static HashMap<String, Icon> icons = new HashMap<String, Icon>();
    public static HashMap<String, ItemStack> craftingMats = new HashMap<String, ItemStack>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        new ConfigHandler(event.getSuggestedConfigurationFile());
        signsWood = new ArrayList<Signs>();
        signsMetal = new ArrayList<Signs>();

        proxy.readSigns();

        Blocks.init();
        Items.init();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.initRenderers();
        LanguageRegistry.instance().addStringLocalization("itemGroup.moarSigns", "MoarSigns");
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event) {
        setupSigns();

        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

        Items.registerRecipes();
    }

    public ResourceLocation getResourceLocation(String s, boolean isMetal) {
        ResourceLocation location = textures.get(s);

        if (location == null) {
            location = new ResourceLocation("moarsigns", "textures/entities/signs/" + (isMetal ? "metal/": "wood/") + s + ".png");
            textures.put(s, location);
        }

        return location;
    }

    public void loadFile(InputStream input) {
        ArrayList<Signs> signs = null;
        ArrayList<Signs> signs2 = null;

        try {
            ObjectInputStream in = new ObjectInputStream(input);

            signs = (ArrayList<Signs>) in.readObject();
            signs2 = (ArrayList<Signs>) in.readObject();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (signs != null) this.tempWoodSigns = signs;
        if (signs2 != null) this.tempMetalSigns = signs2;
    }

    public void setupSigns() {
        ArrayList<ItemStack> planks = OreDictionary.getOres("plankWood");

        ArrayList<ItemStack> ingots = new ArrayList<ItemStack>();
        ingots.addAll(OreDictionary.getOres("ingotCopper"));
        ingots.addAll(OreDictionary.getOres("ingotTin"));
        ingots.addAll(OreDictionary.getOres("ingotSilver"));
        ingots.addAll(OreDictionary.getOres("ingotBronze"));
        ingots.addAll(OreDictionary.getOres("ingotSteel"));

        HashMap<String, ItemStack> materials1 = new HashMap<String, ItemStack>();
        HashMap<String, ItemStack> materials2 = new HashMap<String, ItemStack>();

        SignInitialization.addWoodMaterial(materials1, planks);
        SignInitialization.addMetalMaterial(materials2, ingots);


        Container dummyContainer = new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer entityplayer) {
                return false;
            }

            @Override
            public void onCraftMatrixChanged(IInventory par1IInventory) {}
        };
        InventoryCrafting crafting = new InventoryCrafting(dummyContainer, 3, 3);

        for (Signs sign: MoarSigns.instance.getTempWoodSigns()) {
            for (ItemStack stack: materials1.values()) {
                String unlocalized = stack.getUnlocalizedName();

                for (Signs.Material unloc: sign.material) {
                    if (unloc.unlocalizedName.equals(unlocalized)) {
                        if (stack.getItem() instanceof ItemBlock) {
                            unloc.matId = stack.itemID;
                            unloc.matMeta = stack.getItemDamage();
                        } else {
                            for (int i = 0; i < 9; i++) {crafting.setInventorySlotContents(i, stack);}
                            ItemStack stack1 = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
                            if (stack1 != null) {
                                unloc.matId = stack1.itemID;
                                unloc.matMeta = stack1.getItemDamage();
                            } else {
                                unloc.matId = stack.itemID;
                                unloc.matMeta = stack.getItemDamage();
                            }
                            for (int i = 0; i < 9; i++) {crafting.setInventorySlotContents(i, null);}
                        }
                    }
                }
            }
            for (int i = 0; i < sign.material.length; i++) {
                Signs s = sign.clone();
                if (sign.material[i].matId != 0) {
                    s.activeMaterialIndex = i;
                    MoarSigns.instance.signsWood.add(s);
                }
            }
        }

        for (Signs sign: MoarSigns.instance.getTempMetalSigns()) {
            for (ItemStack stack: materials2.values()) {
                String unlocalized = stack.getUnlocalizedName();

                for (Signs.Material unloc : sign.material) {
                    if (unloc.unlocalizedName.equals(unlocalized)) {
                        if (stack.getItem() instanceof ItemBlock) {
                            unloc.matId = stack.itemID;
                            unloc.matMeta = stack.getItemDamage();
                        } else {
                            for (int i = 0; i < 9; i++) {crafting.setInventorySlotContents(i, stack);}
                            ItemStack stack1 = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
                            if (stack1 != null) {
                                unloc.matId = stack1.itemID;
                                unloc.matMeta = stack1.getItemDamage();
                            } else {
                                unloc.matId = stack.itemID;
                                unloc.matMeta = stack.getItemDamage();
                            }
                            for (int i = 0; i < 9; i++) {crafting.setInventorySlotContents(i, null);}
                        }
                    }
                }
            }
            for (int i = 0; i < sign.material.length; i++) {
                Signs s = sign.clone();
                if (sign.material[i].matId != 0) {
                    s.activeMaterialIndex = i;
                    MoarSigns.instance.signsMetal.add(s);
                }
            }
        }

        MoarSigns.craftingMats = materials1;
        MoarSigns.craftingMats.putAll(materials2);
    }

    public ArrayList<Signs> getSignsWood() {
        return signsWood;
    }
    public ArrayList<Signs> getTempWoodSigns() {
        return tempWoodSigns;
    }
    public ArrayList<Signs> getSignsMetal() {
        return signsMetal;
    }
    public ArrayList<Signs> getTempMetalSigns() {
        return tempMetalSigns;
    }
}

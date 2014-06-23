package gory_moon.moarsigns;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.MaterialRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.interfaces.GuiHandler;
import gory_moon.moarsigns.items.Items;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.PacketHandler;
import gory_moon.moarsigns.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = "after:*")
public class MoarSigns {

    @Instance(ModInfo.ID)
    public static MoarSigns instance;

    public MoarSignsCreativeTab tabMS = new MoarSignsCreativeTab("moarSigns");
    public static Logger logger;

    @SidedProxy(clientSide = ModInfo.CLIENTPROXY, serverSide = ModInfo.COMMONPROXY)
    public static CommonProxy proxy;

    private static HashMap<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();
    public static HashMap<String, IIcon> icons = new HashMap<String, IIcon>();
    public static HashMap<String, ItemStack> craftingMats = new HashMap<String, ItemStack>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        new ConfigHandler(event.getSuggestedConfigurationFile());

        PacketHandler.init();

        logger = LogManager.getLogger("MoarSigns");

        Blocks.init();
        Items.init();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.initRenderers();

        setupSigns();
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        Items.registerRecipes();
    }

    public ResourceLocation getResourceLocation(String s, boolean isMetal) {
        ResourceLocation location = textures.get(s);

        if (location == null) {
            location = new ResourceLocation("moarsigns", "textures/entities/signs/" + (isMetal ? "metal/" : "wood/") + s + ".png");
            textures.put(s, location);
        }

        return location;
    }

    public void setupSigns() {
        ArrayList<ItemStack> planks = OreDictionary.getOres("plankWood");

        ArrayList<ItemStack> ingots = new ArrayList<ItemStack>();
        ingots.addAll(OreDictionary.getOres("ingotCopper"));
        ingots.addAll(OreDictionary.getOres("ingotTin"));
        ingots.addAll(OreDictionary.getOres("ingotSilver"));
        ingots.addAll(OreDictionary.getOres("ingotBronze"));
        ingots.addAll(OreDictionary.getOres("ingotSteel"));

        SignInitialization.addWoodMaterial(planks);
        SignInitialization.addMetalMaterial(ingots);

        Collections.sort(SignRegistry.getSignRegistry(), new Comparator<SignInfo>() {
            @Override
            public int compare(SignInfo o1, SignInfo o2) {
                return (o1.isMetal && !o2.isMetal) ? 1 : ((o1.isMetal) ? 0 : (o2.isMetal ? -1 : (o1.material.path.equals("") && o1.material.path.equals(o2.material.path) ? 0 : (o1.material.path.equals(o2.material.path) ? (o1.itemName.compareToIgnoreCase(o2.itemName)) : (o1.material.path.compareTo(o2.material.path))))));
            }
        });

        Container dummyContainer = new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer entityplayer) {
                return false;
            }

            @Override
            public void onCraftMatrixChanged(IInventory par1IInventory) {
            }
        };
        InventoryCrafting crafting = new InventoryCrafting(dummyContainer, 3, 3);

        for (Map.Entry<String, Set<MaterialInfo>> materialList : MaterialRegistry.materialRegistry.entrySet()) {
            for (MaterialInfo material : materialList.getValue()) {
                ItemStack stack = material.material;

                if (!(stack.getItem() instanceof ItemBlock)) {
                    for (int i = 0; i < 9; i++) {
                        crafting.setInventorySlotContents(i, stack);
                    }
                    ItemStack stack1 = CraftingManager.getInstance().findMatchingRecipe(crafting, null);
                    if (stack1 != null) {
                        material.material = stack1;
                    }
                    for (int i = 0; i < 9; i++) {
                        crafting.setInventorySlotContents(i, null);
                    }
                }

            }
        }
    }
}

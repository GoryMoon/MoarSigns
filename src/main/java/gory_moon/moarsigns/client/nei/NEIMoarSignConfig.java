package gory_moon.moarsigns.client.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class NEIMoarSignConfig implements IConfigureNEI {

    public static ArrayList<ItemStack> moarSigns = new ArrayList<ItemStack>();

    @Override
    public void loadConfig() {
        API.hideItem(new ItemStack(Blocks.signStandingMetal));
        API.hideItem(new ItemStack(Blocks.signStandingWood));
        API.hideItem(new ItemStack(Blocks.signWallMetal));
        API.hideItem(new ItemStack(Blocks.signWallWood));
        API.hideItem(new ItemStack(ModItems.debug));

        API.registerRecipeHandler(new NEIShapedMoarSignHandler());
        API.registerUsageHandler(new NEIShapedMoarSignHandler());
        API.registerRecipeHandler(new NEIShapelessMoarSignHandler());
        API.registerUsageHandler(new NEIShapelessMoarSignHandler());

        MoarSigns.logger.info("Loaded NEI Integration");
    }

    @Override
    public String getName() {
        return "MoarSigns NEI";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}

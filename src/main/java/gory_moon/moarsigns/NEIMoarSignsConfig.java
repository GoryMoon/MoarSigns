package gory_moon.moarsigns;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.items.Items;
import net.minecraft.item.ItemStack;

public class NEIMoarSignsConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.hideItem(new ItemStack(Blocks.signStandingMetal));
        API.hideItem(new ItemStack(Blocks.signStandingWood));
        API.hideItem(new ItemStack(Blocks.signWallMetal));
        API.hideItem(new ItemStack(Blocks.signWallWood));
        API.hideItem(new ItemStack(Items.debug));
    }

    @Override
    public String getName() {
        return "MoarSigns NEI Config";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}

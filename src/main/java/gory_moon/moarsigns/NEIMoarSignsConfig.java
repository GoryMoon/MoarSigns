package gory_moon.moarsigns;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.items.Items;

public class NEIMoarSignsConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.hideItem(Blocks.signStandingMetal.blockID);
        API.hideItem(Blocks.signStandingWood.blockID);
        API.hideItem(Blocks.signWallMetal.blockID);
        API.hideItem(Blocks.signWallWood.blockID);
        API.hideItem(Items.debug.itemID);
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

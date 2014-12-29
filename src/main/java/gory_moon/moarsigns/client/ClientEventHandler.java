package gory_moon.moarsigns.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gory_moon.moarsigns.MoarSigns;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.lang.reflect.Method;

public class ClientEventHandler {

    private boolean setupNEI = true;

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (setupNEI) {
            if (Loader.isModLoaded("NotEnoughItems")) {
                try {
                    Class<?> neiAPI = Class.forName("codechicken.nei.api.API");
                    Method hideItem = neiAPI.getDeclaredMethod("hideItem", new Class[]{ItemStack.class});
                    hideItem.invoke(null, new ItemStack(Blocks.signStandingMetal));
                    hideItem.invoke(null, new ItemStack(Blocks.signStandingWood));
                    hideItem.invoke(null, new ItemStack(Blocks.signWallMetal));
                    hideItem.invoke(null, new ItemStack(Blocks.signWallWood));
                    hideItem.invoke(null, new ItemStack(ModItems.debug));
                    setupNEI = false;
                } catch (Throwable t) {
                    MoarSigns.logger.warn("There was a problem integrating with NEI. This is non critical.");
                }
            }
        }
    }

}

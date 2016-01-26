package gory_moon.moarsigns.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.message.MessageSignMainInfo;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.network.message.MessageSignUpdate;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);

    public static void init() {
        INSTANCE.registerMessage(MessageSignMainInfo.class, MessageSignMainInfo.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageSignOpenGui.class, MessageSignOpenGui.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageSignUpdate.class, MessageSignUpdate.class, 2, Side.SERVER);
    }

}

package gory_moon.moarsigns.network;

import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.message.MessageSignInfo;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);

    public static void init() {
        INSTANCE.registerMessage(MessageSignOpenGui.Handler.class, MessageSignOpenGui.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageSignInfo.ServerHandler.class, MessageSignInfo.class, 1, Side.SERVER);
    }

}

package gory_moon.moarsigns.network;

import gory_moon.moarsigns.lib.ModInfo;
import gory_moon.moarsigns.network.message.MessageSignMainInfo;
import gory_moon.moarsigns.network.message.MessageSignOpenGui;
import gory_moon.moarsigns.network.message.MessageSignUpdate;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);

    public static void init() {
        INSTANCE.registerMessage(MessageSignMainInfo.Handler.class, MessageSignMainInfo.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageSignOpenGui.Handler.class, MessageSignOpenGui.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageSignUpdate.Handler.class, MessageSignUpdate.class, 2, Side.SERVER);
    }

}

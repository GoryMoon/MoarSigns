package gory_moon.moarsigns.network;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ServerMessageHandler<REQ extends IMessage> implements IMessageHandler<REQ, IMessage> {

    @Override
    public IMessage onMessage(final REQ message, final MessageContext ctx) {
        MinecraftServer.getServer().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                handle(message, ctx);
            }
        });
        return null;
    }

    protected abstract void handle(REQ message, MessageContext ctx);

}

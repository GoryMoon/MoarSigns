package gory_moon.moarsigns.network;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ServerMessageHandler<R extends IMessage> implements IMessageHandler<R, IMessage> {

    @Override
    public IMessage onMessage(final R message, final MessageContext ctx) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                handle(message, ctx);
            }
        });
        return null;
    }

    protected abstract void handle(R message, MessageContext ctx);

}

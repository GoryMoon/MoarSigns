package gory_moon.moarsigns.network;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ClientMessageHandler<REQ extends IMessage> implements IMessageHandler<REQ, IMessage> {

    @Override
    public IMessage onMessage(final REQ message, final MessageContext ctx) {
        FMLClientHandler.instance().getClient().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                handle(message, ctx);
            }
        });
        return null;
    }

    protected abstract void handle(REQ message, MessageContext ctx);

}

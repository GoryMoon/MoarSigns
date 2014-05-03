package gory_moon.moarsigns.proxy;

import gory_moon.moarsigns.MoarSigns;

import java.io.InputStream;

public class CommonProxy {

    public void initRenderers() {}

    public void readSigns() {
        InputStream stream = CommonProxy.class.getResourceAsStream("/assets/moarsigns/info/signs.items");
        MoarSigns.instance.loadFile(stream);
    }

}

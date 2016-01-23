package gory_moon.moarsigns.blocks;

import gory_moon.moarsigns.api.SignRegistry;
import net.minecraft.block.properties.PropertyHelper;

import java.util.Collection;

public class PropertyString extends PropertyHelper<String> {

    protected PropertyString(String name) {
        super(name, String.class);

    }

    public static PropertyString create(String name) {
        return new PropertyString(name);
    }

    @Override
    public Collection<String> getAllowedValues() {
        return SignRegistry.getTextureLocations();
    }

    @Override
    public String getName(String value) {
        return value;
    }
}

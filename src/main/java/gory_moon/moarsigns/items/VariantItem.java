package gory_moon.moarsigns.items;

import net.minecraft.item.Item;

import java.util.List;

public abstract class VariantItem extends Item{
    
    public abstract List<Integer> getMetas();
    public abstract String getVariant(int meta);
    
}

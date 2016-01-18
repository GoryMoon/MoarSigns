package gory_moon.moarsigns.api;

import net.minecraft.item.EnumRarity;

public class SignInfo {

    public String itemName;
    public MaterialInfo material;
    public boolean isMetal;
    public EnumRarity rarity = EnumRarity.common;

    public SignSpecialProperty property;
    public String modId;
    public String activateTag;


    public SignInfo(String itemName, MaterialInfo material, SignSpecialProperty property, String modId, String activateTag) {
        this.itemName = itemName;
        this.material = material;
        this.property = property;
        this.modId = modId;
        this.activateTag = activateTag;
    }

    public SignInfo setMetal() {
        this.isMetal = true;
        return this;
    }

    public SignInfo setRarity(EnumRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public SignInfo setRarity(int rarity) {
        return setRarity(EnumRarity.values()[rarity]);
    }
}

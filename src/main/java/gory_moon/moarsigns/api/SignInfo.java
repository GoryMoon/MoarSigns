package gory_moon.moarsigns.api;

import net.minecraft.item.EnumRarity;

public class SignInfo {

    public String itemName;
    public MaterialInfo material;
    public boolean isMetal;
    public EnumRarity rarity = EnumRarity.COMMON;

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

    @Override
    public String toString() {
        return String.format("\n[SignInfo]: \n\tItemName: %1$s\n" + "\tMaterial: %2$s\n" + "\tRarity: %3$s\n" + "\tProperty: %4$s\n" + "\tModID: %5$s\n" + "\tActivateTag: %6$s", itemName, material, rarity.rarityName, property, modId, activateTag);
    }
}

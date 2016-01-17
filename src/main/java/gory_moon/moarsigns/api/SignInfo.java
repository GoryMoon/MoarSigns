package gory_moon.moarsigns.api;

public class SignInfo {

    public String itemName;
    public MaterialInfo material;
    public boolean isMetal;

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
}

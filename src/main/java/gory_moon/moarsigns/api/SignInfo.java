package gory_moon.moarsigns.api;

public class SignInfo {

    public String itemName;
    public MaterialInfo material;
    public boolean isMetal;

    public SignSpecialProperty property;
    public String modId;


    public SignInfo(String itemName, MaterialInfo material, SignSpecialProperty property, String modId) {
        this.itemName = itemName;
        this.material = material;
        this.property = property;
        this.modId = modId;
    }

    public SignInfo setMetal(boolean isMetal) {
        this.isMetal = isMetal;
        return this;
    }
}

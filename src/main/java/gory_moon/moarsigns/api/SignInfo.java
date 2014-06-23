package gory_moon.moarsigns.api;

public class SignInfo {

    public String itemName;
    public MaterialInfo material;
    public boolean isMetal;

    public SignSpecialProperty property;


    public SignInfo(String itemName, MaterialInfo material, SignSpecialProperty property) {
        this.itemName = itemName;
        this.material = material;
        this.property = property;
    }

    public SignInfo setMetal(boolean isMetal) {
        this.isMetal = isMetal;
        return this;
    }
}

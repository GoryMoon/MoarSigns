package gory_moon.moarsigns.util;
import java.io.Serializable;
import java.util.Arrays;

public class Signs implements Serializable{

    public Signs(String signName) {
        this.signName = signName;
    }

    public String signName;
    public String itemName;
    public String itemTexture;
    public Material[] material;
    public boolean isMetal;
    public int activeMaterialIndex;

    @Override
    public String toString() {
        return "Signs{" +
                "signName='" + signName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemTexture='" + itemTexture + '\'' +
                ", isMetal=" + isMetal +
                ", activeMaterialIndex=" + activeMaterialIndex +
                ", material=" + Arrays.toString(material) +

                "}\n";
    }

    public Signs clone() {
        Signs s = new Signs(signName);
        s.isMetal = isMetal;
        s.itemName = itemName;
        s.itemTexture = itemTexture;
        s.activeMaterialIndex = activeMaterialIndex;
        s.material = Arrays.copyOf(material, material.length);
        return s;
    }

    /**
     * Checks if all objects match
     *
     * @param o Object to match with
     * @return True if they are equal else False
     */
    public boolean equalsAll(Object o) {
        if (this == o) return true;
        if (!(o instanceof Signs)) return false;

        Signs signs = (Signs) o;
        return isMetal == signs.isMetal && !(itemName != null ? !itemName.equals(signs.itemName) : signs.itemName != null) && !(itemTexture != null ? !itemTexture.equals(signs.itemTexture) : signs.itemTexture != null) && Arrays.equals(material, signs.material) && !(signName != null ? !signName.equals(signs.signName) : signs.signName != null);

    }

    /**
     * Checks if all except the Signs.Material array match
     *
     * @param o Object ot match with
     * @return True if they are equal else False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Signs)) return false;

        Signs signs = (Signs) o;
        return isMetal == signs.isMetal && !(itemName != null ? !itemName.equals(signs.itemName) : signs.itemName != null) && !(itemTexture != null ? !itemTexture.equals(signs.itemTexture) : signs.itemTexture != null) && !(signName != null ? !signName.equals(signs.signName) : signs.signName != null);

    }

    public static class Material implements Serializable {
        public String unlocalizedName;
        public int meta;

        public String path;

        public String mainTexture;
        public String poleTexture;

        public int matId;
        public int matMeta;

        public boolean gotNugget;

        /**
         * Checks if all objects match
         *
         * @param o Object to match with
         * @return True if they are equal else False
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Material)) return false;

            Material material = (Material) o;
            return matMeta == material.matMeta && matId == material.matId && meta == material.meta && !(poleTexture != null ? !poleTexture.equals(material.poleTexture) : material.poleTexture != null) && !(path != null ? !path.equals(material.path) : material.path != null) && !(mainTexture != null ? !mainTexture.equals(material.mainTexture) : material.mainTexture != null) && !(unlocalizedName != null ? !unlocalizedName.equals(material.unlocalizedName) : material.unlocalizedName != null);

        }

        @Override
        public String toString() {
            return "Material{" +
                    "unlocalizedName='" + unlocalizedName + '\'' +
                    ", meta=" + meta +
                    ", path='" + path + '\'' +
                    ", mainTexture='" + mainTexture + '\'' +
                    ", poleTexture='" + poleTexture + '\'' +
                    ", matId=" + matId +
                    ", matMeta=" + matMeta +
                    ", gotNugget=" + gotNugget +
                    '}';
        }
    }
}

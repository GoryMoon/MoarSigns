package gory_moon.moarsigns.integration.jei.exchange;

import gory_moon.moarsigns.api.MaterialInfo;
import gory_moon.moarsigns.api.MaterialRegistry;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.integration.jei.MoarSignsJeiRecipeHelper;
import gory_moon.moarsigns.items.ItemMoarSign;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static gory_moon.moarsigns.integration.jei.MoarSignsPlugin.moarSigns;

public class ExchangeRecipeMaker {

    public static List<ExchangeRecipe> getExchangeRecipes() {
        List<ExchangeRecipe> exchangeRecipes = new ArrayList<ExchangeRecipe>();
        for (ItemStack stack : moarSigns) {
            String texture = ItemMoarSign.getTextureFromNBTFull(stack.getTagCompound());
            SignInfo signInfo = SignRegistry.get(texture);

            if (signInfo == null || signInfo.material == null || signInfo.material.materialName == null)
                continue;
            HashSet<MaterialInfo> materials = MaterialRegistry.get(signInfo.material.materialName);
            if (materials == null || materials.size() <= 1) {
                continue;
            }

            ArrayList<SignInfo> signs = SignRegistry.getSignInfoFromMaterials(materials);
            if (signs == null || signs.size() <= 1) {
                continue;
            }

            List<ItemStack> variations = MoarSignsJeiRecipeHelper.getVariationStacks(signs);
            exchangeRecipes.add(new ExchangeRecipe(stack, variations));
        }
        return exchangeRecipes;
    }
}

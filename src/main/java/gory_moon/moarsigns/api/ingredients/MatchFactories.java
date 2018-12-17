package gory_moon.moarsigns.api.ingredients;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;

public class MatchFactories {

    @SuppressWarnings("unused")
    public static class MatchAllFactory implements IIngredientFactory {

        @Nonnull
        @Override
        public Ingredient parse(JsonContext context, JsonObject json) {
            return new MatchAllIngredient();
        }
    }

    @SuppressWarnings("unused")
    public static class MatchMetalFactory implements IIngredientFactory {

        @Nonnull
        @Override
        public Ingredient parse(JsonContext context, JsonObject json) {
            return new MatchMetalIngredient();
        }
    }

    @SuppressWarnings("unused")
    public static class MatchWoodFactory implements IIngredientFactory {

        @Nonnull
        @Override
        public Ingredient parse(JsonContext context, JsonObject json) {
            return new MatchWoodIngredient();
        }
    }
}

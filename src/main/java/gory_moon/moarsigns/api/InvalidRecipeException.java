package gory_moon.moarsigns.api;

public class InvalidRecipeException extends RuntimeException {

    public InvalidRecipeException(String ret) {
        super(ret);
    }
}

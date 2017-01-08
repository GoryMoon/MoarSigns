package gory_moon.moarsigns.util;

public class IntegrationException extends Exception {

    public IntegrationException() {
        super();
    }

    public IntegrationException(String s) {
        super(" - " + s);
    }
}

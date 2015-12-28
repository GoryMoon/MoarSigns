package gory_moon.moarsigns.api;

import java.util.ArrayList;

public class IntegrationRegistry {

    private static ArrayList<ISignRegistration> signReg = new ArrayList<ISignRegistration>();

    public static <T extends ISignRegistration> void registerIntegration(Class<T> clazz) {
        try {
            registerIntegration(clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void registerIntegration(ISignRegistration registration) {
        signReg.add(registration);
    }

    public static ArrayList<ISignRegistration> getSignReg() {
        return (ArrayList<ISignRegistration>) signReg.clone();
    }
}

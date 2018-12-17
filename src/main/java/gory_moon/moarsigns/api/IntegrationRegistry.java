package gory_moon.moarsigns.api;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.ArrayList;

public class IntegrationRegistry {

    private static ArrayList<ISignRegistration> signReg = new ArrayList<>();

    /**
     * Registers a class that implements {@link ISignRegistration}
     * It needs to be registered before {@link FMLPostInitializationEvent}
     *
     * @param clazz The class
     */
    public static <T extends ISignRegistration> void registerIntegration(Class<T> clazz) {
        try {
            registerIntegration(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers a class that implements {@link ISignRegistration}
     * It needs to be registered before {@link FMLPostInitializationEvent}
     *
     * @param registration The instance of the class
     */
    public static void registerIntegration(ISignRegistration registration) {
        signReg.add(registration);
    }

    /**
     * Gets a clone of the registry
     *
     * @return List of {@link ISignRegistration}
     */
    public static ArrayList<ISignRegistration> getSignReg() {
        return (ArrayList<ISignRegistration>) signReg.clone();
    }

    public static ISignRegistration getWithTag(String tag) {
        for (ISignRegistration registration : signReg)
            if (registration.getActivateTag().equals(tag))
                return registration;

        return null;
    }
}

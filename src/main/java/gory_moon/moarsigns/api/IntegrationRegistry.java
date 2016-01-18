package gory_moon.moarsigns.api;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;

import java.util.ArrayList;
import java.util.HashSet;

public class IntegrationRegistry {

    private static ArrayList<ISignRegistration> signReg = new ArrayList<ISignRegistration>();
    private static HashSet<String> woodNames = new HashSet<String>();
    private static HashSet<String> metalNames = new HashSet<String>();

    /**
     * Registers a class that implements {@link ISignRegistration}
     * It needs to be registered before {@link FMLPostInitializationEvent}
     *
     * @param clazz The class
     */
    public static <T extends ISignRegistration> void registerIntegration(Class<T> clazz) {
        try {
            registerIntegration(clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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
     * Adds a name of wood to list that is given in {@link ISignRegistration#registerWoodenSigns(ArrayList)}
     *
     * @param name Name in ore dictionary
     */
    public static void registerPlankOreName(String name) {
        woodNames.add(name);
    }

    /**
     * Adds a name of metal or gem to list that is given in {@link ISignRegistration#registerMetalSigns(ArrayList)}
     *
     * @param name Name in ore dictionary
     */
    public static void registerMetalGemOreName(String name) {
        metalNames.add(name);
    }

    /**
     * Gets a clone of the registry
     *
     * @return List of {@link ISignRegistration}
     */
    public static ArrayList<ISignRegistration> getSignReg() {
        return (ArrayList<ISignRegistration>) signReg.clone();
    }

    /**
     * Gets the registered list of ore dictionary names for metal and gems
     *
     * @return List of ore dictionary names
     */
    public static ArrayList<String> getMetalNames() {
        return new ArrayList<String>(metalNames);
    }

    /**
     * Gets the registered list of ore dictionary names for wood
     *
     * @return List of ore dictionary names
     */
    public static ArrayList<String> getWoodNames() {
        return new ArrayList<String>(woodNames);
    }

    public static ISignRegistration getWithTag(String tag) {
        for (ISignRegistration registration : signReg)
            if (registration.getActivateTag() != null && registration.getActivateTag().equals(tag))
                return registration;

        return null;
    }
}

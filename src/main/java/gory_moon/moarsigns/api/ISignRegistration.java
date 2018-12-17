package gory_moon.moarsigns.api;

import gory_moon.moarsigns.util.IntegrationException;

public interface ISignRegistration extends IIntegrationInfo {

    /**
     * Called to register signs
     *
     */
    void registerSigns() throws IntegrationException;

}

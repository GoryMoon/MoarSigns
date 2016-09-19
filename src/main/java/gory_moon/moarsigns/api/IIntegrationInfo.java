package gory_moon.moarsigns.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IIntegrationInfo {

    /**
     * Gets the tag for activation of signs<br/>
     * When adding signs for other mods it must be
     * the MODID of the Mod that it adding to<br/><br/>
     *
     * When adding to own mod, {@link SignRegistry#ALWAYS_ACTIVE_TAG} is only needed
     * @return The Tag to use for activation the signs
     */
    @Nonnull
    String getActivateTag();

    /**
     * Used for printing info about the integration, usually the pretty name of the Mod
     * @return What to call the integration
     */
    @Nonnull
    String getIntegrationName();

    /**
     * Used when getting the pretty name of a Mod that isn't loaded<br/>
     * Usually only used internally or with mods that adds support for other mods
     * @return The pretty name of the mod with the integration
     */
    @Nullable
    String getModName();
}

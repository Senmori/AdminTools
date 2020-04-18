package net.senmori.admintools;

import com.electronwill.nightconfig.core.Config;
import net.senmori.admintools.util.Directory;

/**
 * Represents a system that can be interacted with in order to
 * build a given product.
 * <p>
 * It is up to the implementation to determine the resources that need to
 * be loaded, how to load them, and how the system should be interacted with.
 */
public interface Project
{

    /**
     * The name of the project
     *
     * @return the name of the project
     */
    String getName();

    /**
     * Get the {@link ClassLoader} that will be used to load project specifc
     * files without having to specify the direct path names.
     *
     * @return the classloader
     */
    ClassLoader getProjectClassLoader();

    Directory getWorkingDirectory();

    Directory getConfigDirectory();

    /**
     * Initialize project settings.
     *
     * @return true if the configs were loaded successfully
     */
    boolean loadSettings();

    default Config getConfig() {
        return null;
    }
}

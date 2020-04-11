package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.Config;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ConfigValue<T>
{
    private final List<String> path;
    private final Supplier<T> defaultSupplier;
    private final Config config;

    public ConfigValue(Config config, List<String> path, Supplier<T> defaultSupplier)
    {
        this.config = config;
        this.path = path;
        this.defaultSupplier = defaultSupplier;
    }

    public List<String> getPath()
    {
        return path;
    }

    public T get()
    {
        if (config == null) {
            return defaultSupplier.get();
        }
        return getRaw(config, path, defaultSupplier);
    }

    protected T getRaw(Config config, List<String> path, Supplier<T> defaultSupplier)
    {
        Objects.requireNonNull( config, "Cannot get value from null config" );
        return config.getOrElse( getPath(), defaultSupplier );
    }

    public void set(T value)
    {
        config.set( getPath(), value );
    }
}

package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.Config;

import java.util.List;
import java.util.function.Supplier;

public class StringValue extends ConfigValue<String>
{
    public StringValue(Config config, List<String> path, Supplier<String> defaultSupplier)
    {
        super(config, path, defaultSupplier);
    }

    @Override
    protected String getRaw(Config config, List<String> path, Supplier<String> defaultSupplier)
    {
        return config.getOrElse(path, defaultSupplier);
    }
}

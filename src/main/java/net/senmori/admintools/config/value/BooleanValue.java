package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.Config;

import java.util.List;
import java.util.function.Supplier;

public class BooleanValue extends ConfigValue<Boolean>
{
    public BooleanValue(Config config, List<String> path, Supplier<Boolean> defaultSupplier)
    {
        super(config, path, defaultSupplier);
    }
}

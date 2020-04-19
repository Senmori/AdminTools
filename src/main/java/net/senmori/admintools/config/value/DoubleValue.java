package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;

import java.util.List;
import java.util.function.Supplier;

public class DoubleValue extends ConfigValue<Double>
{
    public DoubleValue(CommentedConfig config, List<String> path, Supplier<Double> defaultSupplier)
    {
        super(config, path, defaultSupplier);
    }

    @Override
    protected Double getRaw(CommentedConfig config, List<String> path, Supplier<Double> defaultSupplier)
    {
        Number n = config.get(path);
        return n == null ? defaultSupplier.get() : n.doubleValue();
    }
}

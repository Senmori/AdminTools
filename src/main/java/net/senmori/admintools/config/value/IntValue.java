package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;

import java.util.List;
import java.util.function.Supplier;

public class IntValue extends ConfigValue<Integer>
{
    public IntValue(CommentedConfig config, List<String> path, Supplier<Integer> defaultSupplier)
    {
        super(config, path, defaultSupplier);
    }

    @Override
    protected Integer getRaw(CommentedConfig config, List<String> path, Supplier<Integer> defaultSupplier)
    {
        return config.getIntOrElse(path,defaultSupplier::get);
    }
}

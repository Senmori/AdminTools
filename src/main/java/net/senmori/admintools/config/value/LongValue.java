package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;

import java.util.List;
import java.util.function.Supplier;

public class LongValue extends ConfigValue<Long>
{
    public LongValue(CommentedConfig config, List<String> path, Supplier<Long> defaultSupplier)
    {
        super(config, path, defaultSupplier);
    }

    @Override
    protected Long getRaw(CommentedConfig config, List<String> path, Supplier<Long> defaultSupplier)
    {
        return config.getLongOrElse(path,defaultSupplier::get);
    }
}

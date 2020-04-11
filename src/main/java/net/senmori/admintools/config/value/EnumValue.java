package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.EnumGetMethod;

import java.util.List;
import java.util.function.Supplier;

public class EnumValue<T extends Enum<T>> extends ConfigValue<T>
{
    private final EnumGetMethod converter;
    private final Class<T> clazz;

    public EnumValue(Config config, List<String> path, Supplier<T> defaultSupplier, EnumGetMethod converter, Class<T> clazz)
    {
        super(config, path, defaultSupplier);
        this.converter = converter;
        this.clazz = clazz;
    }

    @Override
    protected T getRaw(Config config, List<String> path, Supplier<T> defaultSupplier)
    {
        return config.getEnumOrElse(path, clazz, converter, defaultSupplier);
    }
}

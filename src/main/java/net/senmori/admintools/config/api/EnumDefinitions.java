package net.senmori.admintools.config.api;

import com.electronwill.nightconfig.core.EnumGetMethod;
import net.senmori.admintools.config.value.EnumValue;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.senmori.admintools.util.ConfigUtil.split;

/**
 * Represents how an Enum can be defined and the acceptable values for a definition
 */
public interface EnumDefinitions
{
    <V extends Enum<V>> EnumValue<V> defineEnum(List<String> path, Supplier<V> defaultValue, EnumGetMethod converter, Predicate<Object> validator, Class<V> clazz);


    default <V extends Enum<V>> EnumValue<V> defineEnum(String path, V defaultValue)
    {
        return defineEnum(path, defaultValue, Objects::nonNull);
    }

    default <V extends Enum<V>> EnumValue<V> defineEnum(String path, V defaultValue, Predicate<Object> validator)
    {
        return defineEnum(path, defaultValue, validator, EnumGetMethod.NAME_IGNORECASE);
    }

    default <V extends Enum<V>> EnumValue<V> defineEnum(String path, V defaultValue, Predicate<Object> validator, EnumGetMethod converter)
    {
        return defineEnum(split(path),() -> defaultValue, converter, validator, defaultValue.getDeclaringClass());
    }
}

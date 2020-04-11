package net.senmori.admintools.config.api;

import net.senmori.admintools.config.value.ConfigValue;
import net.senmori.admintools.config.value.ListValue;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.senmori.admintools.util.ConfigUtil.split;

/**
 * Represents how a list can be defined and what the acceptable values are
 * in a configuration file.
 */
public interface ListDefinitions
{
    <T> ListValue<T> defineList(List<String> path,
                                Supplier<List<T>> defaultSupplier,
                                Predicate<Object> elementValidator,
                                Function<Object, T> elementConverter);

    <T> ConfigValue<T> defineInList(List<String> path, Supplier<T> defaultValue, Collection<? extends T> acceptableValues);

    default <T> ListValue<T> defineList(String path, List<T> defaultValue, Function<Object, T> elementConverter) {
        return defineList(split(path),() -> defaultValue, Objects::nonNull, elementConverter);
    }

    default <T> ConfigValue<T> defineInList(String path, T defaultValue, Collection<? extends T> acceptableValues)
    {
        return defineInList(split(path),() -> defaultValue,acceptableValues);
    }
}

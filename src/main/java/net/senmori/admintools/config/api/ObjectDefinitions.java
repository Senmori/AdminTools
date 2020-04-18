package net.senmori.admintools.config.api;

import net.senmori.admintools.config.value.BooleanValue;
import net.senmori.admintools.config.value.ConfigValue;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.senmori.admintools.util.ConfigUtil.split;

/**
 * Represents generic object definitions and the validators to ensure they are stored/retrieved correctly.
 */
public interface ObjectDefinitions
{
    /**
     * Define a new generic object definition.
     *
     * @param path            the path to the value
     * @param defaultSupplier the default supplier for the value
     * @param validator       the validator to ensure proper storage and retrieval.
     * @return a new {@link ConfigValue}
     */
    <T> ConfigValue<T> defineObject(List<String> path, Supplier<T> defaultSupplier, Predicate<Object> validator);

    /**
     * Define a new boolean value
     *
     * @param path         the path to the value
     * @param defaultValue the default value
     * @return a new {@link BooleanValue}
     */
    BooleanValue defineObject(List<String> path, Supplier<Boolean> defaultValue);

    /**
     * @see #defineObject(List, Supplier, Predicate)
     */
    default <T> ConfigValue<T> defineObject(String path, T defaultValue)
    {
        Objects.requireNonNull(defaultValue, "Default value can not be null");
        return defineObject(split(path), () -> defaultValue, o -> defaultValue.getClass().isInstance(o.getClass()));
    }

    /**
     * @see #defineObject(List, Supplier, Predicate)
     */
    default <T> ConfigValue<T> defineObject(String path, Supplier<T> defaultSupplier, Predicate<Object> validator)
    {
        return defineObject(split(path), defaultSupplier, validator);
    }

    /**
     * @see #defineObject(List, Supplier)
     */
    default BooleanValue defineObject(String path, boolean defaultValue)
    {
        return defineObject(split(path), () -> defaultValue);
    }

    /**
     * @see #defineObject(List, Supplier)
     */
    default BooleanValue defineObject(String path, Supplier<Boolean> supplier)
    {
        return defineObject(split(path), supplier);
    }


}

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
     * @param path the path to the value
     * @param defaultSupplier the default supplier for the value
     * @param validator the validator to ensure proper storage and retrieval.
     * @return a new {@link ConfigValue}
     */
    <T> ConfigValue<T> define(List<String> path, Supplier<T> defaultSupplier, Predicate<Object> validator);

    /**
     * Define a new boolean value
     * 
     * @param path the path to the value
     * @param defaultValue the default value
     * @return a new {@link BooleanValue}
     */
    BooleanValue define(List<String> path, Supplier<Boolean> defaultValue);

    /** @see #define(List, Supplier, Predicate) */
    default <T> ConfigValue<T> define(String path, T defaultValue) {
        Objects.requireNonNull(defaultValue, "Default value can not be null");
        return define(split(path), () -> defaultValue, o -> defaultValue.getClass().isInstance(o.getClass()));
    }
    /** @see #define(List, Supplier, Predicate) */
    default <T> ConfigValue<T> define(String path, Supplier<T> defaultSupplier, Predicate<Object> validator) {
        return define(split(path), defaultSupplier, validator);
    }
    /** @see #define(List, Supplier) */
    default BooleanValue define(String path, boolean defaultValue) {
        return define(split(path),() -> defaultValue);
    }
    /** @see #define(List, Supplier) */
    default BooleanValue define(String path, Supplier<Boolean> supplier) {
        return define(split(path), supplier);
    }




}

package net.senmori.custommobs.lib.properties.defaults;

import net.senmori.custommobs.lib.properties.read.ReadOnlyProperty;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class AbstractDefaultProperty<T> extends ReadOnlyProperty<T> {

    private final T defaultValue;

    public AbstractDefaultProperty(final T value) {
        this(null, null, value, value);
    }

    public AbstractDefaultProperty(final T value, final T defaultValue) {
        this(null, null, value, defaultValue);
    }

    public AbstractDefaultProperty(final Object bean, final String name, final T value, final T defaultValue) {
        super(bean, name, value);
        this.defaultValue = defaultValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }
}

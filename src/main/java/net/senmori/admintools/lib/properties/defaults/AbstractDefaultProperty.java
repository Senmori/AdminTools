package net.senmori.admintools.lib.properties.defaults;

import net.senmori.admintools.lib.properties.read.ReadOnlyProperty;

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

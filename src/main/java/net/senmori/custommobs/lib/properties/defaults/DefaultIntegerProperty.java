package net.senmori.custommobs.lib.properties.defaults;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.simple.IntegerProperty;

public class DefaultIntegerProperty extends IntegerProperty {

    private final int defaultValue;

    public DefaultIntegerProperty() {
        this(0);
    }

    public DefaultIntegerProperty(int value) {
        this(null, null, value, value);
    }

    public DefaultIntegerProperty(int value, int defaultValue) {
        this(null, null, value, defaultValue);
    }

    public DefaultIntegerProperty(final Object bean, final String name, final int value) {
        this(bean, name, value, value);
    }

    public DefaultIntegerProperty(final Object bean, final String name, final int value, final int defaultValue) {
        super(bean, name, value);
        this.defaultValue = defaultValue;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    @Override
    protected void setValue(int value) {
        final int old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }
}

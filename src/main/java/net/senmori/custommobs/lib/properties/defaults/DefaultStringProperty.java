package net.senmori.custommobs.lib.properties.defaults;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.simple.StringProperty;

public class DefaultStringProperty extends StringProperty {

    private final String defaultValue;

    public DefaultStringProperty() {
        this(null, null, "");
    }

    public DefaultStringProperty(final String value) {
        this(null, null, value);
    }

    public DefaultStringProperty(final String value, final String defaultValue) {
        this(null, null, value, defaultValue);
    }

    public DefaultStringProperty(final Object bean, final String name, final String value) {
        this(bean, name, value, value);
    }

    public DefaultStringProperty(final Object bean, final String name, final String value, final String defaultValue) {
        super(bean, name, value);
        this.defaultValue = defaultValue;
    }
    @Override
    protected void setValue(String value) {
        if (value == null) {
            value = getDefaultValue();
        }
        final String old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}

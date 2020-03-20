package net.senmori.admintools.lib.properties.defaults;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.primitive.FloatProperty;

public class DefaultFloatProperty extends FloatProperty {

    private final float defaultValue;

    public DefaultFloatProperty() {
        this(0F);
    }

    public DefaultFloatProperty(float value) {
        this(null, null, value, value);
    }

    public DefaultFloatProperty(float value, float defaultValue) {
        this(null, null, value, defaultValue);
    }

    public DefaultFloatProperty(final Object bean, final String name, final float value) {
        this(bean, name, value, value);
    }

    public DefaultFloatProperty(final Object bean, final String name, final float value, final float defaultValue) {
        super(bean, name, value);
        this.defaultValue = defaultValue;
    }

    public float getDefaultValue() {
        return this.defaultValue;
    }

    protected void setValue(float value) {
        final float old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }
}

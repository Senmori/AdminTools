package net.senmori.custommobs.lib.properties.simple;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyFloatProperty;

public class FloatProperty extends ReadOnlyFloatProperty {
    public FloatProperty() {
        super(null, null, 0.0F);
    }

    public FloatProperty(final float value) {
        super(null, null, value);
    }

    public FloatProperty(final Object bean, final String name, final float value) {
        super(bean, name, value);
    }

    protected void setValue(final float value) {
        final float old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final float value) {
        setValue( value );
    }
}

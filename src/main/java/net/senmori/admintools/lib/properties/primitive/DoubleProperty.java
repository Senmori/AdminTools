package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.read.ReadOnlyDoubleProperty;

public class DoubleProperty extends ReadOnlyDoubleProperty {
    public DoubleProperty() {
        super(null, null, 0.0D);
    }

    public DoubleProperty(final double value) {
        super(null, null, value);
    }

    public DoubleProperty(final Object bean, final String name, final double value) {
        super(bean, name, value);
    }

    protected void setValue(final double value) {
        final double old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final double value) {
        setValue( value );
    }
}

package net.senmori.custommobs.lib.properties.simple;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyIntegerProperty;

public class IntegerProperty extends ReadOnlyIntegerProperty {
    public IntegerProperty() {
        super(null, null, 0);
    }

    public IntegerProperty(final int value) {
        super(null, null, value);
    }

    public IntegerProperty(final Object bean, final String name, final int value) {
        super(bean, name, value);
    }

    protected void setValue(final int value) {
        final int old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final int value) {
        setValue( value );
    }
}

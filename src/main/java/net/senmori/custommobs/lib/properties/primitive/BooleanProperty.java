package net.senmori.custommobs.lib.properties.primitive;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyBooleanProperty;

public class BooleanProperty extends ReadOnlyBooleanProperty {
    public BooleanProperty() {
        super(null, null, false);
    }

    public BooleanProperty(final boolean value) {
        super(null, null, value);
    }

    public BooleanProperty(final Object bean, final String name, final boolean value) {
        super(bean, name, value);
    }

    protected void setValue(final boolean value) {
        final boolean old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final boolean value) {
        setValue( value );
    }
}

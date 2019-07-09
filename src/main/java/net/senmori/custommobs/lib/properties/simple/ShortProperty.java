package net.senmori.custommobs.lib.properties.simple;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyShortProperty;

public class ShortProperty extends ReadOnlyShortProperty {
    public ShortProperty() {
        super(null, null, (short) 0);
    }

    public ShortProperty(final short value) {
        super(null, null, value);
    }

    public ShortProperty(final Object bean, final String name, final short value) {
        super(bean, name, value);
    }

    protected void setValue(final short value) {
        final short old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final short value) {
        setValue( value );
    }
}

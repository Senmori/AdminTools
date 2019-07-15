package net.senmori.custommobs.lib.properties.primitive;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyLongProperty;

public class LongProperty extends ReadOnlyLongProperty {
    public LongProperty() {
        super(null, null, 0L);
    }

    public LongProperty(final long value) {
        super(null, null, value);
    }

    public LongProperty(final Object bean, final String name, final long value) {
        super(bean, name, value);
    }

    protected void setValue(final long value) {
        final long old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final long value) {
        setValue( value );
    }
}

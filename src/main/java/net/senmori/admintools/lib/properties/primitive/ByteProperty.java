package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.read.ReadOnlyByteProperty;

public class ByteProperty extends ReadOnlyByteProperty {
    public ByteProperty() {
        super(null, null, (byte)0);
    }

    public ByteProperty(final byte value) {
        super(null, null, value);
    }

    public ByteProperty(final Object bean, final String name, final byte value) {
        super(bean, name, value);
    }

    protected void setValue(final byte value) {
        final byte old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final byte value) {
        setValue( value );
    }
}

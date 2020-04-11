package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class ByteProperty extends DefaultProperty<Byte> {
    public ByteProperty() {
        super( null, ( byte ) 0 );
    }

    public ByteProperty(final byte value) {
        super( null, value );
    }

    public ByteProperty(final String name, final byte value) {
        super( name, value );
    }
}

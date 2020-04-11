package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class ShortProperty extends DefaultProperty<Short> {
    public ShortProperty() {
        super( null, ( short ) 0 );
    }

    public ShortProperty(final short value) {
        super( null, value );
    }

    public ShortProperty(final String name, final short value) {
        super( name, value );
    }
}

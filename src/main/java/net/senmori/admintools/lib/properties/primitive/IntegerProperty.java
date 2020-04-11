package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class IntegerProperty extends DefaultProperty<Integer> {
    public IntegerProperty() {
        super( null, 0 );
    }

    public IntegerProperty(final int value) {
        super( null, value );
    }

    public IntegerProperty(final String name, final int value) {
        super( name, value );
    }
}

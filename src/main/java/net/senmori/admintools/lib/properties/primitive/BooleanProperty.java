package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class BooleanProperty extends DefaultProperty<Boolean> {
    public BooleanProperty() {
        super( null, false );
    }

    public BooleanProperty(final boolean value) {
        super( null, value );
    }

    public BooleanProperty(final String name, final boolean value) {
        super( name, value );
    }
}

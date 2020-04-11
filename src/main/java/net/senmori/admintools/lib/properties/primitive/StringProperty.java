package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class StringProperty extends DefaultProperty<String> {
    public StringProperty() {
        super( null, "" );
    }

    public StringProperty(final String value) {
        super( null, value );
    }

    public StringProperty(final String name, final String value) {
        super( name, value );
    }
}

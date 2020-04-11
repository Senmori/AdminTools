package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class CharProperty extends DefaultProperty<Character> {
    public CharProperty() {
        super( null, ' ' );
    }

    public CharProperty(final char value) {
        super( null, value );
    }

    public CharProperty(final String name, final char value) {
        super( name, value );
    }
}

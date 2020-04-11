package net.senmori.admintools.lib.properties.read;

public class ReadOnlyCharProperty extends ReadOnlyProperty<Character> {
    public ReadOnlyCharProperty() {
        super( null, ' ' );
    }

    public ReadOnlyCharProperty(final char value) {
        super( null, value );
    }

    public ReadOnlyCharProperty(final String name, final char value) {
        super( name, value );
    }
}

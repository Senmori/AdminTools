package net.senmori.admintools.lib.properties.read;

public class ReadOnlyIntegerProperty extends ReadOnlyProperty<Integer> {
    public ReadOnlyIntegerProperty() {
        super( null, 0 );
    }

    public ReadOnlyIntegerProperty(final int value) {
        super( null, value );
    }

    public ReadOnlyIntegerProperty(final String name, final int value) {
        super( name, value );
    }
}

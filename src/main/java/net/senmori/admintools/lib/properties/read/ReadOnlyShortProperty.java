package net.senmori.admintools.lib.properties.read;

public class ReadOnlyShortProperty extends ReadOnlyProperty<Short> {

    public ReadOnlyShortProperty() {
        super( null, ( short ) 0 );
    }

    public ReadOnlyShortProperty(final short value) {
        super( null, value );
    }

    public ReadOnlyShortProperty(final String name, final short value) {
        super( name, value );
    }
}

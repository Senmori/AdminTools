package net.senmori.admintools.lib.properties.read;

public class ReadOnlyLongProperty extends ReadOnlyProperty<Long> {

    public ReadOnlyLongProperty() {
        super( null, 0L );
    }

    public ReadOnlyLongProperty(final long value) {
        super( null, value );
    }

    public ReadOnlyLongProperty(final String name, final long value) {
        super( name, value );
    }
}

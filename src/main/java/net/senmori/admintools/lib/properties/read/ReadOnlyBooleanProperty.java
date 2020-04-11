package net.senmori.admintools.lib.properties.read;

public class ReadOnlyBooleanProperty extends ReadOnlyProperty<Boolean> {
    public ReadOnlyBooleanProperty() {
        super( null, false );
    }

    public ReadOnlyBooleanProperty(final boolean value) {
        super( null, value );
    }

    public ReadOnlyBooleanProperty(final String name, final boolean value) {
        super( name, value );
    }

    public Boolean get() {
        return value;
    }
}

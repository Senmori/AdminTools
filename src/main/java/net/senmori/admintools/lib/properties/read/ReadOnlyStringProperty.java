package net.senmori.admintools.lib.properties.read;

public class ReadOnlyStringProperty extends ReadOnlyProperty<String> {

    public ReadOnlyStringProperty() {
        super( null, "" );
    }

    public ReadOnlyStringProperty(final String value) {
        super( null, value );
    }

    public ReadOnlyStringProperty(final String name, final String value) {
        super( name, value );
    }
}

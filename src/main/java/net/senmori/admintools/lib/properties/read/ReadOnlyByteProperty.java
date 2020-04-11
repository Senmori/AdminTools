package net.senmori.admintools.lib.properties.read;

public class ReadOnlyByteProperty extends ReadOnlyProperty<Byte> {
    public ReadOnlyByteProperty() {
        super( null, ( byte ) 0 );
    }

    public ReadOnlyByteProperty(final byte value) {
        super( null, value );
    }

    public ReadOnlyByteProperty(final String name, final byte value) {
        super( name, value );
    }
}

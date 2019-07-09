package net.senmori.custommobs.lib.properties.read;

public class ReadOnlyByteProperty extends ReadOnlyProperty<Byte> {
    public ReadOnlyByteProperty() {
        super(null, null, (byte) 0);
    }

    public ReadOnlyByteProperty(final byte value) {
        super(null, null, value);
    }

    public ReadOnlyByteProperty(final Object bean,final String name, final byte value) {
        super(bean, name, value);
    }

    public byte get() {
        return value;
    }
}

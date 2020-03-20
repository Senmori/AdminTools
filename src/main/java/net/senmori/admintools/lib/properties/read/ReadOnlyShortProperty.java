package net.senmori.admintools.lib.properties.read;

public class ReadOnlyShortProperty extends ReadOnlyProperty<Short> {

    public ReadOnlyShortProperty() {
        super(null, null, (short) 0);
    }

    public ReadOnlyShortProperty(final short value) {
        super(null, null, value);
    }

    public ReadOnlyShortProperty(final Object bean, final String name, final short value) {
        super(bean, name, value);
    }

    public short get() {
        return value;
    }
}

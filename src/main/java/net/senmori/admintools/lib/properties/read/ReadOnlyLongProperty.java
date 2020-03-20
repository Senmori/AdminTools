package net.senmori.admintools.lib.properties.read;

public class ReadOnlyLongProperty extends ReadOnlyProperty<Long> {

    public ReadOnlyLongProperty() {
        super(null, null, 0L);
    }

    public ReadOnlyLongProperty(final long value) {
        super(null, null, value);
    }

    public ReadOnlyLongProperty(final Object bean,final String name, final long value) {
        super(bean, name, value);
    }

    public long get() {
        return value;
    }
}

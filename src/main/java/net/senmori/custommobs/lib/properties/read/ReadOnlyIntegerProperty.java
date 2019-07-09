package net.senmori.custommobs.lib.properties.read;

public class ReadOnlyIntegerProperty extends ReadOnlyProperty<Integer> {
    public ReadOnlyIntegerProperty() {
        super(null, null, 0);
    }

    public ReadOnlyIntegerProperty(final int value) {
        super(null, null, value);
    }

    public ReadOnlyIntegerProperty(final Object bean,final String name, final int value) {
        super(bean, name, value);
    }

    public int get() {
        return value;
    }
}

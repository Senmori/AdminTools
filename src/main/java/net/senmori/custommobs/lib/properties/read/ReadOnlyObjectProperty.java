package net.senmori.custommobs.lib.properties.read;

public class ReadOnlyObjectProperty<T> extends ReadOnlyProperty<T> {
    public ReadOnlyObjectProperty(T value) {
        this(null, null, value);
    }

    public ReadOnlyObjectProperty(final Object bean, final String name, final T value) {
        super(bean, name, value);
    }


    public T get() {
        return value;
    }
}

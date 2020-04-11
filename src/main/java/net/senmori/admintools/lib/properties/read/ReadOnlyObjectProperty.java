package net.senmori.admintools.lib.properties.read;

public class ReadOnlyObjectProperty<T> extends ReadOnlyProperty<T> {
    public ReadOnlyObjectProperty(T value) {
        this( null, value );
    }

    public ReadOnlyObjectProperty(final String name, final T value) {
        super( name, value );
    }

    public T get() {
        return value;
    }
}

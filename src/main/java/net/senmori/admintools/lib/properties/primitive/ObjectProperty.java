package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class ObjectProperty<T> extends DefaultProperty<T> {
    public ObjectProperty() {
        this( null, null );
    }

    public ObjectProperty(final T value) {
        this( null, value );
    }

    public ObjectProperty(final String name, final T value) {
        super( name, value );
    }
}

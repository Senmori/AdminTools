package net.senmori.admintools.lib.properties;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.read.ReadOnlyProperty;

public abstract class Property<T> extends ReadOnlyProperty<T> {

    protected Property() {
        super( null, null );
    }

    protected Property(T value) {
        super( null, value );
    }

    protected Property(final String name, final T value) {
        super( name, value );
    }

    protected void setValue(final T value) {
        final T old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(T value) {
        setValue( value );
    }
}

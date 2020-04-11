package net.senmori.admintools.lib.properties.defaults;

import net.senmori.admintools.lib.properties.Property;
import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.event.InvalidValueEvent;

public abstract class DefaultProperty<T> extends Property<T> {

    private final T defaultValue;

    protected DefaultProperty() {
        super( null, null );
        this.defaultValue = null;
    }

    protected DefaultProperty(T value) {
        super( null, value );
        this.defaultValue = value;
    }

    protected DefaultProperty(String name, T value) {
        super( name, value );
        this.defaultValue = value;
    }

    @Override
    protected void setValue(T value) {
        if ( value == null ) {
            fireEvent( new InvalidValueEvent<>( this, this.value, value ) );
            value = getDefault();
        }
        final T old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public T getDefault() {
        return this.defaultValue;
    }
}

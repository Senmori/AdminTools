package net.senmori.custommobs.lib.properties.primitive;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyObjectProperty;

public class ObjectProperty<T> extends ReadOnlyObjectProperty<T> {
    public ObjectProperty() {
        this(null, null, null);
    }

    public ObjectProperty(final T value) {
        this(null, null, value);
    }

    public ObjectProperty(final Object bean, final String name, final T value) {
        super(bean, name, value);
    }

    protected void setValue(final T value) {
        final T old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final T value) {
        setValue( value );
    }
}

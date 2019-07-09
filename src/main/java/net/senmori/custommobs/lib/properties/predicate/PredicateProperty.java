package net.senmori.custommobs.lib.properties.predicate;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyObjectProperty;

import java.util.function.Predicate;

public class PredicateProperty<T> extends ReadOnlyObjectProperty<Predicate<T>> {

    public PredicateProperty() {
        this(s -> true);
    }

    public PredicateProperty(final Predicate<T> value) {
        this( null, null, value );
    }

    public PredicateProperty(final Object bean, final String name, final Predicate<T> value) {
        super( bean, name, value );
    }

    protected void setValue(final Predicate<T> value) {
        final Predicate<T> old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final Predicate<T> value) {
        setValue( value );
    }
}

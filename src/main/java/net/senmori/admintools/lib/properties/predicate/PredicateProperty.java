package net.senmori.admintools.lib.properties.predicate;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.Predicate;

public class PredicateProperty<T> extends ObjectProperty<Predicate<T>> {

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
}

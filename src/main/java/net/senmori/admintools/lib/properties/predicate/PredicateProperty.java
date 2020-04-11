package net.senmori.admintools.lib.properties.predicate;

import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.Predicate;

public class PredicateProperty<T> extends ObjectProperty<Predicate<T>> {

    public PredicateProperty() {
        super( s -> false );
    }

    private PredicateProperty(final Predicate<T> value) {
        super( null, value );
    }

    private PredicateProperty(final String name, final Predicate<T> value) {
        super( name, value );
    }

    public static <T> PredicateProperty<T> of(final Predicate<T> value) {
        return new PredicateProperty<T>( value );
    }

    public static <T> PredicateProperty<T> of(final String name, final Predicate<T> value) {
        return new PredicateProperty<T>( name, value );
    }
}

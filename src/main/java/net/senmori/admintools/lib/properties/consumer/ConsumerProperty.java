package net.senmori.admintools.lib.properties.consumer;

import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.Consumer;

public class ConsumerProperty<T> extends ObjectProperty<Consumer<T>> {

    public ConsumerProperty() {
        this( s -> {
        } );
    }

    private ConsumerProperty(final Consumer<T> value) {
        super( null, value );
    }

    private ConsumerProperty(final String name, final Consumer<T> value) {
        super( name, value );
    }

    public static <T> ConsumerProperty<T> of(String name) {
        return new ConsumerProperty<>( name, s -> {} );
    }

    public static <T> ConsumerProperty<T> of(final Consumer<T> value) {
        return new ConsumerProperty<T>( value );
    }

    public static <T> ConsumerProperty<T> of(final String name, final Consumer<T> value) {
        return new ConsumerProperty<T>( name, value );
    }
}

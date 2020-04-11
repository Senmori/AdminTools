package net.senmori.admintools.lib.properties.consumer;

import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.BiConsumer;

public class BiConsumerProperty<T, U> extends ObjectProperty<BiConsumer<T, U>> {

    private BiConsumerProperty(final BiConsumer<T, U> consumer) {
        super( null, consumer );
    }

    private BiConsumerProperty(final String name, final BiConsumer<T, U> value) {
        super( name, value );
    }

    public static <T, U> BiConsumerProperty<T, U> of(final BiConsumer<T, U> consumer) {
        return new BiConsumerProperty<T, U>( consumer );
    }

    public static <T, U> BiConsumerProperty<T, U> of(final String name, final BiConsumer<T, U> value) {
        return new BiConsumerProperty<T, U>( name, value );
    }
}

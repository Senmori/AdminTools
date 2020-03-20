package net.senmori.admintools.lib.properties.consumer;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.Consumer;

public class ConsumerProperty<T> extends ObjectProperty<Consumer<T>> {

    public ConsumerProperty() {
        this(s -> {});
    }

    public ConsumerProperty(final Consumer<T> value) {
        this( null, null, value );
    }

    public ConsumerProperty(final Object bean, final String name) {
        this(bean, name, s -> {});
    }

    public ConsumerProperty(final Object bean, final String name, final Consumer<T> value) {
        super( bean, name, value );
    }

    protected void setValue(final Consumer<T> value) {
        final Consumer<T> old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    @Override
    public Consumer<T> get() {
        return (Consumer<T>) this.value;
    }
}

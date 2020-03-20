package net.senmori.admintools.lib.properties.consumer;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.BiConsumer;

public class BiConsumerProperty<T, U> extends ObjectProperty<BiConsumer<T, U>> {

    public BiConsumerProperty(final BiConsumer<T, U> consumer) {
        this(null, null, consumer);
    }

    public BiConsumerProperty(final Object bean, final String name, final BiConsumer<T, U> value) {
        super(bean, name, value);
    }


    @Override
    protected void setValue(BiConsumer<T, U> value) {
        final BiConsumer<T, U> old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    @Override
    public BiConsumer<T, U> get() {
        return (BiConsumer<T, U>) this.value;
    }
}

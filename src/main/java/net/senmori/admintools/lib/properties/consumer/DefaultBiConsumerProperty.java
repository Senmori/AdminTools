package net.senmori.admintools.lib.properties.consumer;

import net.senmori.admintools.lib.properties.event.ChangeEvent;

import java.util.function.BiConsumer;

public class DefaultBiConsumerProperty<T, U> extends BiConsumerProperty<T, U> {

    private final BiConsumer<T, U> defaultValue;

    public DefaultBiConsumerProperty(BiConsumer<T, U> consumer) {
        this( consumer, consumer );
    }

    public DefaultBiConsumerProperty(BiConsumer<T, U> value, BiConsumer<T, U> defaultValue) {
        this( null, null, value, defaultValue );
    }

    public DefaultBiConsumerProperty(Object bean, String name, BiConsumer<T, U> value) {
        this( bean, name, value, value );
    }

    public DefaultBiConsumerProperty(Object bean, String name, BiConsumer<T, U> value, BiConsumer<T, U> defaultValue) {
        super( bean, name, value );
        this.defaultValue = defaultValue;
    }

    public BiConsumer<T, U> getDefaultValue() {
        return defaultValue;
    }

    @Override
    protected void setValue(BiConsumer<T, U> value) {
        if (value == null) {
            value = getDefaultValue();
        }
        final BiConsumer<T, U> old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }
}

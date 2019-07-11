package net.senmori.custommobs.lib.properties.consumer;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;

import java.util.function.Consumer;

public class DefaultConsumerProperty<T> extends ConsumerProperty<T> {

    private final Consumer<T> defaultValue;

    public DefaultConsumerProperty() {
        this(s -> {});
    }

    public DefaultConsumerProperty(final Consumer<T> value) {
        this( null, null, value, value );
    }

    public DefaultConsumerProperty(final Object bean, final String name) {
        this(bean, name, s -> {}, s -> {});
    }

    public DefaultConsumerProperty(final Object bean, final String name, final Consumer<T> value, final Consumer<T> defaultValue) {
        super( bean, name, value );
        this.defaultValue = defaultValue;
    }

    public Consumer<T> getDefaultValue() {
        return defaultValue;
    }

    @Override
    protected void setValue(Consumer<T> value) {
        if (value == null) {
            value = getDefaultValue();
        }
        final Consumer<T> old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }
}

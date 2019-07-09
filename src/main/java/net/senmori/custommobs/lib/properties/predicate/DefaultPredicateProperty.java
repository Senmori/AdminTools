package net.senmori.custommobs.lib.properties.predicate;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.event.InvalidValueEvent;

import java.util.function.Predicate;

public class DefaultPredicateProperty<T> extends PredicateProperty<T> {

    private Predicate<T> defaultValue;

    public DefaultPredicateProperty() {
        this(s -> true);
    }

    public DefaultPredicateProperty(final Predicate<T> value) {
        this(null, null, value, value);
    }

    public DefaultPredicateProperty(final Object bean, final String name, final Predicate<T> value) {
        this(bean, name, value, value);
    }

    public DefaultPredicateProperty(final Object bean, final String name, final Predicate<T> value, final Predicate<T> defaultValue) {
        super(bean, name, value);
        this.defaultValue = defaultValue;
    }

    public Predicate<T> getDefaultValue() {
        return defaultValue;
    }

    @Override
    protected void setValue(Predicate<T> value) {
        if (value == null) {
            value = getDefaultValue();
        }
        final Predicate<T> old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }
}

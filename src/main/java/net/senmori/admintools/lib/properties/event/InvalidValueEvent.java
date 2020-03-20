package net.senmori.admintools.lib.properties.event;

/**
 * This event is called when a property has had it's value attempted to be changed
 * and was not valid.
 */
public class InvalidValueEvent<T> extends ChangeEvent<T> {
    public InvalidValueEvent(Object source, T currentValue, T value) {
        super( source, currentValue, value );
    }

    /**
     * Unlike other implementations of {@link ChangeEvent} this method returns the current value
     * for the bean.
     *
     * @return the current value of the bean
     */
    @Override
    public T getOldValue() {
        return super.getOldValue();
    }

    /**
     * Unlike other implementations of {@link ChangeEvent} this methods returns the value that
     * had been attempted to be set.
     *
     * @return the attempted new value for the bean
     */
    @Override
    public T getValue() {
        return super.getValue();
    }
}

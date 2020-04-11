package net.senmori.admintools.lib.properties.event;

/**
 * This event is called when a number was attempted to be set in a range property
 * but was not within the allowed range.
 */
public class NumberOutOfRangeEvent<T extends Comparable<? super T>> extends ChangeEvent<T> {
    public NumberOutOfRangeEvent(Object source, T currentValue, T value) {
        super( source, currentValue, value );
    }

    /**
     * Unlike other implementations of {@link ChangeEvent} this method returns the current value
     * of the bean.
     *
     * @return the current value of the bean
     */
    @Override
    public T getOldValue() {
        return super.getOldValue();
    }

    /**
     * Unlike other implementations of {@link ChangeEvent} this methods returns the value that
     * was attempted to be set.
     *
     * @return the attempted value
     */
    @Override
    public T getValue() {
        return super.getValue();
    }
}

package net.senmori.admintools.lib.properties.event;

public class ChangeEvent<T extends Object> {

    private final Object bean;
    private final T oldValue;
    private final T value;

    public ChangeEvent(final Object source, final T oldValue, final T value) {
        this.bean = source;
        this.oldValue = oldValue;
        this.value = value;
    }

    public Object getBean() {
        return bean;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getValue() {
        return value;
    }
}

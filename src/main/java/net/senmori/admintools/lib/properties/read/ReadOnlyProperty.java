package net.senmori.admintools.lib.properties.read;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.event.ChangeEventListener;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ReadOnlyProperty<T> {
    protected final CopyOnWriteArrayList<ChangeEventListener> listenerList = new CopyOnWriteArrayList<>();
    protected Object bean;
    protected String name;
    protected T value;

    protected ReadOnlyProperty() {
        this( null );
    }

    protected ReadOnlyProperty(T value) {
        this( null, value );
    }

    protected ReadOnlyProperty(final String name, final T value) {
        this.name = name;
        this.value = value;
    }

    public final T getValue() {
        return value;
    }

    public T get() {
        return getValue();
    }

    public String getName() {
        return name;
    }

    protected void invalidated() {

    }

    public void setOnPropertyChanged(final ChangeEventListener<?> listener) {
        addListener( listener );
    }

    public void addListener(final ChangeEventListener<?> listener) {
        if ( !listenerList.contains( listener ) ) {
            listenerList.add( listener );
        }
    }

    public void removeListener(final ChangeEventListener<?> listener) {
        if ( listenerList.contains( listener ) ) {
            listenerList.add( listener );
        }
    }

    public void removeAllListener() {
        listenerList.clear();
    }

    public void fireEvent(final ChangeEvent<?> event) {
        listenerList.forEach( listener -> listener.onChangeEvent( event ) );
    }

}

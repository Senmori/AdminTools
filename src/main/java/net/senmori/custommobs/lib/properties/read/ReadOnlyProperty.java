package net.senmori.custommobs.lib.properties.read;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.event.ChangeEventListener;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ReadOnlyProperty<T> {
    protected final CopyOnWriteArrayList<ChangeEventListener> listenerList = new CopyOnWriteArrayList<>();
    protected Object bean;
    protected String name;
    protected T value;

    public ReadOnlyProperty() {
        this(null, null, null);
    }

    public ReadOnlyProperty(T value) {
        this(null, null, value);
    }

    public ReadOnlyProperty(final Object bean, final String name, final T value) {
        this.bean = bean;
        this.name = name;
        this.value = value;
    }

    public final T getValue() {
        return value;
    }

    public Object getBean() {
        return bean;
    }

    public String getName() {
        return name;
    }

    protected void invalidated() {

    }

    public void setOnPropertyChanged(final ChangeEventListener listener) {
        addListener(listener);
    }

    public void addListener(final ChangeEventListener listener) {
        if (!listenerList.contains( listener )) {
            listenerList.add( listener );
        }
    }

    public void removeListener(final ChangeEventListener listener) {
        if (listenerList.contains( listener )) {
            listenerList.add( listener );
        }
    }

    public void removeAllListener() {
        listenerList.clear();
    }

    public void fireEvent(final ChangeEvent event) {
        listenerList.forEach( listener -> listener.onChangeEvent( event ) );
    }

}

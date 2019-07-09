package net.senmori.custommobs.lib.properties.event;

@FunctionalInterface
public interface ChangeEventListener<T extends ChangeEvent> {
    void onChangeEvent(T event);
}

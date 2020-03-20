package net.senmori.admintools.lib.properties.read;

public class ReadOnlyBooleanProperty extends ReadOnlyProperty<Boolean> {
    public ReadOnlyBooleanProperty() {
        super(null, null, false);
    }

    public ReadOnlyBooleanProperty(final boolean value) {
        super(null, null, value);
    }

    public ReadOnlyBooleanProperty(final Object bean,final String name, final boolean value) {
        super(bean, name, value);
    }

    public boolean get() {
        return value;
    }
}

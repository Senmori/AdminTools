package net.senmori.custommobs.lib.properties.read;

public class ReadOnlyCharProperty extends ReadOnlyProperty<Character> {
    public ReadOnlyCharProperty() {
        super(null, null, ' ');
    }

    public ReadOnlyCharProperty(final char value) {
        super(null, null, value);
    }

    public ReadOnlyCharProperty(final Object bean,final String name, final char value) {
        super(bean, name, value);
    }

    public char get() {
        return value;
    }
}

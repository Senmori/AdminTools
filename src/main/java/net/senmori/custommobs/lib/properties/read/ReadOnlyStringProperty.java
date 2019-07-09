package net.senmori.custommobs.lib.properties.read;

public class ReadOnlyStringProperty extends ReadOnlyProperty<String> {

    public ReadOnlyStringProperty() {
        super(null, null, "");
    }

    public ReadOnlyStringProperty(final String value) {
        super(null,null, value);
    }

    public ReadOnlyStringProperty(final Object bean, final String name, final String value) {
        super(bean, name, value);
    }

    public String get() {
        return value;
    }
}

package net.senmori.admintools.lib.properties.read;

public class ReadOnlyFloatProperty extends ReadOnlyProperty<Float> {
    public ReadOnlyFloatProperty() {
        super(null, null, 0.0F);
    }

    public ReadOnlyFloatProperty(final float value) {
        super(null, null, value);
    }

    public ReadOnlyFloatProperty(final Object bean,final String name, final float value) {
        super(bean, name, value);
    }

    public float get() {
        return value;
    }
}

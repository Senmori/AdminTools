package net.senmori.admintools.lib.properties.read;

public class ReadOnlyDoubleProperty extends ReadOnlyProperty<Double> {
    public ReadOnlyDoubleProperty() {
        super(null, null, 0.0D);
    }

    public ReadOnlyDoubleProperty(final double value) {
        super(null, null, value);
    }

    public ReadOnlyDoubleProperty(final Object bean,final String name, final double value) {
        super(bean, name, value);
    }

    public double get() {
        return value;
    }
}

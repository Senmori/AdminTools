package net.senmori.admintools.lib.properties.read;

public class ReadOnlyDoubleProperty extends ReadOnlyProperty<Double> {
    public ReadOnlyDoubleProperty() {
        super( null, 0.0D );
    }

    public ReadOnlyDoubleProperty(final double value) {
        super( null, value );
    }

    public ReadOnlyDoubleProperty(final String name, final double value) {
        super( name, value );
    }
}

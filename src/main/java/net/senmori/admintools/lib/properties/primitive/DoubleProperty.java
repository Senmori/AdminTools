package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class DoubleProperty extends DefaultProperty<Double> {
    public DoubleProperty() {
        super( null, 0.0D );
    }

    public DoubleProperty(final double value) {
        super( null, value );
    }

    public DoubleProperty(final String name, final double value) {
        super( name, value );
    }
}

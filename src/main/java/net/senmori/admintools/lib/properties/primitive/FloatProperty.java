package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class FloatProperty extends DefaultProperty<Float> {
    public FloatProperty() {
        super( null, 0.0F );
    }

    public FloatProperty(final float value) {
        super( null, value );
    }

    public FloatProperty(final String name, final float value) {
        super( name, value );
    }
}

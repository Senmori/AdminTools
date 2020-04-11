package net.senmori.admintools.lib.properties.read;

public class ReadOnlyFloatProperty extends ReadOnlyProperty<Float> {
    public ReadOnlyFloatProperty() {
        super( null, 0.0F );
    }

    public ReadOnlyFloatProperty(final float value) {
        super( null, value );
    }

    public ReadOnlyFloatProperty(final String name, final float value) {
        super( name, value );
    }
}

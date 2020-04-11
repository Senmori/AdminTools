package net.senmori.admintools.lib.properties.color;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

import java.awt.Color;

public class ColorProperty extends DefaultProperty<Color> {

    private ColorProperty(Color color) {
        super( null, color );
    }

    private ColorProperty(final String name, final Color color) {
        super( name, color );
    }

    public static ColorProperty of(final String name, final Color color) {
        return new ColorProperty( name, color );
    }

    public static ColorProperty of(Color color) {
        return new ColorProperty( color );
    }
}

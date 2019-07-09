package net.senmori.custommobs.lib.properties.color;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.event.InvalidValueEvent;

import java.awt.Color;

public class DefaultColorProperty extends ColorProperty {

    private final Color defaultValue;

    public DefaultColorProperty(Color color) {
        this( color, color );
    }

    public DefaultColorProperty(final Color color, final Color defaultColor) {
        this( null, null, color, defaultColor );
    }

    public DefaultColorProperty(Object bean, String name, Color color) {
        this( bean, name, color, color );
    }

    public DefaultColorProperty(Object bean, String name, Color color, Color defaultColor) {
        super( bean, name, color );
        this.defaultValue = defaultColor;
    }

    public Color getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setValue(Color color) {
        if ( color == null ) {
            fireEvent( new InvalidValueEvent<>(this, this.value, color) );
            color = getDefaultValue();
        }
        final Color old = this.value;
        this.value = color;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }
}

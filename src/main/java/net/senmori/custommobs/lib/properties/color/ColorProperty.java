package net.senmori.custommobs.lib.properties.color;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyProperty;

import java.awt.Color;

public class ColorProperty extends ReadOnlyProperty<Color> {

    public ColorProperty(Color color) {
        super(null, null, color);
    }

    public ColorProperty(final Object bean, final String name, final Color color) {
        super(bean, name, color);
    }

    protected void setValue(Color color) {
        final Color old = this.value;
        this.value = color;
        invalidated();
        fireEvent( new ChangeEvent<>(this, old, this.value) );
    }

    public void set(Color color) {
        setValue( color );
    }

    public Color get() {
        return this.value;
    }
}

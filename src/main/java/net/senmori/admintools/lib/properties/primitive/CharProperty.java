package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.read.ReadOnlyCharProperty;

public class CharProperty extends ReadOnlyCharProperty {
    public CharProperty() {
        super(null, null, ' ');
    }

    public CharProperty(final char value) {
        super(null, null, value);
    }

    public CharProperty(final Object bean, final String name, final char value) {
        super(bean, name, value);
    }

    protected void setValue(final char value) {
        final char old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final char value) {
        setValue( value );
    }
}

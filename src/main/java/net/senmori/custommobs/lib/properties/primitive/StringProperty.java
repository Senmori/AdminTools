package net.senmori.custommobs.lib.properties.primitive;

import net.senmori.custommobs.lib.properties.event.ChangeEvent;
import net.senmori.custommobs.lib.properties.read.ReadOnlyStringProperty;

public class StringProperty extends ReadOnlyStringProperty {
    public StringProperty() {
        super(null, null, "");
    }

    public StringProperty(final String value) {
        super(null, null, value);
    }

    public StringProperty(final Object bean, final String name, final String value) {
        super(bean, name, value);
    }

    protected void setValue(final String value) {
        final String old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    public void set(final String value) {
        setValue( value );
    }
}

package net.senmori.admintools.lib.properties.primitive;

import net.senmori.admintools.lib.properties.defaults.DefaultProperty;

public class LongProperty extends DefaultProperty<Long> {
    public LongProperty() {
        super( null, 0L );
    }

    public LongProperty(final long value) {
        super( null, value );
    }

    public LongProperty(final String name, final long value) {
        super( name, value );
    }
}

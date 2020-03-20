package net.senmori.admintools.lib.properties.range;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.event.NumberOutOfRangeEvent;
import net.senmori.admintools.lib.properties.primitive.IntegerProperty;
import net.senmori.admintools.lib.util.ValueRange;

public class IntegerRangeProperty extends IntegerProperty {

    private final ValueRange<Integer> valueRange;

    public IntegerRangeProperty(int value, int min, int max) {
        this(null, null,  value, min, max);
    }

    public IntegerRangeProperty(final Object bean, final String name, int value, int min, int max) {
        super(bean, name, value);
        this.valueRange = new ValueRange<>( Integer.class, min, max );
    }

    public ValueRange<Integer> getValueRange() {
        return valueRange;
    }

    @Override
    protected void setValue(int value) {
        if (!valueRange.test( value )) {
            fireEvent( new NumberOutOfRangeEvent<>( this, this.value, value ) );
            return;
        }
        final int old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }
}

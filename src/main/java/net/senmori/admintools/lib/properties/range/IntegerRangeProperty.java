package net.senmori.admintools.lib.properties.range;

import net.senmori.admintools.lib.properties.event.ChangeEvent;
import net.senmori.admintools.lib.properties.event.NumberOutOfRangeEvent;
import net.senmori.admintools.lib.properties.primitive.IntegerProperty;
import net.senmori.admintools.lib.util.ValueRange;

import java.util.function.Predicate;

public class IntegerRangeProperty extends IntegerProperty implements Predicate<Object> {

    private final ValueRange<Integer> valueRange;

    public IntegerRangeProperty(int value, int min, int max) {
        this( null, null, value, min, max );
    }

    public IntegerRangeProperty(final String name, int min, int max) {
        super( name, min );
        this.valueRange = new ValueRange<>( Integer.class, min, max );
    }

    public IntegerRangeProperty(final Object bean, final String name, int value, int min, int max) {
        super( name, value );
        this.valueRange = new ValueRange<>( Integer.class, min, max );
    }

    public ValueRange<Integer> getValueRange() {
        return valueRange;
    }

    public void set(int value) {
        setValue( value );
    }

    @Override
    protected void setValue(Integer value) {
        if ( !valueRange.test( value ) ) {
            fireEvent( new NumberOutOfRangeEvent<>( this, this.value, value ) );
            return;
        }
        final int old = this.value;
        this.value = value;
        invalidated();
        fireEvent( new ChangeEvent<>( this, old, this.value ) );
    }

    private boolean isNumber(Object object) {
        return object instanceof Number;
    }

    @Override
    public boolean test(Object object) {
        if (isNumber( object )) {
            return valueRange.test( ((Number)object).intValue() );
        }
        return false;
    }
}

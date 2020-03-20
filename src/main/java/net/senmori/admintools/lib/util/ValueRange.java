package net.senmori.admintools.lib.util;

import java.util.function.Predicate;

public final class ValueRange<V extends Comparable<? super V>> implements Predicate<V> {

    private final Class<? extends V> clazz;
    private final V min;
    private final V max;

    public ValueRange(Class<V> clazz, V min, V max) {
        this.clazz = clazz;
        this.min = min;
        this.max = max;
    }

    public Class<? extends V> getClazz() {
        return clazz;
    }

    public V getMin() {
        return min;
    }

    public V getMax() {
        return max;
    }

    private boolean isNumber(Object other) {
        return Number.class.isAssignableFrom( clazz ) && other instanceof Number;
    }

    @Override
    public boolean test(V object) {
        if ( isNumber( object ) ) {
            Number n = (Number) object;
            return ((Number)min).doubleValue() <= n.doubleValue() && n.doubleValue() <= ((Number)max).doubleValue();
        }
        if (!clazz.isInstance( object )) return false;
        V other = clazz.cast( object );
        return other.compareTo( min ) >= 0 && other.compareTo( max ) <= 0;
    }

    public String toString() {
        return min + " ~ " + max;
    }
}

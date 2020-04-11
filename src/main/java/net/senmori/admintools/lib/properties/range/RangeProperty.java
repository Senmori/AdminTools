package net.senmori.admintools.lib.properties.range;

public final class RangeProperty {

    public static IntegerRangeProperty of(String name, int min, int max) {
        return new IntegerRangeProperty( name, min, max );
    }

    public static IntegerRangeProperty of(int min, int max) {
        return new IntegerRangeProperty( min, min, max );
    }

    public static IntegerRangeProperty of(int defaultValue, int min, int max) {
        return new IntegerRangeProperty( defaultValue, min, max );
    }
}

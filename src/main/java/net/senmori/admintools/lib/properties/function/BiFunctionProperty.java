package net.senmori.admintools.lib.properties.function;

import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.BiFunction;

public class BiFunctionProperty<T, U, R> extends ObjectProperty<BiFunction<T, U, R>> {

    private BiFunctionProperty() {
        super (null);
    }

    private BiFunctionProperty(BiFunction<T, U, R> func) {
        super (null, func );
    }

    private BiFunctionProperty(String name, BiFunction<T, U, R> func) {
        super(name, func);
    }

    public static <T, U, R> BiFunctionProperty<T, U, R> of(BiFunction<T, U, R> func) {
        return new BiFunctionProperty<T, U, R>( func );
    }

    public static <T, U, R> BiFunctionProperty<T, U, R> of(String name, BiFunction<T, U, R> func) {
        return new BiFunctionProperty<T, U, R>( name, func );
    }
}

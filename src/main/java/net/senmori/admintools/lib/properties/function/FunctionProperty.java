package net.senmori.admintools.lib.properties.function;

import net.senmori.admintools.lib.properties.primitive.ObjectProperty;

import java.util.function.Function;

public class FunctionProperty<T, R> extends ObjectProperty<Function<T, R>> {

    private FunctionProperty() {
        super( null );
    }

    private FunctionProperty(Function<T, R> func) {
        super( null, func );
    }

    private FunctionProperty(String name, Function<T, R> func) {
        super( name, func );
    }

    public static <T, R> FunctionProperty<T, R> of(String name, Function<T, R> function) {
        return new FunctionProperty<>(name, function);
    }

    public static <T, R> FunctionProperty<T, R> of(Function<T, R> function) {
        return new FunctionProperty<>(function);
    }
}

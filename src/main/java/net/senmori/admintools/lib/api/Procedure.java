package net.senmori.admintools.lib.api;

import javax.annotation.Nullable;

/**
 * Similar to {@link java.util.function.Function} but takes no parameters.
 * Can return null.
 */
public interface Procedure<T> {
    /**
     * Execute this procedure.
     *
     * @return the result
     */
    @Nullable
    T execute();
}

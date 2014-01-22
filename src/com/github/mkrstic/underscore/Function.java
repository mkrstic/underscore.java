package com.github.mkrstic.underscore;

/**
 * Created by Mladen on 1/19/14.
 */
public interface Function<T> {
    T apply();

    @Override
    boolean equals(Object object);
}

package com.epam.utils;

/**
 * Created by oleksandr_kramskyi on 5/27/2016.
 */
public interface IPredicate<T, V> {
    boolean apply(T type, V value);
}

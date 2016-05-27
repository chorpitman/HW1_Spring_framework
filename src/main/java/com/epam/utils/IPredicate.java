package com.epam.utils;

public interface IPredicate<T, V> {
    boolean apply(T entity, V value);
}

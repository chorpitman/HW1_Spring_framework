package com.epam.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by oleksandr_kramskyi on 5/27/2016.
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    public static <T, V> List<T> filter(Collection<Object> target, V value, Class<T> clazz, IPredicate<T, V> predicate) {
        List<T> result = new LinkedList<T>();
        for (Object element : target) {
            if (clazz.isAssignableFrom(element.getClass())) {
                T entity = (T) element;
                if (predicate.apply(entity, value)) {
                    result.add(entity);
                }
            }
        }
        return result;
    }
}

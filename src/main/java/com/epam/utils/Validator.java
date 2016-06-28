package com.epam.utils;

public class Validator {
    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("wrong: parameter can not be null");
        }
    }

    public static void checkExpression(boolean expression, String msg) {
        if (expression) {
            throw new IllegalArgumentException("wrong parameter: " + msg);
        }
    }
}

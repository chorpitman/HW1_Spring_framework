package com.epam.utils;

public final class Validator {

    private Validator() {
    }

    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("wrong: parameter can not be null");
        }
    }

    static void checkExpression(boolean expression, String msg) {
        if (expression) {
            throw new IllegalArgumentException("wrong parameter: " + msg);
        }
    }
}
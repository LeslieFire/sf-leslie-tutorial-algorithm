package com.seefeel.leslie.algorithm.util;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 14/3/17
 * Time: AM11:57
 */
public class Precondition {

    public static final void checkArgument(boolean bo, String message) {
        if (!bo){
            throw new IllegalArgumentException(message);
        }
    }
}

package com.gosalelab.generator;

public interface KeyGenerator {
    String getKey(String keyExpression, Object[] arguments, Object returnVal);
}

package com.rules.engine.dslResolver;

public interface DSLResolver {
    String getResolverKeyword();
    Object resolveValue(String keyword);
}

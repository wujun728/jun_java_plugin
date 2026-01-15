package io.github.wujun728.sql.node;

import java.util.Set;

import io.github.wujun728.sql.context.Context;


public interface SqlNode {

    void apply(Context context);

    void applyParameter(Set<String> set);

}

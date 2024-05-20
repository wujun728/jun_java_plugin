package com.jun.plugin.sql.node;

import java.util.Set;

import com.jun.plugin.sql.context.Context;


public interface SqlNode {

    void apply(Context context);

    void applyParameter(Set<String> set);

}

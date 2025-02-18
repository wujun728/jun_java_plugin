package io.github.wujun728.sql.node;

import java.util.List;
import java.util.Set;

import io.github.wujun728.sql.context.Context;


public class MixedSqlNode implements SqlNode {

    List<SqlNode> contents ;

    public MixedSqlNode(List<SqlNode> contents) {
        this.contents = contents;
    }

    @Override
    public void apply(Context context) {
        for (SqlNode node: contents){
            node.apply(context);
        }
    }

    @Override
    public void applyParameter(Set<String> set) {
        for (SqlNode node: contents){
            node.applyParameter(set);
        }
    }
}

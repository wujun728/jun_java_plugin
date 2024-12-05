package io.github.wujun728.sql.engine;

import java.util.concurrent.ConcurrentHashMap;

import io.github.wujun728.sql.node.SqlNode;


public class Cache {

    ConcurrentHashMap<String, SqlNode> nodeCache = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, SqlNode> getNodeCache() {
        return nodeCache;
    }
}

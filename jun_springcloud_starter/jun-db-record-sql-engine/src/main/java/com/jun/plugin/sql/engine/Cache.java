package com.jun.plugin.sql.engine;

import java.util.concurrent.ConcurrentHashMap;

import com.jun.plugin.sql.node.SqlNode;


public class Cache {

    ConcurrentHashMap<String, SqlNode> nodeCache = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, SqlNode> getNodeCache() {
        return nodeCache;
    }
}

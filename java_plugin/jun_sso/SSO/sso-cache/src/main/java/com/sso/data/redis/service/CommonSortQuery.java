package com.sso.data.redis.service;

import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.util.StringUtils;

import java.util.List;

public class CommonSortQuery implements SortQuery<String> {
    private final String key;
    private final int start;
    private final int count;
    private final String by;
    private final String direction;

    public CommonSortQuery(String key, int start, int count, String by, String direction) {
        this.key = key;
        this.start = start;
        this.count = count;
        this.by = by;
        this.direction = direction;
    }

    @Override
    public SortParameters.Order getOrder() {
        return StringUtils.isEmpty(direction) ? null : SortParameters.Order.valueOf(direction);
    }

    @Override
    public Boolean isAlphabetic() {
        return true;
    }

    @Override
    public SortParameters.Range getLimit() {
        return new SortParameters.Range(start, count);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getBy() {
        return by;
    }

    @Override
    public List<String> getGetPattern() {
        return null;
    }
}

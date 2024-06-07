package com.jun.plugin.rest.handler;

import java.util.List;

public abstract class RestEntityExecHandler {

    public abstract String entityName();

    public void beforeCreate() {

    }

    public void afterCreate() {
    }

    public void beforeUpdate() {
    }

    public void afterUpdate() {
    }

    public void beforeDelete() {
    }

    public void afterDelete() {
    }

    public void afterQuery() {
    }

    public void afterQuery(List<?> list) {
    }
}

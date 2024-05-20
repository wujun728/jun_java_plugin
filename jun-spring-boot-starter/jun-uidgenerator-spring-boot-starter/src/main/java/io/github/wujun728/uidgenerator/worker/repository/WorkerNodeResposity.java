package io.github.wujun728.uidgenerator.worker.repository;


import io.github.wujun728.uidgenerator.worker.entity.WorkerNodeEntity;

public interface WorkerNodeResposity {
    WorkerNodeEntity getWorkerNodeByHostPort(String host, String port);

    void addWorkerNode(WorkerNodeEntity entity);
}

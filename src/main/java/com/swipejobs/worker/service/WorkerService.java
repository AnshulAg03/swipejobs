package com.swipejobs.worker.service;

import com.swipejobs.worker.model.Worker;

public interface WorkerService {

    Worker getWorker(Long workerId) throws Exception;

    void updateWorkerCache();
}

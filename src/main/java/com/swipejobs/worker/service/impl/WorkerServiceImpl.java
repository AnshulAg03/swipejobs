package com.swipejobs.worker.service.impl;

import com.swipejobs.worker.exception.InactiveWorkerException;
import com.swipejobs.worker.exception.WorkerNotFoundException;
import com.swipejobs.worker.model.Worker;
import com.swipejobs.worker.service.JobService;
import com.swipejobs.worker.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Slf4j
public class WorkerServiceImpl implements WorkerService {

    private static final Logger LOGGER=Logger.getLogger("WorkerServiceImpl.class");

    @Autowired
    private JobService jobService;

    @Autowired
    private RestTemplateImpl restTemplate;

    @Value("${workers.api.url}")
    private String workerUrl;

    private List<Worker> workersCache = new ArrayList();

    @Override
    public Worker getWorker(Long workerId) throws Exception {
        if(this.workersCache.isEmpty()){
            LOGGER.info("Worker Cache is empty.");
            this.updateWorkerCache();
        }
        Worker worker = this.workersCache.stream().filter(worker1-> worker1.getUserId() == workerId).findFirst().orElseThrow(()-> new WorkerNotFoundException());
        if(!worker.isActive()){
            LOGGER.info(String.format("Worker [%s] is not active.", worker.getUserId()));
            throw new InactiveWorkerException();
        }
        LOGGER.info(String.format("Worker [%s] found.", worker.getUserId()));
        return worker;
    }

    @Override
    public void updateWorkerCache() {
        LOGGER.info("Calling Workers API to getWorkers workers list.");
        try {
            this.workersCache = restTemplate.getWorkers(workerUrl);
        } catch(Exception e){
            LOGGER.warning(String.format("Error occurred while calling %s", workerUrl));
            this.workersCache = new ArrayList();
        }

    }
}

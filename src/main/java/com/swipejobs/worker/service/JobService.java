package com.swipejobs.worker.service;

import com.swipejobs.worker.model.Job;

import java.util.List;

public interface JobService {

    void updateJobCache();

    List<Job> getJobsForWorker(Long workerId) throws Exception;
}

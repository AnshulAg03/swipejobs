package com.swipejobs.worker.service.impl;

import com.swipejobs.worker.model.Job;
import com.swipejobs.worker.model.Worker;
import com.swipejobs.worker.service.JobService;
import com.swipejobs.worker.service.WorkerService;
import com.swipejobs.worker.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JobServiceImplTest {

    @Autowired
    JobService jobService;

    @MockBean
    WorkerService workerService;

    @MockBean
    RestTemplateImpl restTemplate;

    @BeforeAll
    public void setup() throws IOException {
        List<Job> jobList = JsonUtil.jsonArrayToList("jobs.json", Job.class);
        Mockito.when(restTemplate.getResponse(Mockito.any())).thenReturn(jobList);
        jobService.updateJobCache();
    }

    @Test
    void testGetJobForWorker() throws Exception {
        Worker worker = JsonUtil.jsonArrayToList("workers.json", Worker.class).get(0);
        Mockito.when(workerService.getWorker(Mockito.anyLong())).thenReturn(worker);
        List<Job> jobs = jobService.getJobsForWorker(1L);
        Assertions.assertEquals(3, jobs.size());
        Assertions.assertEquals("8,13,37", jobs.stream().map(Job::getJobId).map(String::valueOf).collect(Collectors.joining(",")));
        Assertions.assertTrue(jobs.stream().allMatch(job-> !Collections.disjoint(job.getRequiredCertificates(), worker.getCertificates())));
    }

    @Test
    void testGetJobForWorkerWithoutDrivingLicense() throws Exception {
        Worker worker = JsonUtil.jsonArrayToList("workers.json", Worker.class).get(5);
        Mockito.when(workerService.getWorker(Mockito.anyLong())).thenReturn(worker);
        List<Job> jobs = jobService.getJobsForWorker(5L);
        Assertions.assertEquals(1, jobs.size());
        Assertions.assertEquals("26", jobs.stream().map(Job::getJobId).map(String::valueOf).collect(Collectors.joining(",")));
        Assertions.assertTrue(jobs.stream().allMatch(job-> !Collections.disjoint(job.getRequiredCertificates(), worker.getCertificates())));
        Assertions.assertTrue(jobs.stream().noneMatch(Job::isDriverLicenseRequired));
    }

    @Test
    void testGetJobForWorkerWithoutCertificates() throws Exception {
        Worker worker = JsonUtil.jsonArrayToList("workers.json", Worker.class).get(0);
        worker.setCertificates(new ArrayList());
        Mockito.when(workerService.getWorker(Mockito.anyLong())).thenReturn(worker);
        List<Job> jobs = jobService.getJobsForWorker(0L);
        Assertions.assertEquals(0, jobs.size());
    }

    @Test
    void testGetJobForWorkerWithoutSkills() throws Exception {
        Worker worker = JsonUtil.jsonArrayToList("workers.json", Worker.class).get(0);
        worker.setSkills(new ArrayList());
        Mockito.when(workerService.getWorker(Mockito.anyLong())).thenReturn(worker);
        List<Job> jobs = jobService.getJobsForWorker(0L);
        Assertions.assertEquals(0, jobs.size());
    }

    @Test
    void testGetJobForWorkerWithoutAvalibility() throws Exception {
        Worker worker = JsonUtil.jsonArrayToList("workers.json", Worker.class).get(0);
        worker.setAvailability(new ArrayList());
        Mockito.when(workerService.getWorker(Mockito.anyLong())).thenReturn(worker);
        List<Job> jobs = jobService.getJobsForWorker(0L);
        Assertions.assertEquals(0, jobs.size());
    }

}

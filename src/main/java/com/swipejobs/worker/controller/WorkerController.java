package com.swipejobs.worker.controller;


import com.swipejobs.worker.dto.Response;
import com.swipejobs.worker.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/worker")
@RestController
public class WorkerController {

    @Autowired
    private JobService jobService;

    @GetMapping("/{workerId}/jobs")
    private ResponseEntity getJobsForWorker(@PathVariable Long workerId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(new Response(jobService.getJobsForWorker(workerId)));
    }
}

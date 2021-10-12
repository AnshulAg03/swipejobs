package com.swipejobs.worker.service.impl;

import com.swipejobs.worker.model.Job;
import com.swipejobs.worker.model.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class RestTemplateImpl{

    @Autowired
    private RestTemplate restTemplate;

    public List<Worker> getWorkers(String url ){
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Worker>>() {
        }).getBody();
    }

    public List<Job> getJobs(String url ){
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>() {
        }).getBody();
    }
}

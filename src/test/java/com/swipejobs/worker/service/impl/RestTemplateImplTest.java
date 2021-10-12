package com.swipejobs.worker.service.impl;

import com.swipejobs.worker.model.Job;
import com.swipejobs.worker.model.Worker;
import com.swipejobs.worker.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class RestTemplateImplTest {

    @Autowired
    RestTemplateImpl restTemplateImpl;

    @MockBean
    RestTemplate restTemplate;

    @Test
    void testGetWorkers() throws IOException {
        List<Worker> workerList = JsonUtil.jsonArrayToList("workers.json", Worker.class);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok(workerList));
        List<Worker> response = restTemplateImpl.getWorkers("url");
        Assertions.assertEquals(50, response.size());
    }

    @Test
    void testGetWorkersException(){
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        try {
            restTemplateImpl.getWorkers("url");
            Assertions.fail();
        } catch(Exception e){
            Assertions.assertEquals("RestClientException", e.getClass().getSimpleName());
        }
    }

    @Test
    void testGetJobs() throws IOException {
        List<Job> jobList = JsonUtil.jsonArrayToList("jobs.json", Job.class);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok(jobList));
        List<Job> response = restTemplateImpl.getJobs("url");
        Assertions.assertEquals(40, response.size());
    }

    @Test
    void testGetJobsException(){
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);
        try {
            restTemplateImpl.getJobs("url");
            Assertions.fail();
        } catch(Exception e){
            Assertions.assertEquals("RestClientException", e.getClass().getSimpleName());
        }
    }
}

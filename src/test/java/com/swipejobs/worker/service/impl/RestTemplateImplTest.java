package com.swipejobs.worker.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class RestTemplateImplTest {

    @Autowired
    RestTemplateImpl restTemplateImpl;

    @MockBean
    RestTemplate restTemplate;

    @Test
    void testGetResponse(){
        ResponseEntity responseEntity = ResponseEntity.ok("Response");
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), (Class<Object>) Mockito.any())).thenReturn(responseEntity);

        String response = (String) restTemplateImpl.getResponse("url");
        Assertions.assertEquals("Response", response);
    }

    @Test
    void testGetResponseForException(){
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), (Class<Object>) Mockito.any())).thenThrow(Exception.class);

        try {
            restTemplateImpl.getResponse("url");
            Assertions.fail();
        } catch(Exception e){

        }

    }
}

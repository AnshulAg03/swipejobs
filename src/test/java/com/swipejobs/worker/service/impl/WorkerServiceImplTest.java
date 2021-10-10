package com.swipejobs.worker.service.impl;

import com.swipejobs.worker.model.Worker;
import com.swipejobs.worker.service.WorkerService;
import com.swipejobs.worker.util.JsonUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WorkerServiceImplTest {

    @Autowired
    WorkerService workerService;

    @MockBean
    RestTemplateImpl restTemplate;

    @BeforeAll
    public void setup() throws IOException {
        List<Worker> workerList = JsonUtil.jsonArrayToList("workers.json", Worker.class);
        Mockito.when(restTemplate.getResponse(Mockito.any())).thenReturn(workerList);
        workerService.updateWorkerCache();
    }

    @Test
    @DisplayName("Test throws Exception when Worker External API is not available")
    void testGetWorkerWithNoWorker(){
        try {
            workerService.getWorker(50L);
            Assertions.fail();
        } catch(Exception e){
            Assertions.assertEquals("WorkerNotFoundException", e.getClass().getSimpleName());
            Assertions.assertEquals("Worker not found.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test throws Exception when Worker is inactive")
    void testGetWorkerWithInactiveWorker(){
        try {
            workerService.getWorker(1L);
            Assertions.fail();
        } catch(Exception e){
            Assertions.assertEquals("InactiveWorkerException", e.getClass().getSimpleName());
            Assertions.assertEquals("Worker is not active.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test getResponse worker")
    void testGetWorker(){
        try {
            Worker worker = workerService.getWorker(0L);
            Assertions.assertEquals("562f6647410ecd6bf49146e9", worker.getGuid());
            Assertions.assertTrue(worker.isActive());
            Assertions.assertFalse(worker.isHasDriversLicense());
        } catch(Exception e){
            Assertions.fail();
        }
    }
}

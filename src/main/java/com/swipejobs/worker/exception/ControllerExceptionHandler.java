package com.swipejobs.worker.exception;


import com.swipejobs.worker.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InactiveWorkerException.class)
    protected ResponseEntity handleInactiveWorkerException(InactiveWorkerException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(e.getMessage()));
    }

    @ExceptionHandler(WorkerNotFoundException.class)
    protected ResponseEntity handleWorkerNotFoundException(WorkerNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(e.getMessage()));
    }
}

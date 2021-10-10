package com.swipejobs.worker.exception;

public class InactiveWorkerException extends Exception {

    public InactiveWorkerException(){
        super("Worker is not active.");
    }
}

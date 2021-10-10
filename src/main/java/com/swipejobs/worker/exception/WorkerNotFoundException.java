package com.swipejobs.worker.exception;

public class WorkerNotFoundException extends Exception {

    public WorkerNotFoundException(){
        super("Worker not found.");
    }
}

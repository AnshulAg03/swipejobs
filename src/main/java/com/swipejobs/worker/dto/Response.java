package com.swipejobs.worker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private Object response;

    public Response(Object response){
        this.response = response;
    }
}

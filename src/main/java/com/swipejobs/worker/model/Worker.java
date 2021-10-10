package com.swipejobs.worker.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Worker {

    private Integer rating = 0;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSetter(contentNulls = Nulls.SKIP)
    private List<String> certificates = new ArrayList();

    private List<String> skills = new ArrayList();

    private JobSearchAddress jobSearchAddress;

    private String transportation = "";

    private boolean hasDriversLicense;

    @JsonSetter(contentNulls = Nulls.SKIP)
    private List<Availability> availability = new ArrayList<>();

    @JsonProperty(required = true)
    private String phone;

    @JsonProperty(required = true)
    private String email;

    private Name name = new Name();

    private Integer age;

    @JsonProperty(required = true)
    private String guid;

    @JsonProperty(required = true)
    private Long userId;

}

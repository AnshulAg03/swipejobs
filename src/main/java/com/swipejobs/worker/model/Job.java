package com.swipejobs.worker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Job {

    private boolean driverLicenseRequired;

    private List<String> requiredCertificates = new ArrayList();

    private Location location;

    private String billRate;

    private Integer workersRequired;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    private String about;

    @JsonProperty(required = true)
    private String jobTitle;

    @JsonProperty(required = true)
    private String company;

    @JsonProperty(required = true)
    private String guid;

    @JsonProperty(required = true)
    private Long jobId;
}

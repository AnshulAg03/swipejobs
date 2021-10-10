package com.swipejobs.worker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobSearchAddress extends Location{

    private String unit;

    private Integer maxJobDistance;


}

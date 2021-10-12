package com.swipejobs.worker.service.impl;

import com.swipejobs.worker.model.*;
import com.swipejobs.worker.service.JobService;
import com.swipejobs.worker.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    private static final Logger LOGGER=Logger.getLogger("JobServiceImpl.class");

    @Autowired
    private WorkerService workerService;

    @Autowired
    private RestTemplateImpl restTemplate;

    @Value("${jobs.api.url}")
    private String jobsUrl;

    @Value("${job.limit:3}")
    private Integer jobLimit;

    @Value("${latitude.change.per.km}")
    private double latitudeChangePerKm;

    @Value("${longitude.change.per.km}")
    private double longitudeChangePerKm;

    private List<Job> jobsCache = new ArrayList();

    BiPredicate<List<Availability>, Integer> availabilityPredicate = (availability, dayOfWeek) -> availability.stream().anyMatch(day -> (day.getDayIndex()== dayOfWeek));

    BiPredicate<Boolean, Boolean> driversLicensePredicate = (jobRequiresDriversLicense, workerHasDriverLicense) -> !jobRequiresDriversLicense || workerHasDriverLicense;

    BiPredicate<JobSearchAddress, Location> locationPredicate = (workerLocation, jobLocation) -> {
        Integer maxJobDistance = workerLocation.getMaxJobDistance();
        double totalChangeInLatitude = maxJobDistance *latitudeChangePerKm;
        double totalChangeInLongitude = maxJobDistance *Math.cos(totalChangeInLatitude);
        return Math.abs(workerLocation.getLatitude()-jobLocation.getLatitude())<=totalChangeInLatitude
                && Math.abs(workerLocation.getLongitude()-jobLocation.getLongitude())<=totalChangeInLongitude;
    };

    @Override
    public List<Job> getJobsForWorker(Long workerId) throws Exception {
        return this.getJobsForWorker(this.workerService.getWorker(workerId));
    }

    private List<Job> getJobsForWorker(Worker worker) {
        if(this.jobsCache.isEmpty()){
            LOGGER.info("Job Cache is empty.");
            this.updateJobCache();
        }

        return this.jobsCache.stream()
                .filter(job -> !Collections.disjoint(job.getRequiredCertificates(), worker.getCertificates()))
                .filter(job -> worker.getSkills().contains(job.getJobTitle()))
                .filter(job -> driversLicensePredicate.test(job.isDriverLicenseRequired(), worker.isHasDriversLicense()))
                .filter(job -> availabilityPredicate.test(worker.getAvailability(), job.getStartDate().getDayOfWeek().getValue()))
                .filter(job -> locationPredicate.test(worker.getJobSearchAddress(), job.getLocation()))
                .limit(jobLimit)
                .collect(Collectors.toList());
    }

    @Override
    public void updateJobCache() {
        LOGGER.info("Calling Jobs API to fetch Jobs list");
        try {
            this.jobsCache = restTemplate.getJobs(jobsUrl);
        } catch(Exception e){
            LOGGER.warning(String.format("Error occurred while calling %s", jobsUrl));
            this.jobsCache = new ArrayList();
        }
    }


}

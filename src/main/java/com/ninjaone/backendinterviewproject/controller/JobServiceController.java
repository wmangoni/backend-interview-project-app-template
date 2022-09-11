package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.JobService;
import com.ninjaone.backendinterviewproject.service.JobServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobservice")
public class JobServiceController {
    private final JobServiceService service;

    public JobServiceController(JobServiceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private JobService postDeviceEntity(@RequestBody JobService jobService) {
        return service.saveDeviceEntity(jobService);
    }

    @GetMapping()
    private List<JobService> getDevices() {
        return service.getDevices();
    }
    
    @GetMapping("/{id}")
    private JobService getDeviceEntity(@PathVariable Long id) {
        return service.getDeviceEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteDeviceEntity(@PathVariable Long id) {
        service.deleteDeviceEntity(id);
    }
}

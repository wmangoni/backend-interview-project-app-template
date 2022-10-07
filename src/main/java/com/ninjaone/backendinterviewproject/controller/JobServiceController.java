package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.JobService;
import com.ninjaone.backendinterviewproject.service.JobServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobservice")
public class JobServiceController {

    private final JobServiceService service;

    public JobServiceController(JobServiceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private JobService postJobServiceEntity(@RequestBody JobService jobService) {
        return service.saveJobServiceEntity(jobService);
    }

    @GetMapping()
    private List<JobService> getJobServices() {
        return service.getJobService();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getJobServiceEntity(@PathVariable Long id) {
        Optional<JobService> jobService = service.getJobServiceEntity(id);
        if (jobService.isPresent()) {
            return ResponseEntity.ok(jobService.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteJobServiceEntity(@PathVariable Long id) {
        service.deleteJobServiceEntity(id);
    }
}

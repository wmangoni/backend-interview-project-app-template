package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.JobServiceRepository;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceService {
    private final JobServiceRepository repository;

    public JobServiceService(JobServiceRepository repository) {
        this.repository = repository;
    }

    public List<JobService> getDevices() {
        return new ArrayList<>((Collection<? extends JobService>) repository.findAll());
    }

    public JobService saveDeviceEntity(JobService device) {
        return repository.save(device);
    }

    public Optional<JobService> getDeviceEntity(Long id) {
        return repository.findById(id);
    }

    public void deleteDeviceEntity(Long id) {
        repository.deleteById(id);
    }
}

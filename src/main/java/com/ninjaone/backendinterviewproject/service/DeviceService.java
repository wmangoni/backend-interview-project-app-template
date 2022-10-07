package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public List<Device> getDevices() {
        return new ArrayList<>((Collection<? extends Device>) repository.findAll());
    }

    public Device saveDeviceEntity(Device device) {
        return repository.save(device);
    }

    public Optional<Device> getDeviceEntity(Long id) {
        return repository.findById(id);
    }

    public void deleteDeviceEntity(Long id) {
        repository.deleteById(id);
    }
}

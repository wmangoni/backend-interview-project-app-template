package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Device postDeviceEntity(@RequestBody Device device) {
        return service.saveDeviceEntity(device);
    }

    @GetMapping()
    private List<Device> getDevices() {
        return service.getDevices();
    }
    
    @GetMapping("/{id}")
    private Device getDeviceEntity(@PathVariable Long id) {
        return service.getDeviceEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteDeviceEntity(@PathVariable Long id) {
        service.deleteDeviceEntity(id);
    }
}

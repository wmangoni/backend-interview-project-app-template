package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import com.ninjaone.backendinterviewproject.service.CustomerDeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer-device")
public class CustomerDeviceController {

    private final CustomerDeviceService service;

    public CustomerDeviceController(CustomerDeviceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private CustomerDevice postCustomerDeviceEntity(@RequestBody CustomerDevice device) {
        return service.saveCustomerDeviceEntity(device);
    }

    @GetMapping()
    private List<CustomerDevice> getCustomersDevice() {
        return service.getCustomersDevice();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getCustomerDeviceEntity(@PathVariable Long id) {
        Optional<CustomerDevice> customerDevice = service.getCustomerDeviceEntity(id);
        if (customerDevice.isPresent()) {
            return ResponseEntity.ok(customerDevice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCustomerDeviceEntity(@PathVariable Long id) {
        service.deleteCustomerDeviceEntity(id);
    }

    @GetMapping("/customer/{customerId}")
    private List<CustomerDevice> getDevicesByCustomer(@PathVariable Long customerId) {
        return service.getDevicesByCustomerId(customerId);
    }
}

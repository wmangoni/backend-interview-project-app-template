package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Customer postCustomerEntity(@RequestBody Customer device) {
        return service.saveCustomerEntity(device);
    }

    @GetMapping()
    private List<Customer> getCustomers() {
        return service.getCustomers();
    }
    
    @GetMapping("/{id}")
    private Customer getCustomerEntity(@PathVariable Long id) {
        return service.getCustomerEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCustomerEntity(@PathVariable Long id) {
        service.deleteCustomerEntity(id);
    }
}

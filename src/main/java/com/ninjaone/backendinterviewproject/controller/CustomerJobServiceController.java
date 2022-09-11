package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import com.ninjaone.backendinterviewproject.service.CustomerJobServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-jobservices")
public class CustomerJobServiceController {

    private final CustomerJobServiceService service;

    public CustomerJobServiceController(CustomerJobServiceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private CustomerJobService postCustomerJobServiceEntity(@RequestBody CustomerJobService device) {
        return service.saveCustomerJobServiceEntity(device);
    }

    @GetMapping()
    private List<CustomerJobService> getCustomerJobServices() {
        return service.getCustomerJobServices();
    }
    
    @GetMapping("/{id}")
    private CustomerJobService getCustomerJobServiceEntity(@PathVariable Long id) {
        return service.getCustomerJobServiceEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCustomerJobServiceEntity(@PathVariable Long id) {
        service.deleteCustomerJobServiceEntity(id);
    }

    @GetMapping("/customer/{customerId}")
    private List<CustomerJobService> getServicesByCustomer(@PathVariable Long customerId) {
        return service.getServicesByCustomerId(customerId);
    }
}

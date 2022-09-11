package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerJobServiceRepository;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerJobServiceService {
    private final CustomerJobServiceRepository repository;

    public CustomerJobServiceService(CustomerJobServiceRepository repository) {
        this.repository = repository;
    }

    public List<CustomerJobService> getCustomerJobServices() {
        return new ArrayList<>((Collection<? extends CustomerJobService>) repository.findAll());
    }

    public CustomerJobService saveCustomerJobServiceEntity(CustomerJobService device) {
        return repository.save(device);
    }

    public Optional<CustomerJobService> getCustomerJobServiceEntity(Long id) {
        return repository.findById(id);
    }

    public void deleteCustomerJobServiceEntity(Long id) {
        repository.deleteById(id);
    }

    public List<CustomerJobService> getServicesByCustomerId(Long customerId) {
        return new ArrayList<>((Collection<? extends CustomerJobService>) repository.findByCustomerId(customerId));
    }
}

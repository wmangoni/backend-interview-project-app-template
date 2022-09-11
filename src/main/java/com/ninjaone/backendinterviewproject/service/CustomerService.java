package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>((Collection<? extends Customer>) repository.findAll());
    }

    public Customer saveCustomerEntity(Customer device) {
        return repository.save(device);
    }

    public Optional<Customer> getCustomerEntity(Long id) {
        return repository.findById(id);
    }

    public void deleteCustomerEntity(Long id) {
        repository.deleteById(id);
    }
}

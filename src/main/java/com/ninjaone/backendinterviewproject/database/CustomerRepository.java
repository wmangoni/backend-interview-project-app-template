package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
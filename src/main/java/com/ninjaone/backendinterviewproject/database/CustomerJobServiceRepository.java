package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import org.springframework.data.repository.CrudRepository;

public interface CustomerJobServiceRepository extends CrudRepository<CustomerJobService, Long> {

    Iterable<CustomerJobService> findByCustomerId(Long customerId);
}
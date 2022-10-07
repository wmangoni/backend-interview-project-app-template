package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDeviceRepository extends CrudRepository<CustomerDevice, Long> {

    Iterable<CustomerDevice> findByCustomerId(Long customerId);
}
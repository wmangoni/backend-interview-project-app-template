package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerDeviceRepository extends CrudRepository<CustomerDevice, Long> {

    Iterable<CustomerDevice> findByCustomerId(Long customerId);
}
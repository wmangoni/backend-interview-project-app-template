package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.JobService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobServiceRepository extends CrudRepository<JobService, Long> {

    List<JobService> findByNameContaining(String name);
}

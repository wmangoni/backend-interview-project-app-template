package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.JobServiceRepository;
import com.ninjaone.backendinterviewproject.model.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceServiceTest {
    public static final Long ID = 1L;

    @Mock
    private JobServiceRepository repository;

    @InjectMocks
    private JobServiceService testObject;

    private JobService entity;

    @BeforeEach
    void setup(){
        entity = new JobService(ID, "value", "1.0");
    }

    @Test
    void getJobService() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<JobService> jobServiceEntity = testObject.getJobServiceEntity(ID);
        JobService actualEntity = jobServiceEntity.orElse(null);
        assert actualEntity != null;
        assertEquals(entity.getName(), actualEntity.getName());
        assertEquals(entity.getCost(), actualEntity.getCost());
    }

    @Test
    void saveJobServiceEntity() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, testObject.saveJobServiceEntity(entity));
    }

    @Test
    void deleteJobServiceEntity(){
        doNothing().when(repository).deleteById(ID);
        testObject.deleteJobServiceEntity(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}

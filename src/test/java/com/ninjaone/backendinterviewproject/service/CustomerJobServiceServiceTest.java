package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerJobServiceRepository;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerJobService;
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
public class CustomerJobServiceServiceTest {
    public static final Long ID = 1L;

    @Mock
    private CustomerJobServiceRepository repository;

    @InjectMocks
    private CustomerJobServiceService testObject;

    private CustomerJobService entity;

    @BeforeEach
    void setup() {
        entity = new CustomerJobService(ID, new Customer(1L, "name"), new JobService(1L, "device1", "1.0"));
    }

    @Test
    void getDeviceData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<CustomerJobService> customerOptional = testObject.getCustomerJobServiceEntity(ID);
        CustomerJobService actualEntity = customerOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(entity.getCustomer().getName(), actualEntity.getCustomer().getName());
        assertEquals(entity.getJobService().getName(), actualEntity.getJobService().getName());
        assertEquals(entity.getJobService().getCost(), actualEntity.getJobService().getCost());
    }

    @Test
    void saveDeviceData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, testObject.saveCustomerJobServiceEntity(entity));
    }

    @Test
    void deleteDeviceData() {
        doNothing().when(repository).deleteById(ID);
        testObject.deleteCustomerJobServiceEntity(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}

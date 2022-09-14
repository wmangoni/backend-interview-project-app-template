package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
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
public class CustomerServiceTest {
    public static final Long ID = 1L;

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService testObject;

    private Customer entity;

    @BeforeEach
    void setup(){
        entity = new Customer(ID, "value");
    }

    @Test
    void getDeviceData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<Customer> customerOptional = testObject.getCustomerEntity(ID);
        Customer actualEntity = customerOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(entity.getName(), actualEntity.getName());
    }

    @Test
    void saveDeviceData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, testObject.saveCustomerEntity(entity));
    }

    @Test
    void deleteDeviceData(){
        doNothing().when(repository).deleteById(ID);
        testObject.deleteCustomerEntity(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}

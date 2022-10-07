package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerDeviceRepository;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerDevice;
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
public class CustomerDeviceServiceTest {
    public static final Long ID = 1L;

    @Mock
    private CustomerDeviceRepository repository;

    @InjectMocks
    private CustomerDeviceService testObject;

    private CustomerDevice entity;

    @BeforeEach
    void setup() {
        entity = new CustomerDevice(ID, new Customer(1L, "name"), new Device(1L, "device1", DeviceType.MAC), 1L);
    }

    @Test
    void getDeviceData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<CustomerDevice> customerOptional = testObject.getCustomerDeviceEntity(ID);
        CustomerDevice actualEntity = customerOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(entity.getCustomer().getName(), actualEntity.getCustomer().getName());
        assertEquals(entity.getDevice().getSystemName(), actualEntity.getDevice().getSystemName());
        assertEquals(entity.getQuantity(), actualEntity.getQuantity());
    }

    @Test
    void saveDeviceData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, testObject.saveCustomerDeviceEntity(entity));
    }

    @Test
    void deleteDeviceData() {
        doNothing().when(repository).deleteById(ID);
        testObject.deleteCustomerDeviceEntity(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}

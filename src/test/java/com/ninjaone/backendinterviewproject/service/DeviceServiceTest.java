package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
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
public class DeviceServiceTest {
    public static final Long ID = 1L;

    @Mock
    private DeviceRepository repository;

    @InjectMocks
    private DeviceService testObject;

    private Device entity;

    @BeforeEach
    void setup() {
        entity = new Device(ID, "value", DeviceType.WINDOWS_WORKSTATION);
    }

    @Test
    void getDeviceData() {
        when(repository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<Device> deviceOptional = testObject.getDeviceEntity(ID);
        Device actualEntity = deviceOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(entity.getSystemName(), actualEntity.getSystemName());
    }

    @Test
    void saveDeviceData() {
        when(repository.save(entity)).thenReturn(entity);
        assertEquals(entity, testObject.saveDeviceEntity(entity));
    }

    @Test
    void deleteDeviceData() {
        doNothing().when(repository).deleteById(ID);
        testObject.deleteDeviceEntity(ID);
        Mockito.verify(repository, times(1)).deleteById(ID);
    }
}

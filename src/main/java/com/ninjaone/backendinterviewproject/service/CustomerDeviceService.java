package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.CustomerDeviceRepository;
import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerDeviceService {
    private final CustomerDeviceRepository repository;

    public CustomerDeviceService(CustomerDeviceRepository repository) {
        this.repository = repository;
    }

    public List<CustomerDevice> getCustomersDevice() {
        return new ArrayList<>((Collection<? extends CustomerDevice>) repository.findAll());
    }

    public CustomerDevice saveCustomerDeviceEntity(CustomerDevice device) {
        return repository.save(device);
    }

    public Optional<CustomerDevice> getCustomerDeviceEntity(Long id) {
        return repository.findById(id);
    }

    public void deleteCustomerDeviceEntity(Long id) {
        repository.deleteById(id);
    }

    public List<CustomerDevice> getDevicesByCustomerId(Long customerId) {
        return new ArrayList<>((Collection<? extends CustomerDevice>) repository.findByCustomerId(customerId));
    }

    public BigDecimal getMacQty(List<CustomerDevice> devices) {
        return devices.stream()
                .filter(c -> c.getDevice().getType() == DeviceType.MAC)
                .map(c -> BigDecimal.valueOf(c.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getWindowsQty(List<CustomerDevice> devices) {
        return devices.stream()
                .filter(c -> c.getDevice().getType() != DeviceType.MAC)
                .map(c -> BigDecimal.valueOf(c.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDevicesPrice(List<CustomerDevice> devices) {
        return devices.stream()
                .map(customerDevice -> new BigDecimal("4.0").multiply(BigDecimal.valueOf(customerDevice.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

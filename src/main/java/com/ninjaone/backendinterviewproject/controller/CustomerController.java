package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.domain.DeviceType;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.service.CustomerDeviceService;
import com.ninjaone.backendinterviewproject.service.CustomerJobServiceService;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;
    private final CustomerDeviceService customerDeviceService;
    private final CustomerJobServiceService customerJobServiceService;

    public CustomerController(
            final CustomerService service,
            final CustomerDeviceService customerDeviceService,
            final CustomerJobServiceService customerJobServiceService,
            final DeviceService deviceService) {
        this.service = service;
        this.customerDeviceService = customerDeviceService;
        this.customerJobServiceService = customerJobServiceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Customer postCustomerEntity(@RequestBody Customer device) {
        return service.saveCustomerEntity(device);
    }

    @GetMapping()
    private List<Customer> getCustomers() {
        return service.getCustomers();
    }
    
    @GetMapping("/{id}")
    private Customer getCustomerEntity(@PathVariable Long id) {
        return service.getCustomerEntity(id)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCustomerEntity(@PathVariable Long id) {
        service.deleteCustomerEntity(id);
    }

    @GetMapping("/{customerId}/calculate-price")
    private BigDecimal calculatePrice(@PathVariable Long customerId) {
        List<CustomerDevice> devices = customerDeviceService.getDevicesByCustomerId(customerId);
        List<CustomerJobService> jobServices = customerJobServiceService.getServicesByCustomerId(customerId);

        BigDecimal devicesPrice = devices.stream()
                .map(customerDevice -> new BigDecimal("4.0").multiply(BigDecimal.valueOf(customerDevice.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal jobServicesPrice = jobServices.stream()
                .filter(cjs -> !cjs.getJobService().getName().equals("Antivirus"))
                .map(js -> new BigDecimal(js.getJobService().getCost()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (jobServices.stream().anyMatch(c -> c.getJobService().getName().equals("Antivirus"))) {
            BigDecimal antivirusCost = jobServices.stream()
                    .filter(cjs -> cjs.getJobService().getName().equals("Antivirus"))
                    .map(js -> {
                        BigDecimal valuesForMac = devices.stream()
                                .filter(d -> d.getDevice().getType() == DeviceType.MAC)
                                .map(c -> new BigDecimal("7.0")).reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal valuesForNonMac = devices.stream()
                                .filter(d -> d.getDevice().getType() != DeviceType.MAC)
                                .map(c -> new BigDecimal("5.0")).reduce(BigDecimal.ZERO, BigDecimal::add);

                        return valuesForMac.add(valuesForNonMac);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return devicesPrice.add(jobServicesPrice).add(antivirusCost);
        }

        return devicesPrice.add(jobServicesPrice);
    }
}

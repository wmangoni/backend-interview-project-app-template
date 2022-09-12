package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.CustomerDevice;
import com.ninjaone.backendinterviewproject.model.CustomerJobService;
import com.ninjaone.backendinterviewproject.service.CustomerDeviceService;
import com.ninjaone.backendinterviewproject.service.CustomerJobServiceService;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal calculatePriceByCustomer(@PathVariable Long customerId) {
        List<CustomerDevice> devices = customerDeviceService.getDevicesByCustomerId(customerId);
        List<CustomerJobService> jobServices = customerJobServiceService.getServicesByCustomerId(customerId);

        BigDecimal macQty = customerDeviceService.getMacQty(devices);

        BigDecimal windowsQty = customerDeviceService.getWindowsQty(devices);

        BigDecimal devicesPrice = customerDeviceService.getDevicesPrice(devices);

        BigDecimal jobServicesPrice = customerJobServiceService.getJobServicesCost(jobServices, macQty, windowsQty);

        if (hasAntivirus(jobServices)) {
            BigDecimal macAntivirusTotalCost = BigDecimal.valueOf(7.0).multiply(macQty);
            BigDecimal windowsAntivirusTotalCost = BigDecimal.valueOf(5.0).multiply(windowsQty);

            return devicesPrice.add(jobServicesPrice).add(macAntivirusTotalCost.add(windowsAntivirusTotalCost));
        }

        return devicesPrice.add(jobServicesPrice);
    }

    private boolean hasAntivirus(List<CustomerJobService> jobServices) {
        return jobServices.stream().anyMatch(c -> c.getJobService().getName().equals("Antivirus"));
    }
}
